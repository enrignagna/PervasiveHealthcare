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

import akka.actor.{Actor, ActorLogging, ActorSystem}
import akka.http.scaladsl.model._
import akka.http.scaladsl.{Http, HttpExt}
import akka.pattern._
import akka.util.ByteString
import client.Message._
import client.Requests._
import client.Requests.system.executionContext
import domainmodel.Patient._

class PatientActor(patient: Patient) extends Actor with ActorLogging {

  implicit val system: ActorSystem = context.system
  val http: HttpExt = Http(system)



  //private lazy val onForecastBehaviour: Receive = ???

  private lazy val onInteractionBehaviour: Receive = {
        //TODO: dovrò fare un behaviour per ogni risposta che voglio ricevere in modo da gestirle diversamente
        //oppure verranno gestire alla stesso modo rigirandole come text in un messaggio al richiedente. Devo pensarci
    case AllMedicalRecordMessage(patientID) =>
      allMedicalRecordsRequest(patientID).pipeTo(self)
      this.context become onAttendResponseRequestBehaviour
    case SpecificMedicalRecordMessage(patientID, medicalRecordsID) =>
      specificMedicalRecordRequest(patientID, medicalRecordsID)
      this.context become onAttendResponseRequestBehaviour
    case GeneralInfoMessage(patientID) =>
      generalInfoMessageRequest(patientID).pipeTo(self)
      this.context become onAttendResponseRequestBehaviour
    case GeneralPractitionerInfoMessage(patientID) =>
      generalPractitionerInfoMessageRequest(patientID).pipeTo(self)
      this.context become onAttendResponseRequestBehaviour
    case _ =>
      println("Sto eseguendo delle previsioni")
  }

  private lazy val onAttendResponseRequestBehaviour: Receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        log.info("Got response, body: " + body.utf8String)
      }
      this.context become onInteractionBehaviour
    case resp@HttpResponse(code, _, _, _) =>
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()
      this.context become onInteractionBehaviour
  }

  override def receive: Receive =
    this.onInteractionBehaviour

}