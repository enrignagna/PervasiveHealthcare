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
import domainmodel.Patient.Patient
import domainmodel.professionalfigure.{Anesthetist, GeneralPractitioner, Instrumentalist, Rescuer, Surgeon, WardNurse}
import json.PatientJsonFormat.PatientJsonFormat
import json.RequestJsonFormats.acceptedJsonFormat
import json.professionalfigure.ProfessionalFigureJsonFormat._
import server.models.JwtAuthentication.hasAdminPermissions
import server.models.Protocol
import server.models.Protocol._

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

class AdministratorRoutes(administratorController: ActorRef[Protocol.Command])(implicit val system: ActorSystem[_]) {

  private implicit val timeout = Timeout(500.milliseconds)

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  //TODO: Understand how to get object from read model.
  /*def getSurgeons(): Future[Surgeons] =
    administratorController.ask(GetSurgeons)*/

  def insertSurgeon(surgeon: Surgeon): Future[Confirmation] =
    administratorController.ask(InsertSurgeon(surgeon, _))

  def updateSurgeon(id: String, surgeon: Surgeon): Future[Confirmation] =
    administratorController.ask(UpdateSurgeon(id, surgeon, _))

  def insertAnesthetist(anesthetist: Anesthetist): Future[Confirmation] =
    administratorController.ask(InsertAnesthetist(anesthetist, _))

  def updateAnesthetist(id: String, anesthetist: Anesthetist): Future[Confirmation] =
    administratorController.ask(UpdateAnesthetist(id, anesthetist, _))

  def insertGeneralPractitioner(generalPractitioner: GeneralPractitioner): Future[Confirmation] =
    administratorController.ask(InsertGeneralPractitioner(generalPractitioner, _))

  def updateGeneralPractitioner(id: String, generalPractitioner: GeneralPractitioner): Future[Confirmation] =
    administratorController.ask(UpdateGeneralPractitioner(id, generalPractitioner, _))

  def insertInstrumentalist(instrumentalist: Instrumentalist): Future[Confirmation] =
    administratorController.ask(InsertInstrumentalist(instrumentalist, _))

  def updateInstrumentalist(id: String, instrumentalist: Instrumentalist): Future[Confirmation] =
    administratorController.ask(UpdateInstrumentalist(id, instrumentalist, _))

  def insertRescuer(rescuer: Rescuer): Future[Confirmation] =
    administratorController.ask(InsertRescuer(rescuer, _))

  def updateRescuer(id: String, rescuer: Rescuer): Future[Confirmation] =
    administratorController.ask(UpdateRescuer(id, rescuer, _))

  def insertWardNurse(wardNurse: WardNurse): Future[Confirmation] =
    administratorController.ask(InsertWardNurse(wardNurse, _))

  def updateWardNurse(id: String, wardNurse: WardNurse): Future[Confirmation] =
    administratorController.ask(UpdateWardNurse(id, wardNurse, _))

  def insertPatient(patient: Patient): Future[Confirmation] =
    administratorController.ask(InsertPatient(patient, _))

  def updatePatient(id: String, patient: Patient): Future[Confirmation] =
    administratorController.ask(UpdatePatient(id, patient, _))

