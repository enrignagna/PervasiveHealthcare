/*
 * Copyright (c) 2021.  Ylenia Battistini, Enrico Gnagnarella, Matteo Scucchia
 *
 *                              Licensed under the Apache License, Version 2.0 (the "License");
 *                              you may not use this file except in compliance with the License.
 *                              You may obtain a copy of the License at
 *
 *                                  http://www.apache.org/licenses/LICENSE-2.0
 *
 *                              Unless required by applicable law or agreed to in writing, software
 *                              distributed under the License is distributed on an "AS IS" BASIS,
 *                              WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *                              See the License for the specific language governing permissions and
 *                              limitations under the License.
 */

package json.generalpractitionerinfo

import domainmodel.generalpractitionerinfo.PrescriptionHistory.PrescriptionHistory
import domainmodel.generalpractitionerinfo.{Prescription, PrescriptionDescription, PrescriptionInitialDate}
import json.LocalDateJsonFormat.DateFormat
import json.RequestJsonFormats.{StringJsonFormat, immSetFormat, jsonFormat2}
import spray.json.DefaultJsonProtocol.jsonFormat1
import spray.json.RootJsonFormat

object PrescriptionJsonFormat {

  implicit val prescriptionInitialDateJsonFormat: RootJsonFormat[PrescriptionInitialDate] = jsonFormat1(PrescriptionInitialDate)

  implicit val prescriptionDescriptionJsonFormat: RootJsonFormat[PrescriptionDescription] = jsonFormat1(PrescriptionDescription)

  implicit val prescriptionJsonFormat: RootJsonFormat[Prescription] = jsonFormat2(Prescription)

  implicit val prescriptionsJsonFormat: RootJsonFormat[PrescriptionHistory] = jsonFormat1(PrescriptionHistory)
}
