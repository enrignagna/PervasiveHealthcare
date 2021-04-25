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

package client.login

import akka.actor.{Actor, ActorLogging, ClassicActorSystemProvider}
import akka.http.scaladsl.model._
import akka.http.scaladsl.{Http, HttpExt}
import akka.pattern.pipe
import akka.util.ByteString
import client.Client
import client.login.Requests._
import client.login.Message._
import gui.{DialogGUI, LoginGUI}
import spray.json.JsonParser

import scala.concurrent.ExecutionContextExecutor


class LoginActor(gui: LoginGUI) extends Actor with ActorLogging {

  val http: HttpExt = Http(Client.system)
  implicit val system: ClassicActorSystemProvider = Client.system
  implicit val executionContext: ExecutionContextExecutor = Client.system.classicSystem.dispatcher
  var token: Option[String] = None
  var role: Option[String] = None
  val dialogGUI= new DialogGUI

  private lazy val onInteractionBehaviour: Receive = {
    case LoginMessage(id, password) =>
      println("here")
      loginRequest(id, password).pipeTo(self)
      this.context become onAttendResponseLoginMessageBehaviour
  }

  private lazy val onAttendResponseLoginMessageBehaviour: Receive = {
    case HttpResponse(StatusCodes.OK, _, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        token = Some(
          JsonParser(body.utf8String).asJsObject.getFields("token").mkString replaceAll("[\"]", "")
        )
        role = Some(
          JsonParser(body.utf8String).asJsObject.getFields("role.id").mkString
        )
        gui.responseLogin(role, token)
        println("bodyLogin", body)
      }
      this.context become onInteractionBehaviour
    case resp@HttpResponse(code, _, _, _) =>
      gui.showAlert(code.toString())
      println("Error: " + code.value)
      resp.discardEntityBytes()
      this.context become onInteractionBehaviour
  }

  override def receive: Receive =
    this.onInteractionBehaviour

}