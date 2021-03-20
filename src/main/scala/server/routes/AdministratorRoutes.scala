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

import akka.actor.typed.scaladsl.AskPattern.{Askable, schedulerFromActorSystem}
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, pathPrefix, _}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import domainmodel.professionalfigure.Surgeon
import server.models.Protocol
import server.models.Protocol.{InsertSurgeon, UpdateSurgeon}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class AdministratorRoutes(administratorController: ActorRef[Protocol.Command])(implicit val system: ActorSystem[_]) {

  private implicit val timeout = Timeout(500.milliseconds)

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  //TODO: Understand how to get object from read model.
  /*def getSurgeons(): Future[Surgeons] =
    administratorController.ask(GetSurgeons)*/
  def insertSurgeon(surgeon: Surgeon): Future[Protocol.Confirmation] =
  administratorController.ask(InsertSurgeon(surgeon, _))

  def updateSurgeon(id: String, surgeon: Surgeon): Future[Protocol.Confirmation] =
    administratorController.ask(UpdateSurgeon(id, surgeon, _))

  val administratorRoutes: Route =
    pathPrefix("surgeons") {
      concat(
        pathEnd {
          concat(
            post {
              entity(as[Surgeon]) { surgeon =>
                onSuccess(insertSurgeon(surgeon)) { response =>
                  complete(StatusCodes.Created, response)
                }
              }
            },

          )
        },
        path(Segment) {
          id =>
            concat(
              put {
                entity(as[Surgeon]) { surgeon =>
                  onSuccess(updateSurgeon(id, surgeon)) { response =>
                    complete(StatusCodes.OK, response)
                  }
                }
              }
            )
        }
      )
    }

}