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

package generalpractitionerinfo

import java.time.LocalDate

class BookingVisits(private val bookingId: Int,
                    private var visit: Visit,
                    private var description: String,
                    private var visitTime: LocalDate) {

  private val id = bookingId

  def getId: Int = id

  def getVisit: Visit = visit

  def getVisitDate: LocalDate = visitTime

  def getDescription: String = description

  def setVisit(newVisit: Visit): Unit = {
    visit = newVisit
  }

  def setVisitTime(newVisitTime: LocalDate): Unit = {
    visitTime = newVisitTime
  }

  def setDescription(newDescription: String): Unit = {
    description = newDescription
  }

}

object BookingVisits {
  def apply(bookingId: Int, visit: Visit, description: String, visitTime: LocalDate = java.time.LocalDate.now): BookingVisits =
    new BookingVisits(bookingId, visit, description, visitTime)
}

