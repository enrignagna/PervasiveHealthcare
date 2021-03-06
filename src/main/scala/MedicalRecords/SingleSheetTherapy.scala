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

package MedicalRecords

import Utility.Description

import java.time.LocalDateTime

/**
 * Single sheet therapy.
 *
 * @param drugsPrescription
 * @param drugsSomministration
 */
case class SingleSheetTherapy(drugsPrescription: DrugsPrescription, drugsSomministration: DrugsSomministration)

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
 * @param doctor      doctor that made somministration.
 * @param description description of somministration.
 * @param datetime    date and time of somministration.
 */
case class DrugsSomministration(doctor: Doctor, description: Description, datetime: LocalDateTime = LocalDateTime.now())

/**
 * Collection of single sheet therapy.
 */
object SingleSheetTherapy {

  case class SingleSheetTherapy private(drugsPrescription: Set[DrugsPrescription] = Set.empty, drugsSomministration: Set[DrugsSomministration] = Set.empty) {

    /**
     * Add new drugs somministration.
     *
     * @param drug drug to add.
     * @return collection of single sheet therapy.
     */
    def addNewDrugsSomministration(drug: DrugsSomministration): SingleSheetTherapy = SingleSheetTherapy(drugsPrescription, this.drugsSomministration + drug)

    /**
     * Add new drugs prescription.
     *
     * @param drug drug to add.
     * @return collection of single sheet therapy.
     */
    def addNewDrugsPrescription(drug: DrugsPrescription): SingleSheetTherapy = SingleSheetTherapy(this.drugsPrescription + drug, drugsSomministration)


  }

  /**
   * Apply method.
   *
   * @return collection of single sheet therapy.
   */
  def apply(): SingleSheetTherapy = SingleSheetTherapy()
}