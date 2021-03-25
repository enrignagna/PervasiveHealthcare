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

import domainmodel.medicalrecords.DiagnosticServicesRequests.DiagnosticServicesRequests
import domainmodel.medicalrecords.{DiagnosticServicesRequest, DiagnosticServicesRequestID, Form}
import spray.json.DefaultJsonProtocol.{StringJsonFormat, jsonFormat1, jsonFormat4}
import spray.json.RootJsonFormat
import json.utility.InfoDescriptionJsonFormat.descriptionJsonFormat
import json.professionalfigure.ProfessionalFigureJsonFormat.doctorIDJsonFormat
import json.RequestJsonFormats.immSetFormat

object DiagnosticServicesRequestJsonFormat {

  implicit val diagnosticServicesRequestIDJsonFormat: RootJsonFormat[DiagnosticServicesRequestID] = jsonFormat1(DiagnosticServicesRequestID)
  implicit val formJsonFormat: RootJsonFormat[Form] = jsonFormat1(Form)
  implicit val diagnosticServicesRequestJsonFormat: RootJsonFormat[DiagnosticServicesRequest] = jsonFormat4(DiagnosticServicesRequest)
  implicit val diagnosticServicesRequestsJsonFormat: RootJsonFormat[DiagnosticServicesRequests] = jsonFormat1(DiagnosticServicesRequests)


}
