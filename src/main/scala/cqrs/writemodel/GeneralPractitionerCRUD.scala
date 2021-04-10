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

import cqrs.writemodel.WriteModel.{doctorsCollection, generalpractitionerinfoCollection, patientsCollection}
import domainmodel.{PatientID, User}
import domainmodel.generalpractitionerinfo.GeneralPractitionerInfo
import json.generalpractitionerinfo.GeneralPractitionerInfoJsonFormat.generalPractitionerInfoJsonFormat
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters.equal
import spray.json.enrichAny
import json.IDJsonFormat.{patientIDJsonFormat, doctorIDJsonFormat}
import org.mongodb.scala.model.Updates.{push, set}
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class GeneralPractitionerCRUD {

  def insertGeneralPractitionerInfo(generalpractitionerinfo: GeneralPractitionerInfo): String = {
    val document: BsonDocument = BsonDocument.apply(generalpractitionerinfo.toJson.compactPrint)

    val doctor: Seq[BsonDocument] = Await.result(doctorsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))

    val patient = Await.result(patientsCollection.find(equal("patientID",
      BsonDocument.apply(generalpractitionerinfo.patientID.toJson.compactPrint))).toFuture(),
      Duration(1, TimeUnit.SECONDS))

    println("doctor", doctor)
    println("patient", patient)
    println("patientIDGEN", generalpractitionerinfo.patientID)

    if (doctor.nonEmpty && patient.nonEmpty) {
      Await.result(generalpractitionerinfoCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      Await.result(patientsCollection.findOneAndUpdate(equal("patientID",
        BsonDocument.apply(generalpractitionerinfo.patientID.toJson.compactPrint)),
        set("generalPractitionerInfo", document))
        .toFuture(),
        Duration(1, TimeUnit.SECONDS))

      "General practitioner info created."
    } else {
      "Error! This General practitioner info already exists!"
    }
  }

  def updateGeneralPractitionerInfo(patientID: PatientID, generalpractitionerinfo: GeneralPractitionerInfo): String = {
    val document: BsonDocument = BsonDocument.apply(generalpractitionerinfo.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(patientID.toJson.compactPrint)

    val patient = Await.result(patientsCollection.find(
      equal("patientID", id)).toFuture(),
      Duration(1, TimeUnit.SECONDS))

    //TODO: if we want save the medical history
    val generalPractitionerFinding = Await.result(generalpractitionerinfoCollection.find(equal("doctorID",
      BsonDocument.apply(generalpractitionerinfo.doctorID.toJson.compactPrint)))
      .toFuture(),
      Duration(1, TimeUnit.SECONDS))


    if (patient.nonEmpty && generalPractitionerFinding.nonEmpty) {

      //TODO: if we don't want save the medical history
      Await.result(generalpractitionerinfoCollection.findOneAndUpdate(equal("doctorID",
        BsonDocument.apply(generalpractitionerinfo.doctorID.toJson.compactPrint)),
        set("generalPractitionerInfo", document))
        .toFuture(),
        Duration(1, TimeUnit.SECONDS))

      Await.result(patientsCollection.findOneAndUpdate(equal("patientID",
        BsonDocument.apply(generalpractitionerinfo.patientID.toJson.compactPrint)),
        set("generalPractitionerInfo", document))
        .toFuture(),
        Duration(1, TimeUnit.SECONDS))

      "General practitioner info updated."
    }
    else {
      "Error in the update."
    }
  }
}

