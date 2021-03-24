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

/**
 * This class represents the set of the patient's medical certificates.
 *
 * @param medicalCertificateId, id of medical certificate
 * @param medicalCertificate, pdf of medical certificate.
 */
// TODO: Remove this class
class MedicalCertificatesOld(private val medicalCertificateId: Int,
                             private var medicalCertificate: Set[Byte]) {

  private val id= medicalCertificateId

  /**
   * Get medical certificate's id.
   */
  def getId: Int = id

  /**
   * Get medical certificate.
   */
  def getMedicalCertificate: Set[Byte] = medicalCertificate

  /**
   * Set new medical certificate.
   * @param newMedicalCertificate, new medical certificate.
   */
  def setMedicalCertificate(newMedicalCertificate: Set[Byte]): Unit = {
    medicalCertificate = newMedicalCertificate
  }

}

/**
 * Factory to create a medical certificates.
 */
object MedicalCertificatesOld {
  def apply(medicalCertificateId: Int, medicalCertificate: Set[Byte]): MedicalCertificatesOld =
    new MedicalCertificatesOld(medicalCertificateId, medicalCertificate)
}

