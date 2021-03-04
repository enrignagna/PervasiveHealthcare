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

package generalinfo

import java.time.LocalDate

//TODO: check correctness of the date
case class PrescriptionDate(value: LocalDate = LocalDate.now())
case class PrescriptionInfo(value: String)

case class Prescription(prescriptionDate: PrescriptionDate, prescriptionInfo: PrescriptionInfo)


object PrescriptionHistory {

  case class PrescriptionHistory private(history: Set[Prescription] = Set.empty){
    def addNewPrescription(prescription: Prescription) : PrescriptionHistory = PrescriptionHistory(this.history + prescription)
  }

  def apply(): PrescriptionHistory = PrescriptionHistory()

}