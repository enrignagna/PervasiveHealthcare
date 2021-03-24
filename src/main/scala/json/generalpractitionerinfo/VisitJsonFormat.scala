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

import domainmodel.generalpractitionerinfo.VisitHistory.VisitHistory
import domainmodel.generalpractitionerinfo.{Visit, VisitDate}
import json.LocalDateJsonFormat.DateFormat
import spray.json.DefaultJsonProtocol.{immSetFormat, jsonFormat1}
import spray.json.RootJsonFormat

object VisitJsonFormat {

  implicit val visitDateJsonFormat: RootJsonFormat[VisitDate] = jsonFormat1(VisitDate)

  implicit val visitJsonFormat: RootJsonFormat[Visit] = jsonFormat1(Visit)

  implicit val visitsJsonFormat: RootJsonFormat[VisitHistory] = jsonFormat1(VisitHistory)
}
