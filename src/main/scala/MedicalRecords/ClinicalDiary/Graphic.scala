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

package MedicalRecords.ClinicalDiary

import MedicalRecords.ClinicalDiary.VitalSigns.VitalSigns
import MedicalRecords.InitialAnalysis.Info

import java.time.LocalDate

//TODO: Aggiungere anche orario oltre che data.
case class VitalSign(info: Info, date: LocalDate = LocalDate.now())


object VitalSigns {

  case class VitalSigns private(vitalSigns: Set[VitalSign] = Set.empty) {
    def addNewPVitalSign(vitalSign: VitalSign): VitalSigns = VitalSigns(this.vitalSigns + vitalSign)
  }

  def apply(): VitalSigns = VitalSigns()
}

case class Graphic(vitalSigns: VitalSigns)

