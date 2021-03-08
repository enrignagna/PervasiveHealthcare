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

package domainmodel

import domainmodel.PreviousPathologies.PreviousPathologies

import java.time.LocalDate

sealed trait KinshipDegree

case object Mother extends KinshipDegree
case object Father extends KinshipDegree
case object LegalTutor extends KinshipDegree

case class Familiar(name: String, kinshipDegree: KinshipDegree, previousPathologies: PreviousPathologies, phoneNumber: String)

case class Remote(info: String, date: LocalDate = java.time.LocalDate.now)

case class Physiologic(info: String, date: LocalDate = java.time.LocalDate.now)

/**
 * This class represents the anamnesis.
 * There are various steps that make up the anamnesis including family, remote and psychological anamnesis.
 */
case class Anamensis(familiar: Familiar, remote: Remote, physiologic: Physiologic)

/**
 * Factory to add a new remote anamnesi to the anamnesi's history.
 */
object Remotes {

  case class Remotes private(remotes: Set[Remote] = Set.empty) {
    def addNewRemote(remote: Remote): Remotes = Remotes(this.remotes + remote)
  }

  def apply(): Remotes = Remotes()
}

/**
 * Factory to add a new familiar anamnesi to the anamnesi's history.
 */
object Familiars {

  case class Familiars private(familiars: Set[Familiar] = Set.empty) {
    def addNewFamiliar(familiar: Familiar): Familiars = Familiars(this.familiars + familiar)
  }

  def apply(): Familiars = Familiars()
}