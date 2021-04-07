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

import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

object WriteModel {

  val database: MongoDatabase = MongoClient().getDatabase("WriteModel")

  val authCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("credentials")

  val doctorsCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("doctors")

  val medicalRecordsCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("medicalrecords")

  val patientsCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("patients")

  val clinicalDiariesCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("clinicalDiaries")

  val dischargeLetterCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("dischargeLetters")

  val visitsCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("visits")

  val prescriptionsCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("prescriptions")

  val generalpractitionerinfoCollection: MongoCollection[BsonDocument] =
    database.getCollection[BsonDocument]("generalpractitionerinfo")
}
