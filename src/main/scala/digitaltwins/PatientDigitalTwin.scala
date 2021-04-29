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

package digitaltwins

import akka.actor
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import behaviours.CardiologyDiseasesPredictor
import cqrs.readmodel.RMUtility
import cqrs.writemodel.Repository.patientRepository
import domainmodel.Patient.Patient
import domainmodel.{CardiologyDiseasePrediction, CardiologyDiseasesPresence, CardiologyPrediction, PatientID}

import java.time.{LocalDate, Period}

trait Message

case class CardiologyVisitInserted() extends Message

object PatientDigitalTwin {

  val digitalTwins: actor.ActorSystem = akka.actor.ActorSystem.create("PervasiveHealthcare")
  var patientsDigitalTwins: Map[PatientID, ActorRef] = Map[PatientID, ActorRef]()

  def props(patientID: PatientID): Props = {
    Props(PatientDigitalTwin(patientID))
  }

  def initialize(): Unit = {
    RMUtility.getAllPatientID.foreach(id => patientsDigitalTwins = patientsDigitalTwins + (id -> digitalTwins.actorOf(props(id), id.value)))
  }

  case class PatientDigitalTwin(patientID: PatientID) extends Actor with ActorLogging {

    def makeCardiologyPrediction(patient: Patient): CardiologyDiseasePrediction = {
      val cardiologyVisits = patient.cardiologyVisitHistory.get.history.toList.sortWith((c1, c2) => c1.visitDate.visitDate.isAfter(c2.visitDate.visitDate))
      val age = Period.between(patient.birthDate, LocalDate.now()).toTotalMonths / 24
      CardiologyDiseasesPredictor.predict(age.toInt, patient.gender, cardiologyVisits.head)
    }

    private lazy val onInteractionBehaviour: Receive = {
      case CardiologyVisitInserted =>
        val patientOp = RMUtility.recreatePatientState(patientID)
        if (patientOp.nonEmpty) {
          val patient = patientOp.get
          val prediction = this.makeCardiologyPrediction(patient)
          if (prediction.isInstanceOf[CardiologyDiseasesPresence] && patient.generalPractitionerInfo.nonEmpty) {
            patientRepository.insertCardiologyPrediction(CardiologyPrediction(
              patientID,
              patient.generalPractitionerInfo.get.doctorID,
              patient.cardiologyVisitHistory.get.history.head
            ))
          }
        }
    }

    override def receive: Receive = this.onInteractionBehaviour
  }
}


