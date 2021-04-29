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

package client.generalpractitioner

import client._
import domainmodel.generalpractitionerinfo.GeneralPractitionerInfo
import domainmodel.{DoctorID, PatientID}

object Message {

  /**
   * Insert General Practitioner Info Message.
   *
   * @param generalPractitionerInfo , general practitioner info.
   */
  case class InsertGeneralPractitionerInfoMessage(generalPractitionerInfo: GeneralPractitionerInfo) extends Message

  /**
   * Update General Practitioner Info Message.
   *
   * @param patientID               , id of patient.
   * @param generalPractitionerInfo , general practitioner info.
   */
  case class UpdateGeneralPractitionerInfoMessage(patientID: PatientID, generalPractitionerInfo: GeneralPractitionerInfo) extends Message

  /**
   * Get General Practitioner Info Message.
   *
   * @param doctorID , id of doctor.
   */
  case class GetGeneralPractitionerInfoMessage(doctorID: DoctorID) extends Message

  /**
   * Update Cardiology Prediction Message.
   */
  case class UpdateCardiologyPredictionMessage() extends Message

  /**
   * Get Cardiology Prediction Message.
   */
  case class GetCardiologyPredictionMessage() extends Message

}
