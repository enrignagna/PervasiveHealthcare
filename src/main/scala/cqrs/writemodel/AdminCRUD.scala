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

import cqrs.writemodel.WriteModel.{doctorsCollection, patientsCollection}
import domainmodel.Patient.Patient
import domainmodel.{DoctorID, PatientID, User}
import domainmodel.professionalfigure.{Anesthetist, GeneralPractitioner, Instrumentalist, Rescuer, Surgeon, WardNurse}
import json.professionalfigure.ProfessionalFigureJsonFormat._
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters.equal
import spray.json.enrichAny
import json.IDJsonFormat.{patientIDJsonFormat, doctorIDJsonFormat}
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import json.PatientJsonFormat.PatientJsonFormat

class AdminCRUD {

  def insertSurgeon(surgeon: Surgeon): String = {
    val document: BsonDocument = BsonDocument.apply(surgeon.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty) {
      Await.result(doctorsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(surgeon.doctorID.value, "surgeon"), Role.SURGEON), Duration(1, TimeUnit.SECONDS))
      "Surgeon created."
    } else {
      "Error! Surgeon with the same doctorID already exists!"
    }
  }

  def updateSurgeon(doctorID: DoctorID, surgeon: Surgeon): String = {
    val document: BsonDocument = BsonDocument.apply(surgeon.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(doctorID.toJson.compactPrint)
    Await.result(doctorsCollection.findOneAndReplace(
      equal("doctorID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "Surgeon updated."
  }

  def insertAnesthetist(anesthetist: Anesthetist): String = {
    val document: BsonDocument = BsonDocument.apply(anesthetist.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty) {
      Await.result(doctorsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(anesthetist.doctorID.value, "anesthetist"), Role.ANESTHETIST), Duration(1, TimeUnit.SECONDS))
      "Anesthetist created."
    } else {
      "Error! Anesthetist with the same doctorID already exists!"
    }
  }

  def updateAnesthetist(doctorID: DoctorID, anesthetist: Anesthetist): String = {
    val document: BsonDocument = BsonDocument.apply(anesthetist.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(doctorID.toJson.compactPrint)
    Await.result(doctorsCollection.findOneAndReplace(
      equal("doctorID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "Anesthetist updated."
  }

  def insertInstrumentalist(instrumentalist: Instrumentalist): String = {
    val document: BsonDocument = BsonDocument.apply(instrumentalist.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty) {
      Await.result(doctorsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(instrumentalist.doctorID.value, "instrumentalist"), Role.INSTRUMENTALIST), Duration(1, TimeUnit.SECONDS))
      "Instrumentalist created."
    } else {
      "Error! Instrumentalist with the same doctorID already exists!"
    }
  }

  def updateInstrumentalist(doctorID: DoctorID, instrumentalist: Instrumentalist): String = {
    val document: BsonDocument = BsonDocument.apply(instrumentalist.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(doctorID.toJson.compactPrint)
    Await.result(doctorsCollection.findOneAndReplace(
      equal("doctorID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "Instrumentalist updated."
  }

  def insertGeneralPractitioner(generalPractitioner: GeneralPractitioner): String = {
    val document: BsonDocument = BsonDocument.apply(generalPractitioner.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty) {
      Await.result(doctorsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(generalPractitioner.doctorID.value, "general practitioner"), Role.GENERAL_PRACTITIONER), Duration(1, TimeUnit.SECONDS))
      "General practitioner created."
    } else {
      "Error! General practitioner with the same doctorID already exists!"
    }
  }

  def updateGeneralPractitioner(doctorID: DoctorID, generalPractitioner: GeneralPractitioner): String = {
    val document: BsonDocument = BsonDocument.apply(generalPractitioner.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(doctorID.toJson.compactPrint)
    Await.result(doctorsCollection.findOneAndReplace(
      equal("doctorID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "General practitioner updated."
  }

  def insertRescuer(rescuer: Rescuer): String = {
    val document: BsonDocument = BsonDocument.apply(rescuer.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty) {
      Await.result(doctorsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(rescuer.doctorID.value, "rescuer"), Role.RESCUER), Duration(1, TimeUnit.SECONDS))
      "Rescuer created."
    } else {
      "Error! Rescuer with the same doctorID already exists!"
    }
  }

  def updateRescuer(doctorID: DoctorID, rescuer: Rescuer): String = {
    val document: BsonDocument = BsonDocument.apply(rescuer.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(doctorID.toJson.compactPrint)
    Await.result(doctorsCollection.findOneAndReplace(
      equal("doctorID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "Rescuer updated."
  }

  def insertWardNurse(wardNurse: WardNurse): String = {
    val document: BsonDocument = BsonDocument.apply(wardNurse.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty) {
      Await.result(doctorsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(wardNurse.doctorID.value, "ward nurse"), Role.WARD_NURSE), Duration(1, TimeUnit.SECONDS))
      "Ward nurse created."
    } else {
      "Error! Ward nurse with the same doctorID already exists!"
    }
  }

  def updateWardNurse(doctorID: DoctorID, wardNurse: WardNurse): String = {
    val document: BsonDocument = BsonDocument.apply(wardNurse.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(doctorID.toJson.compactPrint)
    Await.result(doctorsCollection.findOneAndReplace(
      equal("doctorID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "Ward nurse updated."
  }

  def insertPatient(patient: Patient): String = {
    val document: BsonDocument = BsonDocument.apply(patient.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(patientsCollection.find(
      equal("patientID", document.get("patientID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if (res.isEmpty) {
      Await.result(patientsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(Repository.auth.signUp(User(patient.patientID.value, "patient"), Role.PATIENT), Duration(1, TimeUnit.SECONDS))
      "Patient created."
    } else {
      "Error! Patient with the same doctorID already exists!"
    }
  }

  def updatePatient(patientID: PatientID, patient: Patient): String = {
    val document: BsonDocument = BsonDocument.apply(patient.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(patientID.toJson.compactPrint)
    Await.result(patientsCollection.findOneAndReplace(
      equal("patientID", id), document).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    "Patient updated."
  }
}
