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

package cqrs.readmodel

import akka.http.scaladsl.testkit.ScalatestRouteTest
import domainmodel._
import domainmodel.medicalrecords.{MedicalRecord, MedicalRecordsID}
import domainmodel.professionalfigure.Specialization._
import domainmodel.professionalfigure.Surgeon
import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

import java.time.LocalDate
import java.util.UUID

@RunWith(classOf[JUnitRunner])
class ReadModelTest extends AnyFreeSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  val doctorID = UUID.randomUUID().toString
  val patientID = UUID.randomUUID().toString

  ReadModel.createSurgeon(
    Surgeon(
      DoctorID(doctorID),
      "Luca",
      "Manfredi",
      "123546",
      "email",
      "medicalDegreeGrade",
      GENERAL_SURGERY
    ))

  ReadModel.insertPatient(
    Patient.Patient(
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
      None
    )
  )

  ReadModel.insertMedicalRecord(PatientID(patientID),
    MedicalRecord(
      DoctorID(doctorID),
      PatientID(patientID),
      MedicalRecordsID("1"),
      isClosed = false,
      None, None, None,
      None, None, None,
      None, None, None,
      None, None, None, None
    ))

  ReadModel.updateMedicalRecord(PatientID("000006"),
    MedicalRecord(
      DoctorID("1csachsd"),
      PatientID("000006"),
      MedicalRecordsID("1"),
      isClosed = true,
      None, None, None,
      None, None, None,
      None, None, None,
      None, None, None, None
    ))

  "Read model should ensure persitence, so it should have" - {
    "a doctor" in {
      assert(RMUtility.recreateSurgeonState(DoctorID(doctorID)).nonEmpty)
    }
    "a patient" in {
      assert(RMUtility.recreatePatientState(PatientID(patientID)).nonEmpty)
    }
    "and a medical record inside the patient" in {
      assert(RMUtility.recreatePatientState(PatientID(patientID)).get.medicalRecords.nonEmpty)
    }
  }
}