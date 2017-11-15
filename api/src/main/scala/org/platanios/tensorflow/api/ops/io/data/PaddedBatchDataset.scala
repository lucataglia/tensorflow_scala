/* Copyright 2017, Emmanouil Antonios Platanios. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.platanios.tensorflow.api.ops.io.data

import org.platanios.tensorflow.api.core.Shape
import org.platanios.tensorflow.api.ops.{Basic, Op, Output}
import org.platanios.tensorflow.api.types.{INT64, STRING}

/** Dataset that wraps the application of the `paddedBatch` op.
  *
  * $OpDocDatasetPaddedBatch
  *
  * @param  inputDataset  Input dataset.
  * @param  batchSize     Batch size to use.
  * @param  paddedShapes  Shape to which the respective component of each input element should be padded prior to
  *                       batching. Any unknown dimensions (e.g., equal to `-1`) will be padded to the maximum size of
  *                       that dimension in each batch.
  * @param  paddingValues Scalar tensor structure representing the padding values to use for the respective components.
  *                       Defaults to zero for numeric types and the empty string for string types.
  * @param  name          Name for this dataset.
  * @tparam T             Tensor type (i.e., nested structure of tensors).
  * @tparam O             Output type (i.e., nested structure of symbolic tensors).
  * @tparam D             Data type of the outputs (i.e., nested structure of TensorFlow data types).
  * @tparam S             Shape type of the outputs (i.e., nested structure of TensorFlow shapes).
  *
  * @author Emmanouil Antonios Platanios
  */
case class PaddedBatchDataset[T, O, D, S](
    inputDataset: Dataset[T, O, D, S],
    batchSize: Long,
    paddedShapes: S,
    paddingValues: T = null.asInstanceOf[T],
    override val name: String = "PaddedBatchDataset"
) extends Dataset[T, O, D, S](name)(inputDataset.evOToT, inputDataset.ev, inputDataset.evFunctionInput) {
  override def createHandle(): Output = {
    Op.Builder(opType = "PaddedBatchDataset", name = name)
        .addInput(Op.createWithNameScope(name)(inputDataset.createHandle()))
        .addInput(Op.createWithNameScope(name)(Basic.constant(batchSize)))
        .addInputList(Op.createWithNameScope(name)(ev.flattenedShapes(paddedShapes).map(_.toOutput(INT64))))
        .addInputList(Op.createWithNameScope(name)(flattenedPaddingValues))
        .setAttribute("Toutput_types", flattenedOutputDataTypes.toArray)
        .setAttribute("output_shapes", flattenedOutputShapes.toArray)
        .build().outputs(0)
  }

  private[this] def flattenedPaddingValues: Seq[Output] = {
    if (paddingValues != null) {
      ev.flattenedTensors(paddingValues).map(Basic.constant(_))
    } else {
      flattenedOutputDataTypes.map({
        case STRING => Basic.constant("", STRING, Shape.scalar())
        case dataType => Basic.constant(0, dataType, Shape.scalar())
      })
    }
  }

  override def outputDataTypes: D = inputDataset.outputDataTypes
  override def outputShapes: S = {
    ev.unflattenShapes(outputDataTypes, inputDataset.flattenedOutputShapes.map(Shape(-1) ++ _))
  }
}

object PaddedBatchDataset {
  private[data] trait Implicits {
    implicit def datasetToPaddedBatchDatasetOps[T, O, D, S](
        dataset: Dataset[T, O, D, S]): PaddedBatchDatasetOps[T, O, D, S] = {
      PaddedBatchDatasetOps(dataset)
    }
  }

  case class PaddedBatchDatasetOps[T, O, D, S] private[PaddedBatchDatasetOps] (dataset: Dataset[T, O, D, S]) {
    /** $OpDocDatasetPaddedBatch
      *
      * @param  batchSize     Batch size.
      * @param  paddedShapes  Shape to which the respective component of each input element should be padded prior to
      *                       batching. Any unknown dimensions (e.g., equal to `-1`) will be padded to the maximum size
      *                       of that dimension in each batch.
      * @param  paddingValues Scalar tensor structure representing the padding values to use for the respective
      *                       components. Defaults to zero for numeric types and the empty string for string types.
      * @param  name          Name for the created dataset.
      * @return Created dataset.
      */
    def paddedBatch(
        batchSize: Long, paddedShapes: S, paddingValues: T = null.asInstanceOf[T],
        name: String = "PaddedBatch"): Dataset[T, O, D, S] = {
      Op.createWithNameScope(dataset.name) {
        PaddedBatchDataset(dataset, batchSize, paddedShapes, paddingValues, name)
      }
    }
  }

  /** @define OpDocDatasetPaddedBatch
    *   The dataset `paddedBatch` op combines consecutive elements of a dataset into padded batches.
    *
    *   Like the dataset `batch` op, this op combines multiple consecutive elements of a dataset, which might have
    *   different shapes, into a single element. The tensors in the resulting element have an additional outer
    *   dimension, and are padded to the respective shape in `paddedShapes`.
    */
  private[data] trait Documentation
}
