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

import domainmodel.medicalrecords.{CareDiary, InterventionEvaluation, NeedsIdentification, NursingDocumentation, NursingInterventionPlanning, Registration}
import spray.json.DefaultJsonProtocol.{StringJsonFormat, jsonFormat1, jsonFormat2, jsonFormat5}
import spray.json.RootJsonFormat
import json.utility.InfoDescriptionJsonFormat.descriptionJsonFormat
import json.medicalrecords.ClinicalDataJsonFormat.clinicalDataJsonFormat
import json.generalinfo.GeneralInfoJsonFormat.generalInfoJsonFormat
import json.LocalDateTimeJsonFormat.DateTimeFormat

object NursingDocumentationJsonFormat {

  implicit val nursingDocumentationJsonFormat: RootJsonFormat[NursingDocumentation] = jsonFormat5(NursingDocumentation)
  implicit val interventionEvaluationJsonFormat: RootJsonFormat[InterventionEvaluation] = jsonFormat1(InterventionEvaluation)
  implicit val careDiaryJsonFormat: RootJsonFormat[CareDiary] = jsonFormat1(CareDiary)
  implicit val nursingInterventionPlanningJsonFormat: RootJsonFormat[NursingInterventionPlanning] = jsonFormat2(NursingInterventionPlanning)
  implicit val needsIdentificationJsonFormat: RootJsonFormat[NeedsIdentification] = jsonFormat2(NeedsIdentification)
  implicit val registrationJsonFormat: RootJsonFormat[Registration] = jsonFormat2(Registration)
}