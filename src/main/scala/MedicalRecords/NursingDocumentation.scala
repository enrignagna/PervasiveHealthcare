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

package MedicalRecords

import Utility.Description

import java.time.LocalDateTime

case class NursingDocumentation(
                                 registration: Registration,
                                 needsIdentification: NeedsIdentification,
                                 nursingInterventionPlanning: NursingInterventionPlanning,
                                 careDiary: CareDiary,
                                 interventionEvaluation: InterventionEvaluation
                               )

case class InterventionEvaluation(value: String)

case class CareDiary(value: String)

case class NursingInterventionPlanning(datetime: LocalDateTime = LocalDateTime.now(), description: Description)

case class NeedsIdentification(datetime: LocalDateTime = LocalDateTime.now(), description: Description)

//TODO: referenza di clinical data e copia dei dati personali. Finish implementation.
case class Registration(personalData: PersonalData, clinicalData: ClinicalData)


