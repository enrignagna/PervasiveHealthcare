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

package cqrs.readmodel.eventsourcing

import domainmodel.professionalfigure.Surgeon

import java.util.{Date, UUID}


abstract class Event {
  final val id = UUID.randomUUID
  final val created = new Date
}


class insertSurgeonEvent(s: Surgeon) extends Event {
  final val surgeon: Surgeon = s
}

class updateSurgeonEvent(s: Surgeon) extends Event {
  final val surgeon: Surgeon = s
}

class removeSurgeonEvent(s: Surgeon) extends Event {
  final val surgeon: Surgeon = s
}