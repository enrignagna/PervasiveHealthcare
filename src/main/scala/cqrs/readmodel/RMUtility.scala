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

import cqrs.readmodel.eventsourcing.{EventStore, insertSurgeonEvent, removeSurgeonEvent, updateSurgeonEvent}
import domainmodel.professionalfigure.{DoctorID, Surgeon}

object RMUtility {
  def recreateSurgeonState(doctorID: DoctorID): Option[Surgeon] = {
    val events = EventStore.getEvents(doctorID)
    var user: Surgeon = null
    events.foreach {
      case x: insertSurgeonEvent => user = x.surgeon
      case x: updateSurgeonEvent => user = x.surgeon
      case _: removeSurgeonEvent => user = null
    }
    Option(user)
  }
}
