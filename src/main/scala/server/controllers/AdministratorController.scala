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
import domainmodel.professionalfigure.{Doctor, Surgeon}

object AdministratorController {
  // actor protocol
  sealed trait Command
  final case class CreateSurgeon(surgeon: Surgeon, replyTo: ActorRef[CreateSurgeonResponse]) extends Command

  final case class CreateSurgeonResponse(maybeUser: Option[Surgeon])

  def apply(): Behavior[Command] = registry()

  private def registry(): Behavior[Command] =
    Behaviors.receiveMessage {
      case CreateSurgeon(surgeon, replyTo) =>
        //TODO check inside the db and than response
        replyTo ! CreateSurgeonResponse(Some(surgeon))
        Behaviors.same
    }
}
