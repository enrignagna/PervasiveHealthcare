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

import domainmodel.Anamnesis
import domainmodel.generalpractitionerinfo.BookingVisitHistory.BookingVisitHistory
import domainmodel.generalpractitionerinfo.MedicalCertificateHistory.MedicalCertificateHistory
import domainmodel.generalpractitionerinfo.PrescriptionHistory.PrescriptionHistory
import domainmodel.generalpractitionerinfo.TherapyHistory.TherapyHistory
import domainmodel.generalpractitionerinfo.VisitHistory._
import domainmodel.professionalfigure.DoctorID


/**
 * This class represents the information entered by the general practitioner for each patient.
 *
 * @param doctorID      , doctor's information
 * @param visits        , visit's information
 * @param anamnesis     , anamnesis information
 * @param bookingVisits , booking visits information
 * @param prescriptions , prescriptions's information
 * @param therapies     , therapies's information.
 * @param medicalCertificateHistory, medical certificate's information.
 */
case class GeneralPractitionerInfo(
                                    doctorID: DoctorID,
                                    visits: VisitHistory,
                                    anamnesis: Anamnesis,
                                    bookingVisits: BookingVisitHistory,
                                    prescriptions: PrescriptionHistory,
                                    therapies: TherapyHistory,
                                    medicalCertificateHistory: MedicalCertificateHistory
                                  )

