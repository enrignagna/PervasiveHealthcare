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
import domainmodel.medicalrecords.{DrugsAdministered, MedicalRecordsID}
import domainmodel.medicalrecords.clinicaldiary.ClinicalDiary
import json.RequestJsonFormats.acceptedJsonFormat
import json.medicalrecords.clinicaldiary.ClinicalDiaryJsonFormat.clinicalDiaryJsonFormat
import server.models.Protocol
import server.models.Protocol._
import json.medicalrecords.SingleSheetTherapyJsonFormat.drugsAdministeredTherapyJsonFormat
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import server.models.JwtAuthentication.hasRescuerPermissions

class RescuerRoutes(rescuerController: ActorRef[Protocol.Command])(implicit val system: ActorSystem[_]) {

  private implicit val timeout = Timeout(500.milliseconds)

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  def updateClinicalDiary(medicalRecordID: MedicalRecordsID, clinicalDiary: ClinicalDiary): Future[Confirmation] =
    rescuerController.ask(UpdateClinicalDiary(medicalRecordID, clinicalDiary, _))

  def updateDrugAdministered(medicalRecordID: MedicalRecordsID, drugsAdministered: DrugsAdministered): Future[Confirmation] =
    rescuerController.ask(UpdateDrugAdministered(medicalRecordID, drugsAdministered, _))

  val rescuerRoutes: Route =
    pathPrefix("api") {
      pathPrefix("clinicaldiary") {
        path(Segment) {
          id =>
            concat(
              put {
                headerValueByName("x-access-token") { (value) =>
                  authorize(hasRescuerPermissions(value)) {
                    entity(as[ClinicalDiary]) { clinicalDiary =>
                      onSuccess(updateClinicalDiary(MedicalRecordsID(id), clinicalDiary)) { response =>
                        response match {
                          case _: Accepted => complete(StatusCodes.Created, response)
                          case _: Rejected => complete(StatusCodes.BadRequest, response)
                        }
                      }
                    }
                  }
                }
              }
            )
        }
      } ~
        pathPrefix("drugAdministered") {
          path(Segment) {
            id =>
              concat(
                put {
                  headerValueByName("x-access-token") { (value) =>
                    authorize(hasRescuerPermissions(value)) {
                      entity(as[DrugsAdministered]) { drugAdministered =>
                        onSuccess(updateDrugAdministered(MedicalRecordsID(id), drugAdministered)) { response =>
                          response match {
                            case _: Accepted => complete(StatusCodes.Created, response)
                            case _: Rejected => complete(StatusCodes.BadRequest, response)
                          }
                        }
                      }
                    }
                  }
                }
              )
          }
        }
    }
}