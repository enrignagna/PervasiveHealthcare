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

package json.medicalrecords

import domainmodel.medicalrecords.SingleSheetTherapies.SingleSheetTherapies
import domainmodel.medicalrecords.{DrugsPrescription, DrugsSomministration, SingleSheetTherapy}
import spray.json.DefaultJsonProtocol.{jsonFormat2, jsonFormat3}
import json.utility.InfoDescriptionJsonFormat.descriptionJsonFormat
import spray.json.RootJsonFormat
import json.RequestJsonFormats.immSetFormat
import json.LocalDateTimeJsonFormat.DateTimeFormat
import json.professionalfigure.DoctorJsonFormat.DoctorJsonFormat

object SingleSheetTherapyJsonFormat {
  implicit val singleSheetTherapyJsonFormat: RootJsonFormat[SingleSheetTherapy] = jsonFormat2(SingleSheetTherapy)
  implicit val drugsPrescriptionJsonFormat: RootJsonFormat[DrugsPrescription] = jsonFormat3(DrugsPrescription)
  implicit val drugsSomministrationTherapyJsonFormat: RootJsonFormat[DrugsSomministration] = jsonFormat3(DrugsSomministration)
  implicit val singleSheetTherapiesJsonFormat: RootJsonFormat[SingleSheetTherapies] = jsonFormat2(SingleSheetTherapies)
}
