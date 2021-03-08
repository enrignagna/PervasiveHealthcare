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

import domainmodel.generalpractitionerinfo.Prescriptions.Prescriptions
import domainmodel.generalpractitionerinfo.Therapies.Therapies
import domainmodel.generalpractitionerinfo.Visits._

case class GeneralPractitionerInfoDoctor(doctorID: Int, name: String)

case class GeneralPractitionerInfoVisits(visit: Visits)

case class GeneralPractitionerInfoAnamnesis(anamensis: Anamensis)

case class GeneralPractitionerInfoBookingVisits(bookingVisits: BookingVisits)

case class GeneralPractitionerInfoPrescriptions(prescriptions: Prescriptions)

case class GeneralPractitionerInfoTherapies(therapies: Therapies)

/**
 * This class represents the information entered by the general practitioner for each patient.
 * @param doctor, doctor's information
 * @param visits, visit's information
 * @param anamnesis, anamnesis information
 * @param bookingVisits, booking visits information
 * @param prescriptions, prescriptions's information
 * @param generalPractitionerInfoTherapies, therapies's information.
 */
case class GeneralPractitionerInfo(doctor: GeneralPractitionerInfoDoctor, visits: GeneralPractitionerInfoVisits, anamnesis: GeneralPractitionerInfoAnamnesis, bookingVisits: GeneralPractitionerInfoBookingVisits, prescriptions: GeneralPractitionerInfoPrescriptions, generalPractitionerInfoTherapies: GeneralPractitionerInfoTherapies)

