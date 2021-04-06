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

import cqrs.writemodel.WriteModel.{doctorsCollection, medicalRecordsCollection, patientsCollection}
import domainmodel.User
import domainmodel.medicalrecords.clinicaldiary.ClinicalDiary
import domainmodel.medicalrecords.{DrugsAdministered, MedicalRecord, MedicalRecordsID, SingleSheetTherapies}
import json.IDJsonFormat.patientIDJsonFormat
import json.medicalrecords.MedicalRecordJsonFormat.{medicalRecordJsonFormat, medicalRecordsIDJsonFormat}
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters.equal
import spray.json.enrichAny

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class RescuerCRUD {

  def updateClinicalDiary(medicalRecordID: MedicalRecordsID, clinicalDiary: ClinicalDiary): String = {
    val id: BsonDocument = BsonDocument.apply(medicalRecordID.toJson.compactPrint)

    val oldMedicalRecordDocument = Await.result(medicalRecordsCollection.find(
      equal("medicalRecordID", id)).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (oldMedicalRecordDocument.nonEmpty && oldMedicalRecordDocument.size == 1) {
      val oldMedicalRecord = oldMedicalRecordDocument.head.asInstanceOf[MedicalRecord]
      val newMedicalRecord = MedicalRecord(oldMedicalRecord.doctorID, oldMedicalRecord.patientID, oldMedicalRecord.medicalRecordID, oldMedicalRecord.isClosed,
        oldMedicalRecord.initialAnalysis, Some(clinicalDiary), oldMedicalRecord.diagnosticServicesRequests, oldMedicalRecord.graphic,
        oldMedicalRecord.painReliefHistory, oldMedicalRecord.singleSheetTherapyHistory, oldMedicalRecord.adviceRequest, oldMedicalRecord.reports, oldMedicalRecord.operatingReports,
        oldMedicalRecord.nursingDocumentation, oldMedicalRecord.anesthesiologyRecord, oldMedicalRecord.medicalSurgicalDevices, oldMedicalRecord.dischargeLetter)


      val newMedicalRecordDocument: BsonDocument = BsonDocument.apply(newMedicalRecord.toJson.compactPrint)
      Await.result(medicalRecordsCollection.findOneAndReplace(
        equal("medicalRecordID", id), BsonDocument.apply(newMedicalRecord.toJson.compactPrint)).toFuture(),
        Duration(1, TimeUnit.SECONDS))
      Await.result(patientsCollection.findOneAndUpdate(
        equal("patientID", newMedicalRecord.patientID), newMedicalRecordDocument).toFuture(),
        Duration(1, TimeUnit.SECONDS))
      "Clinical diary updated."
    } else "Error! Medical record not exist"
  }

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

  def updateDrugAdministered(medicalRecordID: MedicalRecordsID, drugAdministered: DrugsAdministered): String = {
    val id: BsonDocument = BsonDocument.apply(medicalRecordID.toJson.compactPrint)

    val oldMedicalRecordDocument = Await.result(medicalRecordsCollection.find(
      equal("medicalRecordID", id)).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (oldMedicalRecordDocument.nonEmpty && oldMedicalRecordDocument.size == 1) {
      val oldMedicalRecord = oldMedicalRecordDocument.head.asInstanceOf[MedicalRecord]
      val oldSheetTherapy = oldMedicalRecord.singleSheetTherapyHistory

      val newSheetTherapy = if(oldSheetTherapy.isEmpty) Some(SingleSheetTherapies().addNewDrugsAdministered(drugAdministered)) else Some(oldSheetTherapy.get.addNewDrugsAdministered(drugAdministered))
      val newMedicalRecord = MedicalRecord(oldMedicalRecord.doctorID, oldMedicalRecord.patientID, oldMedicalRecord.medicalRecordID, oldMedicalRecord.isClosed,
        oldMedicalRecord.initialAnalysis, oldMedicalRecord.clinicalDiary, oldMedicalRecord.diagnosticServicesRequests, oldMedicalRecord.graphic,
        oldMedicalRecord.painReliefHistory, newSheetTherapy, oldMedicalRecord.adviceRequest, oldMedicalRecord.reports, oldMedicalRecord.operatingReports,
        oldMedicalRecord.nursingDocumentation, oldMedicalRecord.anesthesiologyRecord, oldMedicalRecord.medicalSurgicalDevices, oldMedicalRecord.dischargeLetter)

      val newMedicalRecordDocument: BsonDocument = BsonDocument.apply(newMedicalRecord.toJson.compactPrint)
      Await.result(medicalRecordsCollection.findOneAndReplace(
        equal("medicalRecordID", id), BsonDocument.apply(newMedicalRecord.toJson.compactPrint)).toFuture(),
        Duration(1, TimeUnit.SECONDS))
      Await.result(patientsCollection.findOneAndUpdate(
        equal("patientID", newMedicalRecord.patientID), newMedicalRecordDocument).toFuture(),
        Duration(1, TimeUnit.SECONDS))
      "Drug administered updated."
    } else "Error! Medical record not exist"
  }

}