  val administratorRoutes: Route =
    pathPrefix("api") {
      pathPrefix("surgeons") {
        pathEnd {
          post {
            headerValueByName("x-access-token") { value =>
              authorize(hasAdminPermissions(value)) {
                entity(as[Surgeon]) { surgeon =>
                  onSuccess(insertSurgeon(surgeon)) { response =>
                    response match {
                      case _: Accepted => complete(StatusCodes.Created, response)
                      case _: Rejected => complete(StatusCodes.BadRequest, response)
                    }
                  }
                }
              }
            }
          }
        } ~ path(Segment) { id =>
            put {
              headerValueByName("x-access-token") { value =>
                authorize(hasAdminPermissions(value)) {
                  entity(as[Surgeon]) { surgeon =>
                    onSuccess(updateSurgeon(id, surgeon)) { response =>
                      response match {
                        case _: Accepted => complete(StatusCodes.Created, response)
                        case _: Rejected => complete(StatusCodes.BadRequest, response)
                      }
                    }
                  }
                }
              }
            }
          }
      } ~
        pathPrefix("anesthetists") {
          pathEnd {

            post {
              headerValueByName("x-access-token") { value =>
                authorize(hasAdminPermissions(value)) {
                  entity(as[Anesthetist]) { anesthetist =>
                    onSuccess(insertAnesthetist(anesthetist)) { response =>
                      response match {
                        case _: Accepted => complete(StatusCodes.Created, response)
                        case _: Rejected => complete(StatusCodes.BadRequest, response)
                      }
                    }
                  }
                }
              }
            }
          } ~
            path(Segment) {
              id =>
                concat(
                  put {
                    headerValueByName("x-access-token") { value =>
                      authorize(hasAdminPermissions(value)) {
                        entity(as[Anesthetist]) { anesthetist =>
                          onSuccess(updateAnesthetist(id, anesthetist)) { response =>
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
        pathPrefix("generalpractitioners") {
          pathEnd {
            post {
              headerValueByName("x-access-token") { value =>
                authorize(hasAdminPermissions(value)) {
                  entity(as[GeneralPractitioner]) { generalPractitioner =>
                    onSuccess(insertGeneralPractitioner(generalPractitioner)) { response =>
                      response match {
                        case _: Accepted => complete(StatusCodes.Created, response)
                        case _: Rejected => complete(StatusCodes.BadRequest, response)
                      }
                    }
                  }
                }
              }
            }
          } ~
            path(Segment) {
              id =>
                concat(
                  put {
                    headerValueByName("x-access-token") { value =>
                      authorize(hasAdminPermissions(value)) {
                        entity(as[GeneralPractitioner]) { generalPractitioner =>
                          onSuccess(updateGeneralPractitioner(id, generalPractitioner)) { response =>
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
        pathPrefix("instrumentalists") {
          pathEnd {
            post {
              headerValueByName("x-access-token") { value =>
                authorize(hasAdminPermissions(value)) {
                  entity(as[Instrumentalist]) { instrumentalist =>
                    onSuccess(insertInstrumentalist(instrumentalist)) { response =>
                      response match {
                        case _: Accepted => complete(StatusCodes.Created, response)
                        case _: Rejected => complete(StatusCodes.BadRequest, response)
                      }
                    }
                  }
                }
              }
            }
          } ~
            path(Segment) {
              id =>
                concat(
                  put {
                    headerValueByName("x-access-token") { value =>
                      authorize(hasAdminPermissions(value)) {
                        entity(as[Instrumentalist]) { instrumentalist =>
                          onSuccess(updateInstrumentalist(id, instrumentalist)) { response =>
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
        pathPrefix("rescuers") {
          pathEnd {
            post {
              headerValueByName("x-access-token") { value =>
                authorize(hasAdminPermissions(value)) {
                  entity(as[Rescuer]) { rescuer =>
                    onSuccess(insertRescuer(rescuer)) { response =>
                      response match {
                        case _: Accepted => complete(StatusCodes.Created, response)
                        case _: Rejected => complete(StatusCodes.BadRequest, response)
                      }
                    }
                  }
                }
              }
            }
          } ~
            path(Segment) {
              id =>
                concat(
                  put {
                    headerValueByName("x-access-token") { value =>
                      authorize(hasAdminPermissions(value)) {
                        entity(as[Rescuer]) { rescuer =>
                          onSuccess(updateRescuer(id, rescuer)) { response =>
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
        pathPrefix("wardnurses") {
          pathEnd {
            post {
              headerValueByName("x-access-token") { value =>
                authorize(hasAdminPermissions(value)) {
                  entity(as[WardNurse]) { wardnurse =>
                    onSuccess(insertWardNurse(wardnurse)) { response =>
                      response match {
                        case _: Accepted => complete(StatusCodes.Created, response)
                        case _: Rejected => complete(StatusCodes.BadRequest, response)
                      }
                    }
                  }
                }
              }
            }
          } ~
            path(Segment) {
              id =>

                  put {
                    headerValueByName("x-access-token") { value =>
                      authorize(hasAdminPermissions(value)) {
                        entity(as[WardNurse]) { wardnurse =>
                          onSuccess(updateWardNurse(id, wardnurse)) { response =>
                            response match {
                              case _: Accepted => complete(StatusCodes.Created, response)
                              case _: Rejected => complete(StatusCodes.BadRequest, response)
                            }
                          }
                        }
                      }
                    }
                  }

            }
        } ~
        pathPrefix("patients") {
          pathEnd {
            post {
              headerValueByName("x-access-token") { value =>
                authorize(hasAdminPermissions(value)) {
                  entity(as[Patient]) { patient =>
                    onSuccess(insertPatient(patient)) { response =>
                      response match {
                        case _: Accepted => complete(StatusCodes.Created, response)
                        case _: Rejected => complete(StatusCodes.BadRequest, response)
                      }
                    }
                  }
                }
              }
            }
          } ~
            path(Segment) {
              id =>
                concat(
                  put {
                    headerValueByName("x-access-token") { value =>
                      authorize(hasAdminPermissions(value)) {
                        entity(as[Patient]) { patient =>
                          onSuccess(updatePatient(id, patient)) { response =>
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