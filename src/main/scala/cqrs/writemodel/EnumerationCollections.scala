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

import cqrs.writemodel.WriteModel.database
import domainmodel.Gender
import domainmodel.generalinfo.{AllergyClass, BloodType, Rh}
import domainmodel.professionalfigure.Specialization
import json.PatientJsonFormat.genderJsonFormat
import json.generalinfo.AllergyJsonFormat.allergyClassJsonFormat
import json.generalinfo.BloodGroupJsonFormat.{bloodTypeJsonFormat, rhJsonFormat}
import json.professionalfigure.ProfessionalFigureJsonFormat.{roleJsonFormat, specializationJsonFormat}
import org.mongodb.scala.{Document, MongoCollection}
import spray.json.enrichAny

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object RoleCollection {
  def initialize(): Unit ={
    val roleCollection: MongoCollection[Document] = database.getCollection("roles")
    val numberRolesDocs: Int = Await.result(roleCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if(numberRolesDocs == 0){
      Await.result(roleCollection.insertMany(Role.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }
}

object GenderCollection {

  def initialize(): Unit ={
    val genderCollection: MongoCollection[Document] = database.getCollection("gender")
    val numberGenderDocs: Int = Await.result(genderCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if(numberGenderDocs == 0){
      Await.result(genderCollection.insertMany(Gender.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
    /*
    Gender.values.foreach(value => {
      Await.result(genderCollection.insertOne(BsonDocument.apply(JsObject("id" -> JsNumber(value.id), "value" -> JsString(value.toString)).toJson.compactPrint)).toFuture(), Duration(1, TimeUnit.SECONDS))
    })

     */
  }
}

object AllergyClassCollection {
  def initialize(): Unit ={
    val allergyClassCollection: MongoCollection[Document] = database.getCollection("allergyclasses")
    val numberAllergiesDocs: Int = Await.result(allergyClassCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if(numberAllergiesDocs == 0){
      Await.result(allergyClassCollection.insertMany(AllergyClass.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }
}

object BloodTypeCollection {
  def initialize(): Unit ={
    val bloodTypeCollection: MongoCollection[Document] = database.getCollection("bloodtypes")
    val numberBloodTypesDocs: Int = Await.result(bloodTypeCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if(numberBloodTypesDocs == 0){
      Await.result(bloodTypeCollection.insertMany(BloodType.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }
}

object RhCollection {
  def initialize(): Unit ={
    val rhCollection: MongoCollection[Document] = database.getCollection("rh")
    val numberRhDocs: Int = Await.result(rhCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if(numberRhDocs == 0){
      Await.result(rhCollection.insertMany(Rh.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }
}

object SpecializationCollection {
  def initialize(): Unit ={
    val specializationCollection: MongoCollection[Document] = database.getCollection("specializations")
    val numberSpecializationDocs: Int = Await.result(specializationCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if(numberSpecializationDocs == 0){
      Await.result(specializationCollection.insertMany(Specialization.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }
}
