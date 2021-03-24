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

package domainmodel.generalpractitionerinfo

import java.time.LocalDate

case class PrescriptionInitialDate(prescriptionInitialDate: LocalDate = LocalDate.now())

case class PrescriptionDescription(prescriptionDescription: String)

/**
 * This class represents the prescriptions prescribed by the general practitioner for the patient.
 */
case class Prescription(prescriptionInitialDate: PrescriptionInitialDate, description: PrescriptionDescription)

/**
 * Factory to add a new remote prescription to the prescriptions's history.
 */
object PrescriptionHistory {

  case class PrescriptionHistory private(history: Set[Prescription] = Set.empty) {
    def addNewPrescription(prescription: Prescription): PrescriptionHistory = PrescriptionHistory(this.history + prescription)
  }

  def apply(): PrescriptionHistory = PrescriptionHistory()
}