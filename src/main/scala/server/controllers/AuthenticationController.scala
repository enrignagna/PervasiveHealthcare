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

package server.controllers

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import cqrs.Repository
import server.models.JwtAuthentication
import server.models.JwtAuthentication.Token
import server.models.Protocol.{Command, Login, LoginAccepted, Rejected}

import scala.util.Random

object AuthenticationController {

  def apply(): Behavior[Command] = handleCommand()

  private def handleCommand(): Behavior[Command] =
    Behaviors.receiveMessage {
      case Login(user, replyTo) =>
        val res = Repository.auth.login(user)
        if (res._1.nonEmpty) {
          val token: String = Random.alphanumeric take 64 mkString ""
          JwtAuthentication.tokens = JwtAuthentication.tokens.addNewToken(Token((token, res._1.get.id)))
          replyTo ! LoginAccepted(res._2, token)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res._2)
        }
        Behaviors.same
      case _ => throw new IllegalArgumentException()
    }
}

