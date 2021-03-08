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
import domainmodel.generalpractitionerinfo.Prescriptions.Prescriptions
import org.junit.runner.RunWith
import org.scalatest.freespec._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PrescriptionTest extends AnyFreeSpec {
  val prescription: Prescription = Prescription(PrescriptionInitialDate(), PrescriptionDescription("First prescription"))
  "A prescription should have" - {
    "a date" in {
      assert(prescription.prescriptionInitialDate != null)
    }
    "a description" in {
      assert(prescription.description != null)
    }
  }

  "A prescription date should not be after the current date" in {
    prescription.prescriptionInitialDate.prescriptionInitialDate.isAfter(LocalDate.now())
  }

  val prescriptions: Prescriptions = Prescriptions()
  "A prescriptions" - {
    "should be initially empty" in {
      assert(prescriptions.prescriptions.isEmpty)
    }

    "can added" in {
      assert(prescriptions.addNewPrescription(prescription).prescriptions.nonEmpty)
    }
  }

}