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

package cqrs.writemodel

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class WriteModelTest extends AnyFreeSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  /* val doctorID = UUID.randomUUID().toString
   val patientID = UUID.randomUUID().toString

   val patientResponse = Repository.adminRepository.insertPatient(Patient.Patient(
     PatientID(patientID),
     CF("aaa"),
     "Luca",
     "Verdi",
     LocalDate.of(1990, 11, 22),
     "Cesena",
     Gender.MALE,
     "054488888",
     Some("33355548"),
     "Via Cerchia 20",
     "...",
     "Cesena",
     "FC",
     None,
     None,
     None,
     None
   ))

   val surgeonResponse = Repository.adminRepository.insertSurgeon(Surgeon(
     DoctorID(doctorID),
     "Luca",
     "Manfredi",
     "123546",
     "email",
     "medicalDegreeGrade",
     GENERAL_SURGERY
   ))


   val medicalRecordResponse = Repository.surgeonRepository.insertMedicalRecord(
     MedicalRecord(
       DoctorID(doctorID),
       PatientID(patientID),
       MedicalRecordsID("1"),
       isClosed = false,
       None, None, None,
       None, None, None,
       None, None, None,
       None, None, None, None
     )
   )
   val updateMedicalRecordResponse = Repository.surgeonRepository.updateMedicalRecord(
     MedicalRecordsID("1"),
     MedicalRecord(
       DoctorID(doctorID),
       PatientID(patientID),
       MedicalRecordsID("1"),
       isClosed = true,
       None, None, None,
       None, None, None,
       None, None, None,
       None, None, None, None
     )

   )

   "Write model should ensure that all all update or insert operations are completed successfully" - {
     "a surgeon response must be successful" in {
       assert(surgeonResponse == "Surgeon created.")
     }
     "a patient response must be successful" in {
       assert(patientResponse == "Patient created.")
     }
     " a medical record response must be successful" in {
       assert(medicalRecordResponse == "Medical record created.")
     }
     "and all medical records for a doctor" in {
       assert(updateMedicalRecordResponse == "Medical record updated.")
     }

   }*/
}