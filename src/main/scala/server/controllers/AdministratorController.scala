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

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import database.AdminCRUD
import domainmodel.professionalfigure.{Doctor, DoctorID, Surgeon}

//TODO move it to models
final case class Surgeons(surgeons: Set[Surgeon] = Set.empty)

object AdministratorController {
  // actor protocol
  sealed trait Command
  final case class GetSurgeons(replyTo: ActorRef[Surgeons]) extends Command
  final case class InsertSurgeon(surgeon: Surgeon, replyTo: ActorRef[ActionPerformed]) extends Command
  final case class UpdateSurgeon(id: String, surgeon: Surgeon, replyTo: ActorRef[ActionPerformed]) extends Command


  final case class ActionPerformed(description: String)

  def apply(): Behavior[Command] = registry()

  private def registry(): Behavior[Command] =
    Behaviors.receiveMessage {

      case InsertSurgeon(surgeon, replyTo) =>
        //TODO check inside the db and than response
        val res = AdminCRUD.insertSurgeon(surgeon)
        replyTo ! ActionPerformed(res)
        registry()
      case UpdateSurgeon(id, surgeon, replyTo) =>
        val res = AdminCRUD.updateSurgeon(DoctorID(id), surgeon)
        replyTo ! ActionPerformed(res)
        registry()
      case _ => registry()
    }
}
