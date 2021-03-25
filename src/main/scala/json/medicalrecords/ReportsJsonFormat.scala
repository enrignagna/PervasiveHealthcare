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

import domainmodel.medicalrecords.Reports.Reports
import domainmodel.medicalrecords.{Activity, Assistance, Consulting, Diagnostics, Rehabilitation, Report, TherapeuticDelivery, TreatmentType}
import spray.json.DefaultJsonProtocol.{StringJsonFormat, jsonFormat1, jsonFormat3, jsonFormat5}
import spray.json.RootJsonFormat
import json.RequestJsonFormats.immSetFormat
import json.LocalDateTimeJsonFormat.DateTimeFormat

object ReportsJsonFormat {

  implicit val diagnosticsJsonFormat: RootJsonFormat[Diagnostics] = jsonFormat1(Diagnostics)
  implicit val consultingJsonFormat: RootJsonFormat[Consulting] = jsonFormat1(Consulting)
  implicit val therapeuticDeliveryJsonFormat: RootJsonFormat[TherapeuticDelivery] = jsonFormat1(TherapeuticDelivery)
  implicit val rehabilitationJsonFormat: RootJsonFormat[Rehabilitation] = jsonFormat1(Rehabilitation)
  implicit val assistanceTypeJsonFormat: RootJsonFormat[Assistance] = jsonFormat1(Assistance)
  implicit val activityJsonFormat: RootJsonFormat[Activity] = jsonFormat5(Activity)
  implicit val treatmentTypeJsonFormat: RootJsonFormat[TreatmentType] = jsonFormat1(TreatmentType)
  implicit val reportJsonFormat: RootJsonFormat[Report] = jsonFormat3(Report)
  implicit val reportsJsonFormat: RootJsonFormat[Reports] = jsonFormat1(Reports)
}