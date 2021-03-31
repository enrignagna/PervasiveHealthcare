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
import domainmodel.professionalfigure.{Anesthetist, DoctorID, GeneralPractitioner, Instrumentalist, Rescuer, Surgeon, Surgeons, WardNurse}
import server.models.Protocol._

object AdministratorController {

  final case class State(items: Surgeons) {
    def insertSurgeon(surgeon: Surgeon): State = {
      ReadModel().createSurgeon(surgeon)
      State(Surgeons(items.surgeons + surgeon))
    }
    def updateSurgeon(surgeon: Surgeon): State = {
      ReadModel().updateSurgeon(surgeon)
      State(Surgeons((items.surgeons - items.surgeons.find(x => x.doctorID equals surgeon.doctorID).get) + surgeon))
    }
    def removeSurgeon(surgeon: Surgeon): State = {
      ReadModel().removeSurgeon(surgeon)
      State(Surgeons((items.surgeons - items.surgeons.find(x => x.doctorID equals surgeon.doctorID).get)))
    }
    def insertAnesthetist(anesthetist: Anesthetist): State = {
     // ReadModel().createAnesthetist(anesthetist)
      State(Surgeons(items.surgeons))
    }
    def updateAnesthetist(anesthetist: Anesthetist): State = {
      //ReadModel().updateAnesthetist(anesthetist)
      State(Surgeons(items.surgeons))
    }
    def insertInstrumentalist(instrumentalist: Instrumentalist): State = {
      // ReadModel().createInstrumentalist(instrumentalist)
      State(Surgeons(items.surgeons))
    }
    def updateInstrumentalist(instrumentalist: Instrumentalist): State = {
      //ReadModel().updateInstrumentalist(instrumentalist)
      State(Surgeons(items.surgeons))
    }
    def insertGeneralPractitioner(generalPractitioner: GeneralPractitioner): State = {
      // ReadModel().createGeneralPractitioner(generalPractitioner)
      State(Surgeons(items.surgeons))
    }
    def updateGeneralPractitioner(generalPractitioner: GeneralPractitioner): State = {
      //ReadModel().updateGeneralPractitioner(generalPractitioner)
      State(Surgeons(items.surgeons))
    }
    def insertRescuer(rescuer: Rescuer): State = {
      // ReadModel().createRescuer(rescuer)
      State(Surgeons(items.surgeons))
    }
    def updateRescuer(rescuer: Rescuer): State = {
      //ReadModel().updateRescuer(rescuer)
      State(Surgeons(items.surgeons))
    }
    def insertWardNurse(wardNurse: WardNurse): State = {
      // ReadModel().createWardNurse(wardNurse)
      State(Surgeons(items.surgeons))
    }
    def updateWardNurse(wardNurse: WardNurse): State = {
      //ReadModel().updateWardNurse(wardNurse)
      State(Surgeons(items.surgeons))
    }
   /* def insertPatient(patient: Patient): State = {
      // ReadModel().createPatient(patient)
      State(Surgeons(items.surgeons))
    }
    def updatePatient(patient: Patient): State = {
      //ReadModel().updatePatient(patient)
      State(Surgeons(items.surgeons))
    }*/
  }
  object State {
    var state: State = State(Surgeons())
  }

  def apply(): Behavior[Command] = handleCommand()
  def handleCommand(): Behavior[Command] =
    Behaviors.receiveMessage {
      case InsertSurgeon(surgeon, replyTo) =>
        val res = Repository.adminRepository.insertSurgeon(surgeon)
        if (res == "Surgeon created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          State.state = State.state.insertSurgeon(surgeon)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateSurgeon(id, surgeon, replyTo) =>
        val res = Repository.adminRepository.updateSurgeon(DoctorID(id), surgeon)
        if (res == "Surgeon updated.") {
          State.state = State.state.updateSurgeon(surgeon)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertAnesthetist(anesthetist, replyTo) =>
        val res = Repository.adminRepository.insertAnesthetist(anesthetist)
        if (res == "Anesthetist created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          State.state = State.state.insertAnesthetist(anesthetist)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateAnesthetist(id, anesthetist, replyTo) =>
        val res = Repository.adminRepository.updateAnesthetist(DoctorID(id), anesthetist)
        if (res == "Anesthetist updated.") {
          State.state = State.state.updateAnesthetist(anesthetist)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertInstrumentalist(instrumentalist, replyTo) =>
        val res = Repository.adminRepository.insertInstrumentalist(instrumentalist)
        if (res == "Instrumentalist created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          State.state = State.state.insertInstrumentalist(instrumentalist)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateInstrumentalist(id, instrumentalist, replyTo) =>
        val res = Repository.adminRepository.updateInstrumentalist(DoctorID(id), instrumentalist)
        if (res == "Instrumentalist updated.") {
          State.state = State.state.updateInstrumentalist(instrumentalist)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertGeneralPractitioner(generalPractitioner, replyTo) =>
        val res = Repository.adminRepository.insertGeneralPractitioner(generalPractitioner)
        if (res == "General practitioner created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          State.state = State.state.insertGeneralPractitioner(generalPractitioner)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateGeneralPractitioner(id, generalPractitioner, replyTo) =>
        val res = Repository.adminRepository.updateGeneralPractitioner(DoctorID(id), generalPractitioner)
        if (res == "General practitioner updated.") {
          State.state = State.state.updateGeneralPractitioner(generalPractitioner)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertRescuer(rescuer, replyTo) =>
        val res = Repository.adminRepository.insertRescuer(rescuer)
        if (res == "Rescuer created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          State.state = State.state.insertRescuer(rescuer)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateRescuer(id, rescuer, replyTo) =>
        val res = Repository.adminRepository.updateRescuer(DoctorID(id), rescuer)
        if (res == "Rescuer updated.") {
          State.state = State.state.updateRescuer(rescuer)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case InsertWardNurse(wardnurse, replyTo) =>
        val res = Repository.adminRepository.insertWardNurse(wardnurse)
        if (res == "Ward nurse created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          State.state = State.state.insertWardNurse(wardnurse)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateWardNurse(id, wardNurse, replyTo) =>
        val res = Repository.adminRepository.updateWardNurse(DoctorID(id), wardNurse)
        if (res == "Ward nurse updated.") {
          State.state = State.state.updateWardNurse(wardNurse)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
     /* case InsertPatient(patient, replyTo) =>
        val res = Repository.adminRepository.insertPatient(patient)
        if (res == "Patient created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          State.state = State.state.insertPatient(patient)
          replyTo ! Accepted(res)
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdatePatient(id, patient, replyTo) =>
        val res = Repository.adminRepository.updatePatient(DoctorID(id), patient)
        if (res == "Patient updated.") {
          State.state = State.state.updatePatient(patient)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same*/
    }
}
