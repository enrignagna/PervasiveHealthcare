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

class MedicalCertificates(private val medicalCertificateId: Int,
                          private var medicalCertificate: Set[Byte]) {

  private val id= medicalCertificateId

  def getId: Int = id

  def getMedicalCertificate: Set[Byte] = medicalCertificate

  def setMedicalCertificate(newMedicalCertificate: Set[Byte]): Unit = {
    medicalCertificate = newMedicalCertificate
  }

}

object MedicalCertificates {
  def apply(medicalCertificateId: Int, medicalCertificate: Set[Byte]): MedicalCertificates =
    new MedicalCertificates(medicalCertificateId, medicalCertificate)
}

