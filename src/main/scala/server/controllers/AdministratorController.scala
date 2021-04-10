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

package server.controllers

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import cqrs.readmodel.ReadModel
import cqrs.writemodel.Repository
import domainmodel.{DoctorID, PatientID}
import server.models.Protocol._

object AdministratorController {

  def apply(): Behavior[Command] = handleCommand()

  def handleCommand(): Behavior[Command] =
    Behaviors.receiveMessage {
      case InsertSurgeon(surgeon, replyTo) =>
        val res = Repository.adminRepository.insertSurgeon(surgeon)
        if (res == "Surgeon created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          ReadModel.createSurgeon(surgeon)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateSurgeon(id, surgeon, replyTo) =>
        val res = Repository.adminRepository.updateSurgeon(DoctorID(id), surgeon)
        if (res == "Surgeon updated.") {
          ReadModel.updateSurgeon(surgeon)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertAnesthetist(anesthetist, replyTo) =>
        val res = Repository.adminRepository.insertAnesthetist(anesthetist)
        if (res == "Anesthetist created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          // ReadModel().createAnesthetist(anesthetist)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateAnesthetist(id, anesthetist, replyTo) =>
        val res = Repository.adminRepository.updateAnesthetist(DoctorID(id), anesthetist)
        if (res == "Anesthetist updated.") {
          //ReadModel().updateAnesthetist(anesthetist)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertInstrumentalist(instrumentalist, replyTo) =>
        val res = Repository.adminRepository.insertInstrumentalist(instrumentalist)
        if (res == "Instrumentalist created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          // ReadModel().createInstrumentalist(instrumentalist)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateInstrumentalist(id, instrumentalist, replyTo) =>
        val res = Repository.adminRepository.updateInstrumentalist(DoctorID(id), instrumentalist)
        if (res == "Instrumentalist updated.") {
          //ReadModel().updateInstrumentalist(instrumentalist)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertGeneralPractitioner(generalPractitioner, replyTo) =>
        val res = Repository.adminRepository.insertGeneralPractitioner(generalPractitioner)
        if (res == "General practitioner created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          // ReadModel().createGeneralPractitioner(generalPractitioner)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateGeneralPractitioner(id, generalPractitioner, replyTo) =>
        val res = Repository.adminRepository.updateGeneralPractitioner(DoctorID(id), generalPractitioner)
        if (res == "General practitioner updated.") {
          //ReadModel().updateGeneralPractitioner(generalPractitioner)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertRescuer(rescuer, replyTo) =>
        val res = Repository.adminRepository.insertRescuer(rescuer)
        if (res == "Rescuer created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          // ReadModel().createRescuer(rescuer)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateRescuer(id, rescuer, replyTo) =>
        val res = Repository.adminRepository.updateRescuer(DoctorID(id), rescuer)
        if (res == "Rescuer updated.") {
          //ReadModel().updateRescuer(rescuer)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertWardNurse(wardnurse, replyTo) =>
        val res = Repository.adminRepository.insertWardNurse(wardnurse)
        if (res == "Ward nurse created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          // ReadModel().createWardNurse(wardNurse)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateWardNurse(id, wardNurse, replyTo) =>
        val res = Repository.adminRepository.updateWardNurse(DoctorID(id), wardNurse)
        println("controller")
        if (res == "Ward nurse updated.") {
          //ReadModel().updateWardNurse(wardNurse)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertPatient(patient, replyTo) =>
        val res = Repository.adminRepository.insertPatient(patient)
        if (res == "Patient created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          // ReadModel().createPatient(patient)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdatePatient(id, patient, replyTo) =>
        val res = Repository.adminRepository.updatePatient(PatientID(id), patient)
        if (res == "Patient updated.") {
          //ReadModel().updatePatient(patient)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
    }
}
