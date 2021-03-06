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

package org.platanios.tensorflow.api.core

import org.platanios.tensorflow.api.core.client.SessionConfig
import org.platanios.tensorflow.jni.{Session => NativeSession}

import org.tensorflow.framework.DeviceAttributes

/** Contains helper methods for dealing with devices.
  *
  * @author Emmanouil Antonios Platanios
  */
object Devices {
  /** Returns a sequence containing information for all the devices available to this local process.
    *
    * @param  sessionConfig Optional session configuration to use.
    * @return Sequence with information for all the devices available to this local process.
    */
  def local(sessionConfig: Option[SessionConfig] = None): Seq[DeviceAttributes] = {
    val devices = NativeSession.deviceList(sessionConfig.map(_.toConfigProto.toByteArray).orNull).toSeq
    devices.map(DeviceAttributes.parseFrom)
  }
}
