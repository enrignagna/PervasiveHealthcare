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

package MedicalRecords.InitialAnalysis

import MedicalRecords.InitialAnalysis.Familiars.Familiars
import Utility.Info

import java.time.LocalDate


sealed trait KinshipDegree

case object Mother extends KinshipDegree

case object Father extends KinshipDegree

case object LegalTutor extends KinshipDegree

case class Familiar(name: String, kinship: KinshipDegree, previousPathology: PreviousPathology)

object Familiars {

  case class Familiars private(familiars: Set[Familiar] = Set.empty) {
    def addNewFamiliars(familiar: Familiar): Familiars = Familiars(this.familiars + familiar)
  }

  def apply(): Familiars = Familiars()
}


case class Remote(info: Info, date: LocalDate = LocalDate.now)

case class Physiologic(info: Info, date: LocalDate = LocalDate.now)


case class Anamensis(familiars: Familiars, remote: Remote, physiologic: Physiologic) {

}