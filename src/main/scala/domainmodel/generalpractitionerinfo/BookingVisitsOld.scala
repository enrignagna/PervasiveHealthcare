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

package domainmodel.generalpractitionerinfo

import java.time.LocalDate

/**
 * This class represents the history of the visits that make up the patient's clinical history.
 *
 * @param bookingId, booking id
 * @param visit, visit's information
 * @param description, visit's description
 * @param visitTime, visit's date.
 */
class BookingVisitsOld(private val bookingId: Int,
                       private var visit: Visit,
                       private var description: String,
                       private var visitTime: LocalDate) {

  private val id = bookingId

  /**
   * Get booking visit's id.
   */
  def getId: Int = id

  /**
   * Get booking visit's visit.
   */
  def getVisit: Visit = visit

  /**
   * Get a visit date.
   */
  def getVisitDate: LocalDate = visitTime

  /**
   * Get booking visit's description.
   */
  def getDescription: String = description

  /**
   * Set a new visit.
   * @param newVisit, new visit.
   */
  def setVisit(newVisit: Visit): Unit = {
    visit = newVisit
  }

  /**
   * Set booking visit's date.
   * @param newVisitTime, date of the visit.
   */
  def setVisitTime(newVisitTime: LocalDate): Unit = {
    visitTime = newVisitTime
  }

  /**
   * Set booking visit's description.
   * @param newDescription, description.
   */
  def setDescription(newDescription: String): Unit = {
    description = newDescription
  }

}

/**
 * Factory to create a booking visits.
 */
object BookingVisitsOld {
  def apply(bookingId: Int, visit: Visit, description: String, visitTime: LocalDate = java.time.LocalDate.now): BookingVisitsOld =
    new BookingVisitsOld(bookingId, visit, description, visitTime)
}

