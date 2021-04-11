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

package client

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import domainmodel.PatientID
import domainmodel.medicalrecords.MedicalRecordsID

import scala.concurrent.{ExecutionContextExecutor, Future}

object Requests {

  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "SingleRequest")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.executionContext


  def loginRequest(id: String, password: String): Future[HttpResponse] = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        uri = "http://127.0.0.1:8080/api/login",
        entity = HttpEntity(ContentTypes.`application/json`, s"""{ "id": "$id", "password" : "$password" }""")
      )
    )
  }

  def allMedicalRecordsRequest(patientID: PatientID): Future[HttpResponse] = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        //TODO: da aggiornare rotta e body
        uri = "http://127.0.0.1:8080/api/",
        entity = HttpEntity(ContentTypes.`application/json`, s"""{ "userId": "${patientID.value}" }""")
      )
    )
  }

  def specificMedicalRecordRequest(patientID: PatientID, medicalRecordsID: MedicalRecordsID): Future[HttpResponse] = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        //TODO: da aggiornare rotta e body
        uri = "http://127.0.0.1:8080/api/",
        entity = HttpEntity(ContentTypes.`application/json`, s"""{ "userId": "${patientID.value}", "medicalRecordID" : "$medicalRecordsID" }""")
      )
    )
  }

  def generalInfoMessageRequest(patientID: PatientID): Future[HttpResponse] = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        //TODO: da aggiornare rotta e body
        uri = "http://127.0.0.1:8080/api/",
        entity = HttpEntity(ContentTypes.`application/json`, s"""{ "userId": "${patientID.value}" }""")
      )
    )
  }

  def generalPractitionerInfoMessageRequest(patientID: PatientID): Future[HttpResponse] = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        //TODO: da aggiornare rotta e body
        uri = "http://127.0.0.1:8080/api/",
        entity = HttpEntity(ContentTypes.`application/json`, s"""{ "userId": "${patientID.value}" }""")
      )
    )
  }

}