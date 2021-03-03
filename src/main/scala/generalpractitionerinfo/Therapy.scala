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

package generalpractitionerinfo

import java.time.LocalDate

case class TherapyDate(therapyDate: LocalDate = LocalDate.now())

case class TherapyDescription(therapyDescription: String)

case class TherapyInitialDate(therapyInitialDate: LocalDate = LocalDate.now())

case class TherapyFinalDate(therapyFinalDate: LocalDate = LocalDate.now())

case class Therapy(therapyDate: TherapyDate, therapyDescription: TherapyDescription, therapyInitialDate: TherapyInitialDate, therapyFinalDate: TherapyFinalDate)

object Therapies{
  case class Therapies private (therapies: Set[Therapy] = Set.empty){
    def addNewTherapy(therapy: Therapy): Therapies = Therapies(this.therapies + therapy)
  }

  def apply(): Therapies = Therapies()
}