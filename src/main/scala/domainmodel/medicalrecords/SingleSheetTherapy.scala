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

package domainmodel.medicalrecords

import domainmodel.professionalfigure.Doctor
import domainmodel.utility.Description

import java.time.LocalDateTime

/**
 * Single sheet therapy.
 *
 * @param drugsPrescription
 * @param drugsAdministered
 */
case class SingleSheetTherapy(drugsPrescription: DrugsPrescription, drugsAdministered: DrugsAdministered)

/**
 * Drugs prescription.
 *
 * @param doctor      doctor that made prescription.
 * @param description description of prescription.
 * @param datetime    date and time of prescription.
 */
case class DrugsPrescription(doctor: Doctor, description: Description, datetime: LocalDateTime = LocalDateTime.now())

/**
 * Drugs somministration.
 *
 * @param doctor      doctor that made administered.
 * @param description description of administered.
 * @param datetime    date and time of administered.
 */
case class DrugsAdministered(doctor: Doctor, description: Description, datetime: LocalDateTime = LocalDateTime.now())

/**
 * Collection of single sheet therapy.
 */
object SingleSheetTherapies {

  case class SingleSheetTherapies private(drugsPrescription: Set[DrugsPrescription] = Set.empty, drugsAdministered: Set[DrugsAdministered] = Set.empty) {

    /**
     * Add new drugs administered.
     *
     * @param drug drug to add.
     * @return collection of single sheet therapy.
     */
    def addNewDrugsAdministered(drug: DrugsAdministered): SingleSheetTherapies = SingleSheetTherapies(drugsPrescription, this.drugsAdministered + drug)

    /**
     * Add new drugs prescription.
     *
     * @param drug drug to add.
     * @return collection of single sheet therapy.
     */
    def addNewDrugsPrescription(drug: DrugsPrescription): SingleSheetTherapies = SingleSheetTherapies(this.drugsPrescription + drug, drugsAdministered)


  }

  /**
   * Apply method.
   *
   * @return collection of single sheet therapy.
   */
  def apply(): SingleSheetTherapies = SingleSheetTherapies()
}