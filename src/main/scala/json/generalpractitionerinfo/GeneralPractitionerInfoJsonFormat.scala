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

package json.generalpractitionerinfo

import domainmodel.Anamnesis
import domainmodel.generalpractitionerinfo.BookingVisitHistory.BookingVisitHistory
import domainmodel.generalpractitionerinfo.PrescriptionHistory.PrescriptionHistory
import domainmodel.generalpractitionerinfo.TherapyHistory.TherapyHistory
import domainmodel.generalpractitionerinfo.VisitHistory.VisitHistory
import domainmodel.generalpractitionerinfo._
import domainmodel.professionalfigure.DoctorID
import json.AnamnesisJsonFormat.{familiarsJsonFormat, physiologicJsonFormat, remotesJsonFormat}
import json.RequestJsonFormats.{StringJsonFormat, immSetFormat, jsonFormat3, jsonFormat7}
import json.generalpractitionerinfo.BookingVisitsJsonFormat.bookingVisitJsonFormat
import json.generalpractitionerinfo.MedicalCertificatesJsonFormat.medicalCertificateHistoryJsonFormat
import json.generalpractitionerinfo.PrescriptionJsonFormat.prescriptionJsonFormat
import json.generalpractitionerinfo.TherapyJsonFormat.therapyJsonFormat
import json.generalpractitionerinfo.VisitJsonFormat.visitJsonFormat
import spray.json.DefaultJsonProtocol.jsonFormat1
import spray.json.RootJsonFormat

object GeneralPractitionerInfoJsonFormat {

  implicit val generalPractitionerInfoDoctorJsonFormat: RootJsonFormat[DoctorID] = jsonFormat1(DoctorID)

  implicit val generalPractitionerInfoVisitsJsonFormat: RootJsonFormat[VisitHistory] = jsonFormat1(VisitHistory)

  implicit val generalPractitionerInfoAnamnesisJsonFormat: RootJsonFormat[Anamnesis] = jsonFormat3(Anamnesis)

  implicit val generalPractitionerInfoBookingVisitsJsonFormat: RootJsonFormat[BookingVisitHistory] = jsonFormat1(BookingVisitHistory)

  implicit val generalPractitionerInfoPrescriptionsJsonFormat: RootJsonFormat[PrescriptionHistory] = jsonFormat1(PrescriptionHistory)

  implicit val generalPractitionerInfoTherapiesJsonFormat: RootJsonFormat[TherapyHistory] = jsonFormat1(TherapyHistory)

  implicit val generalPractitionerInfoJsonFormat: RootJsonFormat[GeneralPractitionerInfo] = jsonFormat7(GeneralPractitionerInfo)

}
