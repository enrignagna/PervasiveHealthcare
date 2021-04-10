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

package cqrs.writemodel

import java.util.concurrent.TimeUnit

import cqrs.writemodel.WriteModel.{dischargeLetterCollection, doctorsCollection, medicalRecordsCollection, patientsCollection}
import domainmodel.{PatientID, User}
import domainmodel.generalinfo.GeneralInfo
import domainmodel.medicalrecords.{DischargeLetter, MedicalRecord, MedicalRecordsID}
import json.medicalrecords.MedicalRecordJsonFormat.medicalRecordJsonFormat
import json.generalinfo.GeneralInfoJsonFormat.generalInfoJsonFormat
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters.equal
import spray.json.enrichAny
import json.IDJsonFormat.patientIDJsonFormat
import json.medicalrecords.MedicalRecordJsonFormat.medicalRecordsIDJsonFormat

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class WardNurseCRUD {

  def insertMedicalRecord(medicalRecord: MedicalRecord): String = {
    val document: BsonDocument = BsonDocument.apply(medicalRecord.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    val patient = Await.result(patientsCollection.find(equal("patientID", medicalRecord.patientID)).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty && patient.nonEmpty) {
      Await.result(medicalRecordsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(patientsCollection.findOneAndUpdate(equal("patientID", BsonDocument.apply(medicalRecord.patientID.toJson.compactPrint)), document)
        .toFuture(),
        Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(medicalRecord.doctorID.value, "doctor"), Role.SURGEON), Duration(1, TimeUnit.SECONDS))
      "Medical record created."
    } else {
      "Error! This medical record already exists!"
    }
  }

  def updateMedicalRecord(medicalRecordID: MedicalRecordsID, medicalRecord: MedicalRecord): String = {
    val document: BsonDocument = BsonDocument.apply(medicalRecord.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(medicalRecordID.toJson.compactPrint)
    Await.result(medicalRecordsCollection.findOneAndReplace(
      equal("medicalRecordID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    Await.result(patientsCollection.findOneAndUpdate(
      equal("patientID", medicalRecord.patientID), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "Medical record updated."
  }

  def updateGeneralInfo(patientID: PatientID, generalInfo: GeneralInfo): String = {
    val document: BsonDocument = BsonDocument.apply(generalInfo.toJson.compactPrint)

    Await.result(patientsCollection.findOneAndUpdate(
      equal("patientID", patientID), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "General info updated."
  }

}
