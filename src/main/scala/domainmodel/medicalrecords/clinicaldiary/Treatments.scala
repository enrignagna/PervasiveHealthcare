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

package domainmodel.medicalrecords.clinicaldiary

import domainmodel.professionalfigure.Doctor
import domainmodel.utility.Description

import java.time.LocalDate

/**
 * General treatment.
 *
 * @param date        date of treatment.
 * @param description description of treatment.
 * @param doctor      doctor that has made treatment.
 */
class Treatment(date: LocalDate, description: Description, doctor: Doctor)

//TODO: Pensavo all'ereditarietà, ma ho visto anche questa soluzione
/**
 * Diagnostic Treatments
 *
 * @param treatment treatment executed.
 */
case class DiagnosticTreatments(treatment: Treatment)

/**
 * Therapeutic Treatments
 *
 * @param treatment treatment executed.
 */
case class TherapeuticTreatments(treatment: Treatment)

/**
 * Rehabilitation Treatments
 *
 * @param treatment treatment executed.
 */
case class RehabilitationTreatments(treatment: Treatment)