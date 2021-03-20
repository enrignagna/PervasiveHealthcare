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
import akka.persistence.typed.PersistenceId
import akka.persistence.typed.scaladsl.{Effect, EventSourcedBehavior}
import cqrs.AdminCRUD
import cqrs.readmodel.ReadModel
import domainmodel.professionalfigure.{DoctorID, Surgeon, Surgeons}
import server.models.Protocol._

object AdministratorController {


  final case class State(items: Surgeons) {

    def insertSurgeon(surgeon: Surgeon): State = {
      ReadModel().createSurgeon(surgeon)
      State(Surgeons(items.surgeons + surgeon))
    }

    def updateSurgeon(id: DoctorID, surgeon: Surgeon): State = {
      ReadModel().updateSurgeon(surgeon)
      State(Surgeons((items.surgeons - items.surgeons.find(x => x.doctorID equals id).get) + surgeon))
    }

    def removeSurgeon(surgeon: Surgeon): State = {
      ReadModel().removeSurgeon(surgeon)
      State(Surgeons((items.surgeons - items.surgeons.find(x => x.doctorID equals surgeon.doctorID).get)))
    }
  }

  object State {
    val empty: State = State(Surgeons())
  }

  def apply(id: String): Behavior[Command] = {
    EventSourcedBehavior[Command, Event, State](
      PersistenceId("AdministratorController", id), // PersistenceId is the identifier of actor
      State.empty, // define the state when the entity is first created
      (state, command) => handleCommand(state, command), //command handler. Produces effects (persisting event, ecc)
      (state, event) => handleEvent(state, event)) //Handle to change the state after handle command. Returns a new state
  }

  def handleCommand(state: State, command: Command): Effect[Event, State] =
    command match {
      case InsertSurgeon(surgeon, replyTo) =>
        val res = AdminCRUD.insertSurgeon(surgeon)
        if (res == "Surgeon created.") { //if there is an error the events are not stored, otherwise the events will be stored.
          Effect
            .persist(SurgeonAdded(surgeon))
            .thenRun(_ => replyTo ! Accepted(res))

        } else {
          replyTo ! Rejected(res)
          Effect.none
        }
      case UpdateSurgeon(id, surgeon, replyTo) =>
        val res = AdminCRUD.updateSurgeon(DoctorID(id), surgeon)
        if (res == "Surgeon updated.") {
          Effect //if there is an error the events are not stored, otherwise the events will be stored.
            .persist(SurgeonUpdated(DoctorID(id), surgeon))
            .thenRun(_ => replyTo ! Accepted(res)) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
          Effect.none
        }
      case RemoveSurgeon(surgeon, replyTo) =>
        val res = AdminCRUD.removeSurgeon(surgeon)
        if (res == "Surgeon removed.") {
          Effect //if there is an error the events are not stored, otherwise the events will be stored.
            .persist(SurgeonRemoved(surgeon))
            .thenRun(_ => replyTo ! Accepted(res)) // actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
          Effect.none
        }
    }

  def handleEvent(state: State, event: Event): State =
    event match {
      case SurgeonAdded(surgeon) => state.insertSurgeon(surgeon)
      case SurgeonUpdated(id, surgeon) => state.updateSurgeon(id, surgeon)
      case SurgeonRemoved(surgeon) => state.removeSurgeon(surgeon)
    }

}
