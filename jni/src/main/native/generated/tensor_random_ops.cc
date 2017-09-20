/* DO NOT EDIT THIS FILE - it is machine generated */

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

#include "tensor_random_ops.h"

#include <algorithm>
#include <cstring>
#include <memory>
#include <sstream>

#include "c_api.h"
#include "c_eager_api.h"
#include "exception.h"
#include "utilities.h"

JNIEXPORT jlong JNICALL Java_org_platanios_tensorflow_jni_generated_tensors_Random_00024_randomUniform(
    JNIEnv* env, jobject object, jlong context_handle, jlong shape, jint dtype, jlong seed, jlong seed2) {
  REQUIRE_HANDLE(context, TFE_Context, context_handle, 0);
  std::unique_ptr<TF_Status, decltype(&TF_DeleteStatus)> status(TF_NewStatus(), TF_DeleteStatus);

  std::unique_ptr<TFE_Op, decltype(&TFE_DeleteOp)> op(
      TFE_NewOp(context, "RandomUniform", status.get()), TFE_DeleteOp);
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(shape_handle, TFE_TensorHandle, shape, 0);
  TFE_OpAddInput(op.get(), shape_handle, status.get());
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(attr_T_shape_handle, TFE_TensorHandle, shape, 0);
  const TF_DataType attr_T = TFE_TensorHandleDataType(attr_T_shape_handle);
  TFE_OpSetAttrType(op.get(), "T", attr_T);

  TFE_OpSetAttrType(op.get(), "dtype", static_cast<TF_DataType>(dtype));

  TFE_OpSetAttrInt(op.get(), "seed", static_cast<int64_t>(seed));

  TFE_OpSetAttrInt(op.get(), "seed2", static_cast<int64_t>(seed2));

  const int num_outputs = 1;
  std::unique_ptr<TFE_TensorHandle* []> outputs(new TFE_TensorHandle* [num_outputs]);
  std::unique_ptr<int[]> actual_num_outputs(new int[1] {1});
  TFE_Execute(op.get(), outputs.get(), actual_num_outputs.get(), status.get());
  CHECK_STATUS(env, status.get(), 0);

  return reinterpret_cast<jlong>(outputs[0]);
}

JNIEXPORT jlong JNICALL Java_org_platanios_tensorflow_jni_generated_tensors_Random_00024_randomUniformInt(
    JNIEnv* env, jobject object, jlong context_handle, jlong shape, jlong minval, jlong maxval, jlong seed, jlong seed2) {
  REQUIRE_HANDLE(context, TFE_Context, context_handle, 0);
  std::unique_ptr<TF_Status, decltype(&TF_DeleteStatus)> status(TF_NewStatus(), TF_DeleteStatus);

  std::unique_ptr<TFE_Op, decltype(&TFE_DeleteOp)> op(
      TFE_NewOp(context, "RandomUniformInt", status.get()), TFE_DeleteOp);
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(shape_handle, TFE_TensorHandle, shape, 0);
  TFE_OpAddInput(op.get(), shape_handle, status.get());
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(minval_handle, TFE_TensorHandle, minval, 0);
  TFE_OpAddInput(op.get(), minval_handle, status.get());
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(maxval_handle, TFE_TensorHandle, maxval, 0);
  TFE_OpAddInput(op.get(), maxval_handle, status.get());
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(attr_T_shape_handle, TFE_TensorHandle, shape, 0);
  const TF_DataType attr_T = TFE_TensorHandleDataType(attr_T_shape_handle);
  TFE_OpSetAttrType(op.get(), "T", attr_T);

  REQUIRE_HANDLE(attr_Tout_minval_handle, TFE_TensorHandle, minval, 0);
  const TF_DataType attr_Tout = TFE_TensorHandleDataType(attr_Tout_minval_handle);
  TFE_OpSetAttrType(op.get(), "Tout", attr_Tout);

  REQUIRE_HANDLE(attr_Tout_maxval_handle, TFE_TensorHandle, maxval, 0);
  const TF_DataType attr_Tout_maxval = TFE_TensorHandleDataType(attr_Tout_maxval_handle);
  if (attr_Tout != attr_Tout_maxval) {
      std::stringstream error_msg;
      error_msg
          << "Argument 'maxval' of 'randomUniformInt' op with data type '"
          << attr_Tout_maxval
          << "' must match data type '"
          << attr_Tout
          << "' of argument 'minval'";
      throw_exception(env, tf_invalid_argument_exception, error_msg.str().c_str());
  }

  TFE_OpSetAttrInt(op.get(), "seed", static_cast<int64_t>(seed));

  TFE_OpSetAttrInt(op.get(), "seed2", static_cast<int64_t>(seed2));

  const int num_outputs = 1;
  std::unique_ptr<TFE_TensorHandle* []> outputs(new TFE_TensorHandle* [num_outputs]);
  std::unique_ptr<int[]> actual_num_outputs(new int[1] {1});
  TFE_Execute(op.get(), outputs.get(), actual_num_outputs.get(), status.get());
  CHECK_STATUS(env, status.get(), 0);

  return reinterpret_cast<jlong>(outputs[0]);
}

JNIEXPORT jlong JNICALL Java_org_platanios_tensorflow_jni_generated_tensors_Random_00024_randomStandardNormal(
    JNIEnv* env, jobject object, jlong context_handle, jlong shape, jint dtype, jlong seed, jlong seed2) {
  REQUIRE_HANDLE(context, TFE_Context, context_handle, 0);
  std::unique_ptr<TF_Status, decltype(&TF_DeleteStatus)> status(TF_NewStatus(), TF_DeleteStatus);

  std::unique_ptr<TFE_Op, decltype(&TFE_DeleteOp)> op(
      TFE_NewOp(context, "RandomStandardNormal", status.get()), TFE_DeleteOp);
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(shape_handle, TFE_TensorHandle, shape, 0);
  TFE_OpAddInput(op.get(), shape_handle, status.get());
  CHECK_STATUS(env, status.get(), 0);

  REQUIRE_HANDLE(attr_T_shape_handle, TFE_TensorHandle, shape, 0);
  const TF_DataType attr_T = TFE_TensorHandleDataType(attr_T_shape_handle);
  TFE_OpSetAttrType(op.get(), "T", attr_T);

  TFE_OpSetAttrType(op.get(), "dtype", static_cast<TF_DataType>(dtype));

  TFE_OpSetAttrInt(op.get(), "seed", static_cast<int64_t>(seed));

  TFE_OpSetAttrInt(op.get(), "seed2", static_cast<int64_t>(seed2));

  const int num_outputs = 1;
  std::unique_ptr<TFE_TensorHandle* []> outputs(new TFE_TensorHandle* [num_outputs]);
  std::unique_ptr<int[]> actual_num_outputs(new int[1] {1});
  TFE_Execute(op.get(), outputs.get(), actual_num_outputs.get(), status.get());
  CHECK_STATUS(env, status.get(), 0);

  return reinterpret_cast<jlong>(outputs[0]);
}
