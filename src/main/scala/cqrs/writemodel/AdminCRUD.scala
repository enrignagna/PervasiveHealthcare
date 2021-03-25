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

import cqrs.writemodel.WriteModel.doctorsCollection
import domainmodel.User
import domainmodel.professionalfigure.{Anesthetist, DoctorID, Instrumentalist, Surgeon}
import json.professionalfigure.ProfessionalFigureJsonFormat._
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters.equal
import spray.json.enrichAny

import scala.concurrent.Await
import scala.concurrent.duration.Duration


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


}
