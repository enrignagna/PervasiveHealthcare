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

package client.patient

import client.Message

object Message {

  /**
   * Patient login message.
   *
   * @param id       , id of user.
   * @param password , password of user
   */
  case class PatientLoginMessage(id: String, password: String) extends Message

  /**
   * All Medical Record Message.
   */
  case class AllMedicalRecordMessage() extends Message

  /**
   * General Info Message.
   */
  case class GeneralInfoMessage() extends Message

  /**
   * General Practitioner Info Message.
   */
  case class GeneralPractitionerInfoMessage() extends Message


}
