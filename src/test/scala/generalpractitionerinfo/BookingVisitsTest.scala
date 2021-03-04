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

import org.junit.runner.RunWith
import org.scalatest.freespec._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BookingVisitsTest extends AnyFreeSpec {
  val visit: Visit = Visit(VisitDate())
  val bookingVisits: BookingVisits = BookingVisits(1, visit, "visit for shoulder surgery")
  "A booking visits should have" - {
    "an id" in {
      assert(bookingVisits.getId == 1)
    }
    "a visit information" in {
      assert(bookingVisits.getVisit == visit)
    }
    "a description" in {
      assert(bookingVisits.getDescription == "visit for shoulder surgery")
    }
  }
}