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

import domainmodel.PreviousPathologies.PreviousPathologies
import domainmodel.generalpractitionerinfo.Visits.Visits
import org.junit.runner.RunWith
import org.scalatest.freespec._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GeneralPractitionerInfoTest extends AnyFreeSpec {
  val visit: Visit = Visit(VisitDate())
  val generalVisit: GeneralPractitionerInfoVisits = GeneralPractitionerInfoVisits(Visits().addNewVisit(visit))
  val anamnesis: GeneralPractitionerInfoAnamnesis = GeneralPractitionerInfoAnamnesis(Anamensis(Familiar("Rossi Mario", Father, PreviousPathologies(), "3387514876"), Remote("first anamnesis"), Physiologic("self conscious")))
  val prescription: GeneralPractitionerInfoPrescriptions = GeneralPractitionerInfoPrescriptions(Prescriptions().addNewPrescription(Prescription(PrescriptionInitialDate(), PrescriptionDescription("First prescription"))))
  val therapy: GeneralPractitionerInfoTherapies = GeneralPractitionerInfoTherapies(Therapies().addNewTherapy(Therapy(TherapyDate(), TherapyDescription("Therapy for shoulder surgery"), TherapyInitialDate(LocalDate.of(2021, 5, 15)), TherapyFinalDate(LocalDate.of(2021, 6, 15)))))
  val bookingVisits: GeneralPractitionerInfoBookingVisits = GeneralPractitionerInfoBookingVisits(BookingVisits(1, visit, "visit for shoulder surgery"))
  val doctor: GeneralPractitionerInfoDoctor = GeneralPractitionerInfoDoctor(1, "Giorgio Verdi")
  val generalPractitionerInfo: GeneralPractitionerInfo = GeneralPractitionerInfo(doctor, generalVisit, anamnesis, bookingVisits, prescription, therapy)

  "A general practitioner info should have" - {
    "a practitioner info" in {
      assert(generalPractitionerInfo.doctor != null)
    }
    "a visit information" in {
      assert(generalPractitionerInfo.visits != null)
    }
    "an anamnesis info" in {
      assert(generalPractitionerInfo.anamnesis != null)
    }
    "an booking visits info" in {
      assert(generalPractitionerInfo.bookingVisits != null)
    }
    "an prescription info" in {
      assert(generalPractitionerInfo.prescriptions != null)
    }
    "an therapy info" in {
      assert(generalPractitionerInfo.generalPractitionerInfoTherapies != null)
    }
  }
}

