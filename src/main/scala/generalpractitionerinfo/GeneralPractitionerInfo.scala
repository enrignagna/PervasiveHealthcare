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

import generalpractitionerinfo.Prescriptions.Prescriptions
import generalpractitionerinfo.Therapies.Therapies
import generalpractitionerinfo.Visits._

class GeneralPractitionerInfo(private var doctorId:Int,
                              private var visits: Visits,
                              private var anamensis: Anamensis,
                              private var bookingVisits: BookingVisits,
                              private var prescriptions: Prescriptions,
                              private var therapies: Therapies) {

  def createGeneralPractitionerInfo(doctorId: Int, visit: Visit, anamensis: Anamensis, bookingVisits: BookingVisits, prescription: Prescription, therapy: Therapy): GeneralPractitionerInfo =
  new GeneralPractitionerInfo(doctorId, Visits().addNewVisit(visit), Anamensis(anamensis.familiar, anamensis.remote, anamensis.physiologic), BookingVisits(???, visit, visit.visitDate.visitDate, ???), Prescriptions().addNewPrescription(prescription), Therapies().addNewTherapy(therapy))

}
