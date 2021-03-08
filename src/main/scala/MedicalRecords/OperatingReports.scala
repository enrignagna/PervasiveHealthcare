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

import MedicalRecords.Medical.Medical
import Utility.Note

import java.time.LocalDateTime


//TODO: Implement this.
// chiedere parere su compound types o se tenere sempre delle case class

case class DoctorReport(doctor: Doctor, note: Note)

//TODO Make implementation of doctor or of every single type of doctor.
case class AnesthetistsReport(doctor: Anesthetists, note: Note)

case class InstrumentalistReport(doctor: Instrumentalist, note: Note)


object Medical {

  case class Medical private(doctors: Set[DoctorReport] = Set.empty,
                             anesthetists: Set[AnesthetistsReport] = Set.empty,
                             instrumentalists: Set[InstrumentalistReport] = Set.empty) {


    def addNewDoctor(doctor: DoctorReport): Medical =
      Medical(this.doctors + doctor, anesthetists, instrumentalists)

    def addNewAnesthetist(anesthetist: AnesthetistsReport): Medical =
      Medical(doctors, this.anesthetists + anesthetist, instrumentalists)

    def addNewInstrumentalist(instrumentalist: InstrumentalistReport): Medical =
      Medical(doctors, anesthetists, this.instrumentalists + instrumentalist)

  }

  /**
   * Apply method.
   *
   * @return collection of single sheet therapy.
   */
  def apply(): Medical = Medical()
}

case class InterventionType(value: String)

case class OperatingReports(medical: Medical,
                            datetime: LocalDateTime = LocalDateTime.now(),
                            interventionType: InterventionType) {

}
