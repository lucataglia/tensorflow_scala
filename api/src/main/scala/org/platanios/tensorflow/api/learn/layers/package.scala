/* Copyright 2017-18, Emmanouil Antonios Platanios. All Rights Reserved.
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

package org.platanios.tensorflow.api.learn

import org.platanios.tensorflow.api.core.{Graph, Shape}
import org.platanios.tensorflow.api.ops.{OpSpecification, Output}
import org.platanios.tensorflow.api.ops.variables._
import org.platanios.tensorflow.api.types.DataType

import scala.util.DynamicVariable

/**
  * @author Emmanouil Antonios Platanios
  */
package object layers {
  private[learn] val layerContext: DynamicVariable[LayerCreationContext] = {
    new DynamicVariable[LayerCreationContext](LayerCreationContext())
  }

  trait ParameterGetter {
    def get(
        name: String,
        dataType: DataType,
        shape: Shape,
        initializer: Initializer = null,
        regularizer: Regularizer = null,
        trainable: Boolean = true,
        reuse: Reuse = ReuseOrCreateNew,
        collections: Set[Graph.Key[Variable]] = Set.empty,
        cachingDevice: OpSpecification => String = null
    ): Output

    def apply(
        name: String,
        dataType: DataType,
        shape: Shape,
        initializer: Initializer = null,
        regularizer: Regularizer = null,
        trainable: Boolean = true,
        reuse: Reuse = ReuseOrCreateNew,
        collections: Set[Graph.Key[Variable]] = Set.empty,
        cachingDevice: OpSpecification => String = null
    ): Output = {
      get(name, dataType, shape, initializer, regularizer, trainable, reuse, collections, cachingDevice)
    }
  }

  private[layers] val parameterGetter: DynamicVariable[ParameterGetter] = {
    new DynamicVariable[ParameterGetter](DefaultParameterGetter)
  }

  private[this] object DefaultParameterGetter extends ParameterGetter {
    override def get(
        name: String,
        dataType: DataType,
        shape: Shape,
        initializer: Initializer = null,
        regularizer: Regularizer = null,
        trainable: Boolean = true,
        reuse: Reuse = ReuseOrCreateNew,
        collections: Set[Graph.Key[Variable]] = Set.empty,
        cachingDevice: OpSpecification => String = null
    ): Output = {
      Variable.getVariable(
        name, dataType, shape, initializer, regularizer, trainable, reuse, collections, cachingDevice)
    }
  }

  private[api] trait API
      extends Activation.API
          with Basic.API
          with Embedding.API
          with Input.API
          with Layer.API
          with Loss.API
          with Math.API
          with NN.API
          with Summary.API
          with core.API
          with rnn.API {
    type ParameterGetter = layers.ParameterGetter

    def withParameterGetter[R](parameterGetter: ParameterGetter)(block: => R): R = {
      layers.parameterGetter.withValue(parameterGetter)(block)
    }
  }

  private[api] object API extends API
}
