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

package cqrs.readmodel

import cqrs.readmodel.eventsourcing._
import domainmodel.Patient.{Patient, updateGeneralInfo, updateGeneralPractitionerInfo, updateMedicalRecords}
import domainmodel.professionalfigure._
import domainmodel.{DoctorID, PatientID}

object RMUtility {
  /**
   * Method to get surgeon from events.
   *
   * @param doctorID , id of the surgeon.
   * @return required surgeon.
   */
  def recreateSurgeonState(doctorID: DoctorID): Option[Surgeon] = {
    val events = EventStore.getEvents(doctorID)
    var user: Surgeon = null
    events.foreach {
      case x: InsertSurgeonEvent => user = x.s
      case x: UpdateSurgeonEvent => user = x.s
    }
    Option(user)
  }

  /**
   * Method to get instrumentalist from events.
   *
   * @param doctorID , id of the instrumentalist.
   * @return required instrumentalist.
   */
  def recreateInstrumentalistState(doctorID: DoctorID): Option[Instrumentalist] = {
    val events = EventStore.getEvents(doctorID)
    var user: Instrumentalist = null
    events.foreach {
      case x: InsertInstrumentalistEvent => user = x.i
      case x: UpdateInstrumentalistEvent => user = x.i
    }
    Option(user)
  }

  /**
   * Method to get general practitioner from events.
   *
   * @param doctorID , id of the general practitioner.
   * @return required general practitioner.
   */
  def recreateGeneralPractitionerState(doctorID: DoctorID): Option[GeneralPractitioner] = {
    val events = EventStore.getEvents(doctorID)
    var user: GeneralPractitioner = null
    events.foreach {
      case x: InsertGeneralPractitionerEvent => user = x.g
      case x: UpdateGeneralPractitionerEvent => user = x.g
    }
    Option(user)
  }

  /**
   * Method to get anesthetist from events.
   *
   * @param doctorID , id of the anesthetist.
   * @return required anesthetist.
   */
  def recreateAnesthetistState(doctorID: DoctorID): Option[Anesthetist] = {
    val events = EventStore.getEvents(doctorID)
    var user: Anesthetist = null
    events.foreach {
      case x: InsertAnesthetistEvent => user = x.a
      case x: UpdateAnesthetistEvent => user = x.a
    }
    Option(user)
  }

  /**
   * Method to get ward nurse from events.
   *
   * @param doctorID , id of the ward nurse.
   * @return required ward nurse.
   */
  def recreateWardNurseState(doctorID: DoctorID): Option[WardNurse] = {
    val events = EventStore.getEvents(doctorID)
    var user: WardNurse = null
    events.foreach {
      case x: InsertWardNurseEvent => user = x.w
      case x: UpdateWardNurseEvent => user = x.w
    }
    Option(user)
  }

  /**
   * Method to get rescuer from events.
   *
   * @param doctorID , id of the rescuer.
   * @return required rescuer.
   */
  def recreateRescuerState(doctorID: DoctorID): Option[Rescuer] = {
    val events = EventStore.getEvents(doctorID)
    var user: Rescuer = null
    events.foreach {
      case x: InsertRescuerEvent => user = x.r
      case x: UpdateRescuerEvent => user = x.r
    }
    Option(user)
  }

  /**
   * Method to get patient from events.
   *
   * @param patientID , id of the patient.
   * @return required patient
   */
  def recreatePatientState(patientID: PatientID): Option[Patient] = {
    val events = EventStore.getEvents(patientID)
    var user: Patient = null
    events.foreach {
      case x: InsertPatientInfoEvent => user = x.p
      case x: UpdatePatientInfoEvent => user = x.p
      case x: InsertMedicalRecordEvent => user = updateMedicalRecords(user, x.m)
      case x: UpdateMedicalRecordEvent => user = updateMedicalRecords(user, x.m)
      case x: InsertGeneralInfoEvent => user = updateGeneralInfo(user, x.g)
      case x: UpdateGeneralInfoEvent => user = updateGeneralInfo(user, x.g)
      case x: InsertGeneralPractitionerInfoEvent => user = updateGeneralPractitionerInfo(user, x.g)
      case x: UpdateGeneralPractitionerInfoEvent => user = updateGeneralPractitionerInfo(user, x.g)
    }
    Option(user)
  }
}
