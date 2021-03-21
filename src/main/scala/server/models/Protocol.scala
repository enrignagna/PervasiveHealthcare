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
package server.models

import akka.actor.typed.ActorRef
import domainmodel.professionalfigure.{DoctorID, Surgeon}

object Protocol {

  sealed trait Command

  sealed trait Event

  sealed trait Confirmation

  final case class Accepted(description: String) extends Confirmation

  final case class Rejected(reason: String) extends Confirmation

  //Administrator protocol
  final case class InsertSurgeon(surgeon: Surgeon, replyTo: ActorRef[Confirmation]) extends Command

  final case class UpdateSurgeon(id: String, surgeon: Surgeon, replyTo: ActorRef[Confirmation]) extends Command

  final case class RemoveSurgeon(surgeon: Surgeon, replyTo: ActorRef[Confirmation]) extends Command

  final case class SurgeonAdded(surgeon: Surgeon) extends Event

  final case class SurgeonUpdated(id: DoctorID, surgeon: Surgeon) extends Event

  final case class SurgeonRemoved(surgeon: Surgeon) extends Event

}
