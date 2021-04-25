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

package cqrs.readmodel

import cqrs.readmodel.ReadModel.database
import cqrs.writemodel.Role
import cqrs.writemodel.Role.Role
import domainmodel.Gender.Gender
import domainmodel.KinshipDegree.KinshipDegree
import domainmodel.generalinfo.AllergyClass.AllergyClass
import domainmodel.generalinfo.BloodType.BloodType
import domainmodel.generalinfo.Rh.Rh
import domainmodel.generalinfo.{AllergyClass, BloodType, Rh}
import domainmodel.professionalfigure.Specialization
import domainmodel.professionalfigure.Specialization.Specialization
import domainmodel.{Gender, KinshipDegree}
import json.AnamnesisJsonFormat.kinshipDegreeJsonFormat
import json.PatientJsonFormat.genderJsonFormat
import json.generalinfo.AllergyJsonFormat.allergyClassJsonFormat
import json.generalinfo.BloodGroupJsonFormat.{bloodTypeJsonFormat, rhJsonFormat}
import json.professionalfigure.ProfessionalFigureJsonFormat.{roleJsonFormat, specializationJsonFormat}
import org.mongodb.scala.bson.BsonDocument
import org.mongodb.scala.{Document, MongoCollection}
import spray.json.{JsonParser, enrichAny}

import java.util.concurrent.TimeUnit
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * Role Collection.
 */
object RoleCollection {
  val roleCollection: MongoCollection[Document] = database.getCollection("roles")

  /**
   * Initialize method.
   */
  def initialize(): Unit = {
    val numberRolesDocs: Int = Await.result(roleCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if (numberRolesDocs == 0) {
      Await.result(roleCollection.insertMany(Role.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }

  /**
   * Get method.
   *
   * @return set of roles.
   */
  def get(): Set[Role] = {
    val res: Seq[BsonDocument] = Await.result(roleCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS)).map(BsonDocument(_))
    if (res.nonEmpty) {
      res.map(document => JsonParser(document.toString).convertTo[Role.Role]).toSet
    }
    else Set.empty
  }
}

/**
 * Gender Collection.
 */
object GenderCollection {

  val genderCollection: MongoCollection[Document] = database.getCollection("gender")

  /**
   * Initialize method.
   */
  def initialize(): Unit = {
    val numberGenderDocs: Int = Await.result(genderCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if (numberGenderDocs == 0) {
      Await.result(genderCollection.insertMany(Gender.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }

  /**
   * Get method.
   *
   * @return set of gender.
   */
  def get(): Set[Gender] = {
    val res: Seq[BsonDocument] = Await.result(genderCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS)).map(BsonDocument(_))
    if (res.nonEmpty) {
      res.map(document => JsonParser(document.toString).convertTo[Gender.Gender]).toSet
    }
    else Set.empty
  }
}

/**
 * Allergy Class Collection.
 */
object AllergyClassCollection {
  val allergyClassCollection: MongoCollection[Document] = database.getCollection("allergyclasses")

  /**
   * Initialize method.
   */
  def initialize(): Unit = {
    val numberAllergiesDocs: Int = Await.result(allergyClassCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if (numberAllergiesDocs == 0) {
      Await.result(allergyClassCollection.insertMany(AllergyClass.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }

  /**
   * Get method.
   *
   * @return set of allergy class.
   */
  def get(): Set[AllergyClass] = {
    val res: Seq[BsonDocument] = Await.result(allergyClassCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS)).map(BsonDocument(_))
    if (res.nonEmpty) {
      res.map(document => JsonParser(document.toString).convertTo[AllergyClass.AllergyClass]).toSet
    }
    else Set.empty
  }
}

/**
 * Blood Type Collection.
 */
object BloodTypeCollection {
  val bloodTypeCollection: MongoCollection[Document] = database.getCollection("bloodtypes")

  /**
   * Initialize method.
   */
  def initialize(): Unit = {
    val numberBloodTypesDocs: Int = Await.result(bloodTypeCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if (numberBloodTypesDocs == 0) {
      Await.result(bloodTypeCollection.insertMany(BloodType.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }

  /**
   * Get method.
   *
   * @return set of blood type.
   */
  def get(): Set[BloodType] = {
    val res: Seq[BsonDocument] = Await.result(bloodTypeCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS)).map(BsonDocument(_))
    if (res.nonEmpty) {
      res.map(document => JsonParser(document.toString).convertTo[BloodType.BloodType]).toSet
    }
    else Set.empty
  }
}

/**
 * Rh Collection.
 */
object RhCollection {
  val rhCollection: MongoCollection[Document] = database.getCollection("rh")

  /**
   * Initialize method.
   */
  def initialize(): Unit = {
    val numberRhDocs: Int = Await.result(rhCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if (numberRhDocs == 0) {
      Await.result(rhCollection.insertMany(Rh.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }

  /**
   * Get method.
   *
   * @return set of rh.
   */
  def get(): Set[Rh] = {
    val res: Seq[BsonDocument] = Await.result(rhCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS)).map(BsonDocument(_))
    if (res.nonEmpty) {
      res.map(document => JsonParser(document.toString).convertTo[Rh.Rh]).toSet
    }
    else Set.empty
  }
}

/**
 * Specialization Collection.
 */
object SpecializationCollection {
  val specializationCollection: MongoCollection[Document] = database.getCollection("specializations")

  /**
   * Initialize method.
   */
  def initialize(): Unit = {
    val numberSpecializationDocs: Int = Await.result(specializationCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if (numberSpecializationDocs == 0) {
      Await.result(specializationCollection.insertMany(Specialization.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }

  /**
   * Get method.
   *
   * @return set of specialization.
   */
  def get(): Set[Specialization] = {
    val res: Seq[BsonDocument] = Await.result(specializationCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS)).map(BsonDocument(_))
    if (res.nonEmpty) {
      res.map(document => JsonParser(document.toString).convertTo[Specialization.Specialization]).toSet
    }
    else Set.empty
  }
}

/**
 * Kinship Degree Collection.
 */
object KinshipDegreeCollection {
  val kinshipDegreeCollection: MongoCollection[Document] = database.getCollection("kinshipdegrees")

  /**
   * Initialize method.
   */
  def initialize(): Unit = {
    val numberKinshipDocs: Int = Await.result(kinshipDegreeCollection.countDocuments().toFuture(), Duration(1, TimeUnit.SECONDS)).intValue()
    if (numberKinshipDocs == 0) {
      Await.result(kinshipDegreeCollection.insertMany(KinshipDegree.values.map(value => Document(value.toJson.compactPrint)).toSeq).toFuture(), Duration(1, TimeUnit.SECONDS))
    }
  }

  /**
   * Get method.
   *
   * @return set of kinship degree.
   */
  def get(): Set[KinshipDegree] = {
    val res: Seq[BsonDocument] = Await.result(kinshipDegreeCollection.find().toFuture(), Duration(1, TimeUnit.SECONDS)).map(BsonDocument(_))
    if (res.nonEmpty) {
      res.map(document => JsonParser(document.toString).convertTo[KinshipDegree.KinshipDegree]).toSet
    }
    else Set.empty
  }
}
