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

package database

import java.util.concurrent.TimeUnit

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates.set
import database.Helpers.GenericObservable
import database.WriteModel.surgeonsCollection
import domainmodel.professionalfigure.{DoctorID, Surgeon}
import org.mongodb.scala.Observer
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.result.InsertOneResult
import server.JsonFormats._
import spray.json.enrichAny

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object AdminCRUD {

  def insertSurgeon(surgeon: Surgeon): String = {
    val document: BsonDocument = BsonDocument.apply(surgeon.toJson.compactPrint)

    val res: Seq[BsonDocument] = Await.result(surgeonsCollection.find(
      equal("doctorID", document.get("doctorID"))).toFuture(),
      Duration(1, TimeUnit.SECONDS))
    if(res.isEmpty){
      Await.result(surgeonsCollection.insertOne(document).toFuture(), Duration(1, TimeUnit.SECONDS))
      "Surgeon created."
    } else{
      "Error! Surgeon with the same doctorID already exists!"
    }
  }

  def updateSurgeon(doctorID: DoctorID, surgeon: Surgeon): String = {
    val document: BsonDocument = BsonDocument.apply(surgeon.toJson.compactPrint)
    val id: BsonDocument = BsonDocument.apply(doctorID.toJson.compactPrint)
    Await.result(surgeonsCollection.findOneAndReplace(
      equal("doctorID", id), document).toFuture(),
      Duration(10, TimeUnit.SECONDS))
    "Surgeon updated."
  }
}
