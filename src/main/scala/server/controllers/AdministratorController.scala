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
import database.Repository
import domainmodel.professionalfigure.{DoctorID, Surgeon, Surgeons}
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

        /*
      case RemoveSurgeon(surgeon, replyTo) =>
        val res = Repository.adminRepository.removeSurgeon(surgeon)
        if (res == "Surgeon removed.") {
          State.state = State.state.removeSurgeon(surgeon)
          replyTo ! Accepted(res) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same

         */
    }
}
