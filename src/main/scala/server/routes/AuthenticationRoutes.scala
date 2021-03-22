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

package server.routes

import java.math.BigInteger
import java.security.MessageDigest

import akka.actor.typed.scaladsl.AskPattern.{Askable, schedulerFromActorSystem}
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, complete, concat, entity, onSuccess, path, pathEnd, pathPrefix, post, put}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import domainmodel.User
import domainmodel.professionalfigure.Surgeon
import json.RequestJsonFormats.acceptedJsonFormat
import json.UserJsonFormat.userJsonFormat
import server.models.Protocol
import server.models.Protocol.{Accepted, Confirmation, InsertSurgeon, Login, LoginAccepted, Rejected, UpdateSurgeon}
import server.utils.Utils

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class AuthenticationRoutes(authenticationController: ActorRef[Protocol.Command])(implicit val system: ActorSystem[_]) {

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  private implicit val timeout = Timeout(500.milliseconds)
  //TODO: Understand how to get object from read model.
  /*def getSurgeons(): Future[Surgeons] =
    administratorController.ask(GetSurgeons)*/
  def login(user: User): Future[Confirmation] =
  authenticationController.ask(Login(user, _))


  val authenticationRoutes: Route =
    pathPrefix("api") {
      path("login") {
        pathEnd {
          post {

            entity(as[User]) { user =>
              val hashedPassword = Utils.getHashedPassword(user.password) //password hashing
              val newUser = User(user.id, hashedPassword)
              onSuccess(login(newUser)) { response =>
                response match {
                  case _: LoginAccepted => complete(StatusCodes.OK, response)
                  case _: Rejected => complete(StatusCodes.BadRequest, response)
                  case _ => throw new IllegalArgumentException()
                }
              }
            }
          }
        }
      }
    }
}
