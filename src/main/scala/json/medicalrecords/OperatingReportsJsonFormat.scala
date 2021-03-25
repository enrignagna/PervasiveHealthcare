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

import domainmodel.medicalrecords.Medical.Medical
import domainmodel.medicalrecords.{AnesthetistReport, InstrumentalistReport, InterventionType, OperatingReports, SurgeonReport}
import spray.json.DefaultJsonProtocol.{StringJsonFormat, jsonFormat1, jsonFormat2, jsonFormat3}
import spray.json.RootJsonFormat
import json.professionalfigure.ProfessionalFigureJsonFormat._
import json.utility.InfoDescriptionJsonFormat.noteJsonFormat
import json.RequestJsonFormats.immSetFormat
import json.LocalDateTimeJsonFormat.DateTimeFormat

object OperatingReportsJsonFormat {

  implicit val surgeonReportJsonFormat: RootJsonFormat[SurgeonReport] = jsonFormat2(SurgeonReport)
  implicit val anesthetistReportJsonFormat: RootJsonFormat[AnesthetistReport] = jsonFormat2(AnesthetistReport)
  implicit val instrumentalistReportJsonFormat: RootJsonFormat[InstrumentalistReport] = jsonFormat2(InstrumentalistReport)
  implicit val medicalJsonFormat: RootJsonFormat[Medical] = jsonFormat3(Medical)
  implicit val interventionTypeJsonFormat: RootJsonFormat[InterventionType] = jsonFormat1(InterventionType)
  implicit val operatingReportsJsonFormat: RootJsonFormat[OperatingReports] = jsonFormat3(OperatingReports)

}