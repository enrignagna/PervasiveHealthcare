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

package client.patient

import akka.actor.{Actor, ActorLogging, ClassicActorSystemProvider}
import akka.http.scaladsl.model._
import akka.http.scaladsl.{Http, HttpExt}
import akka.pattern.pipe
import akka.util.ByteString
import client.Client
import client.patient.Message._
import client.patient.Requests._
import domainmodel.Patient._
import domainmodel.PatientID
import json.PatientJsonFormat.PatientJsonFormat
import spray.json.JsonParser

import scala.concurrent.ExecutionContextExecutor


class PatientActor(patientID: PatientID) extends Actor with ActorLogging {

  val http: HttpExt = Http(Client.system)
  implicit val system: ClassicActorSystemProvider = Client.system
  implicit val executionContext: ExecutionContextExecutor = Client.system.classicSystem.dispatcher
  var token: Option[String] = None


  private lazy val onInteractionBehaviour: Receive = {
    case AllMedicalRecordMessage =>
      patientRequest(token.getOrElse(""), patientID).pipeTo(self)
      this.context become onAttendResponseAllMedicalRecordMessageBehaviour
    case GeneralInfoMessage =>
      patientRequest(token.getOrElse(""), patientID).pipeTo(self)
      this.context become onAttendResponseGeneralInfoMessageBehaviour
    case GeneralPractitionerInfoMessage =>
      patientRequest(token.getOrElse(""), patientID).pipeTo(self)
      this.context become onAttendResponseGeneralPractitionerInfoMessageBehaviour
  }

  private lazy val onAttendResponseAllMedicalRecordMessageBehaviour: Receive = {
    case HttpResponse(StatusCodes.OK, _, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        val pat: Patient = JsonParser(body.utf8String).convertTo[Patient]
        if (pat.medicalRecords.nonEmpty)
          println(pat.medicalRecords.get)
        else
          println("No medical records available.")
      }
      this.context become onInteractionBehaviour
    case resp@HttpResponse(code, _, _, _) =>
      println("Error: " + code.value)
      resp.discardEntityBytes()
      this.context become onInteractionBehaviour
  }


  private lazy val onAttendResponseGeneralInfoMessageBehaviour: Receive = {
    case HttpResponse(StatusCodes.OK, _, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        val pat: Patient = JsonParser(body.utf8String).convertTo[Patient]
        if (pat.generalInfo.nonEmpty)
          println(pat.generalInfo.get)
        else
          println("No general info available.")
      }
      this.context become onInteractionBehaviour
    case resp@HttpResponse(code, _, _, _) =>
      println("Error: " + code.value)
      resp.discardEntityBytes()
      this.context become onInteractionBehaviour
  }


  private lazy val onAttendResponseGeneralPractitionerInfoMessageBehaviour: Receive = {
    case HttpResponse(StatusCodes.OK, _, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        val pat: Patient = JsonParser(body.utf8String).convertTo[Patient]
        if (pat.generalPractitionerInfo.nonEmpty)
          println(pat.generalPractitionerInfo.get)
        else
          println("No general practitioner info available.")
      }
      this.context become onInteractionBehaviour
    case resp@HttpResponse(code, _, _, _) =>
      println("Error: " + code.value)
      resp.discardEntityBytes()
      this.context become onInteractionBehaviour
  }


  override def receive: Receive =
    this.onInteractionBehaviour

}