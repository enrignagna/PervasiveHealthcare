/*
 *
 *  * Copyright (c) 2021.  Ylenia Battistini, Enrico Gnagnarella, Matteo Scucchia
 *  *
 *  *                              Licensed under the Apache License, Version 2.0 (the "License");
 *  *                              you may not use this file except in compliance with the License.
 *  *                              You may obtain a copy of the License at
 *  *
 *  *                                  http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *                              Unless required by applicable law or agreed to in writing, software
 *  *                              distributed under the License is distributed on an "AS IS" BASIS,
 *  *                              WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *                              See the License for the specific language governing permissions and
 *  *                              limitations under the License.
 *
 */

package json.generalinfo

import domainmodel.generalinfo.{GeneralInfo, Height, Weight}
import json.PathologyJsonFormat.previousPathologiesJsonFormat
import json.generalinfo.AllergyJsonFormat.allergiesJsonFormat
import json.generalinfo.BloodGroupJsonFormat.bloodGroupJsonFormat
import json.generalinfo.ExamJsonFormat.examHistoryJsonFormat
import json.generalinfo.PrescriptionJsonFormat.prescriptionHistoryJsonFormat
import spray.json.DefaultJsonProtocol.{DoubleJsonFormat, IntJsonFormat, jsonFormat1, jsonFormat7}
import spray.json.RootJsonFormat

object GeneralInfoJsonFormat {
  implicit val weightJsonFormat: RootJsonFormat[Weight] = jsonFormat1(Weight)
  implicit val heightJsonFormat: RootJsonFormat[Height] = jsonFormat1(Height)
  implicit val prescriptionJsonFormat: RootJsonFormat[GeneralInfo] = jsonFormat7(GeneralInfo)
}