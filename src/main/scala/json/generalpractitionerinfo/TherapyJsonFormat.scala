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

import domainmodel.generalpractitionerinfo.TherapyHistory.TherapyHistory
import domainmodel.generalpractitionerinfo._
import json.LocalDateJsonFormat.DateFormat
import json.RequestJsonFormats.{StringJsonFormat, immSetFormat, jsonFormat4}
import spray.json.DefaultJsonProtocol.jsonFormat1
import spray.json.RootJsonFormat

object TherapyJsonFormat {

  implicit val therapyDateJsonFormat: RootJsonFormat[TherapyDate] = jsonFormat1(TherapyDate)

  implicit val therapyDescriptionJsonFormat: RootJsonFormat[TherapyDescription] = jsonFormat1(TherapyDescription)

  implicit val therapyInitialDateJsonFormat: RootJsonFormat[TherapyInitialDate] = jsonFormat1(TherapyInitialDate)

  implicit val therapyFinalDateJsonFormat: RootJsonFormat[TherapyFinalDate] = jsonFormat1(TherapyFinalDate)

  implicit val therapyJsonFormat: RootJsonFormat[Therapy] = jsonFormat4(Therapy)

  implicit val therapiesJsonFormat: RootJsonFormat[TherapyHistory] = jsonFormat1(TherapyHistory)
}
