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
import cqrs.writemodel.Repository
import domainmodel.generalpractitionerinfo.Visit
import domainmodel.medicalrecords.MedicalRecord
import domainmodel.medicalrecords.clinicaldiary.ClinicalDiary
import server.models.Protocol._

object GeneralPractitionerController {

  def apply(): Behavior[Command] = handleCommand()
  def handleCommand(): Behavior[Command] =
    Behaviors.receiveMessage {
      case InsertGeneralPractitionerInfo(generalPractitonerInfo, replyTo) =>
        val res = Repository.generalPractitionerRepository.insertGeneralPractitionerInfo(generalPractitonerInfo)
        if (res == "General practitioner info created.") {
          // ReadModel().insertGeneralPractitionerInfo(generalPractitonerInfo)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
      case UpdateGeneralPractitionerInfo(patientID, generalPractitionerInfo, replyTo) =>
        val res = Repository.generalPractitionerRepository.updateGeneralPractitionerInfo(patientID , generalPractitionerInfo)
        if (res == "General practitioner info updated.") {
          // ReadModel().updateGeneralPractitionerInfo(generalPractitonerInfo)
          replyTo ! Accepted(res)// actions that are to be performed after successful.
        } else {
          replyTo ! Rejected(res)
        }
        Behaviors.same
    }
}
