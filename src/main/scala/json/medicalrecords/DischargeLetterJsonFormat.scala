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

import domainmodel.medicalrecords.PharmacologicalTherapies.PharmacologicalTherapies
import domainmodel.medicalrecords.{AdmissionReason, ClinicalProblemsEncountered, DischargeDiagnosis, DischargeLetter, FollowUpInstructions, PharmacologicalTherapy, PostHospitalCareInformation, SdoCompilation, SignificantFindings}
import spray.json.DefaultJsonProtocol.{StringJsonFormat, jsonFormat1, jsonFormat5, jsonFormat7}
import spray.json.RootJsonFormat
import json.utility.InfoDescriptionJsonFormat.descriptionJsonFormat
import json.LocalDateJsonFormat.DateFormat
import json.professionalfigure.ProfessionalFigureJsonFormat.doctorIDJsonFormat
import json.RequestJsonFormats.immSetFormat
import json.LocalDateTimeJsonFormat.DateTimeFormat

object DischargeLetterJsonFormat {

  implicit val admissionReasonJsonFormat: RootJsonFormat[AdmissionReason] = jsonFormat1(AdmissionReason)
  implicit val clinicalProblemsEncounteredJsonFormat: RootJsonFormat[ClinicalProblemsEncountered] = jsonFormat1(ClinicalProblemsEncountered)
  implicit val significantFindingsJsonFormat: RootJsonFormat[SignificantFindings] = jsonFormat1(SignificantFindings)
  implicit val dischargeDiagnosisJsonFormat: RootJsonFormat[DischargeDiagnosis] = jsonFormat1(DischargeDiagnosis)
  implicit val followUpInstructionsJsonFormat: RootJsonFormat[FollowUpInstructions] = jsonFormat1(FollowUpInstructions)
  implicit val postHospitalCareInformationJsonFormat: RootJsonFormat[PostHospitalCareInformation] = jsonFormat1(PostHospitalCareInformation)
  implicit val sdoCompilationJsonFormat: RootJsonFormat[SdoCompilation] = jsonFormat1(SdoCompilation)
  implicit val pharmacologicalTherapyJsonFormat: RootJsonFormat[PharmacologicalTherapy] = jsonFormat5(PharmacologicalTherapy)
  implicit val pharmacologicalTherapiesJsonFormat: RootJsonFormat[PharmacologicalTherapies] = jsonFormat1(PharmacologicalTherapies)
  implicit val dischargeLetterJsonFormat: RootJsonFormat[DischargeLetter] = jsonFormat7(DischargeLetter)

}
