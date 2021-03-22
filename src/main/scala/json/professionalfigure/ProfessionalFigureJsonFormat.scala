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

package json.professionalfigure

import cqrs.Role
import domainmodel.professionalfigure.{Anesthetist, DoctorID, Instrumentalist, Specialization, Surgeon, Surgeons}
import json.EnumerationJsonFormat.EnumJsonConverter
import spray.json.DefaultJsonProtocol._
import spray.json.{DeserializationException, JsNumber, JsObject, JsString, JsValue, RootJsonFormat}

import scala.collection.immutable.Map


object ProfessionalFigureJsonFormat {

  implicit val doctorIDJsonFormat: RootJsonFormat[DoctorID] = jsonFormat1(DoctorID)

  implicit val specializationJsonFormat: EnumJsonConverter[Specialization.type] = new EnumJsonConverter(Specialization)

  implicit object SurgeonJsonFormat extends RootJsonFormat[Surgeon] {
    override def read(json: JsValue): Surgeon = {
      json.asJsObject.getFields(
        "doctorID", "name", "surname", "phoneNumber", "email", "specialization", "role") match {
        case Seq(doctorID, JsString(name), JsString(surname), JsString(phoneNumber), JsString(email),
        JsNumber(specialization), JsNumber(_)) =>
          Surgeon(doctorID.convertTo[DoctorID], name, surname, phoneNumber,
          email, Specialization(specialization.toInt))
        case _ => throw DeserializationException("Surgeon expected")
      }
    }

    override def write(obj: Surgeon): JsValue = JsObject(
      "doctorID" -> JsObject("value" -> JsString(obj.doctorID.value)),
      "name" -> JsString(obj.name),
      "surname" -> JsString(obj.surname),
      "phoneNumber" -> JsString(obj.phoneNumber),
      "email" -> JsString(obj.email),
      "specialization" -> JsNumber(obj.specialization.id),
      "role" -> JsNumber(Role.SURGEON.id)
    )
  }

  implicit object AnesthetistJsonFormat extends RootJsonFormat[Anesthetist] {
    override def read(json: JsValue): Anesthetist = {
      json.asJsObject.getFields(
        "doctorID", "name", "surname", "phoneNumber", "email", "role") match {
        case Seq(doctorID, JsString(name), JsString(surname), JsString(phoneNumber),
        JsString(email), JsNumber(_)) => Anesthetist(doctorID.convertTo[DoctorID], name, surname, phoneNumber,
          email)
        case _ => throw DeserializationException("Anesthetist expected")
      }
    }

    override def write(obj: Anesthetist): JsValue = JsObject(
      "doctorID" -> JsObject("value" -> JsString(obj.doctorID.value)),
      "name" -> JsString(obj.name),
      "surname" -> JsString(obj.surname),
      "phoneNumber" -> JsString(obj.phoneNumber),
      "email" -> JsString(obj.email),
      "role" -> JsNumber(Role.ANESTHETIST.id)
    )
  }

  implicit object InstrumentalistJsonFormat extends RootJsonFormat[Instrumentalist] {
    override def read(json: JsValue): Instrumentalist = {
      json.asJsObject.getFields(
        "doctorID", "name", "surname", "phoneNumber", "email", "role") match {
        case Seq(doctorID, JsString(name), JsString(surname), JsString(phoneNumber),
        JsString(email), JsNumber(_)) => Instrumentalist(doctorID.convertTo[DoctorID], name, surname, phoneNumber,
          email)
        case _ => throw DeserializationException("Anesthetist expected")
      }
    }

    override def write(obj: Instrumentalist): JsValue = JsObject(
      "doctorID" -> JsObject("value" -> JsString(obj.doctorID.value)),
      "name" -> JsString(obj.name),
      "surname" -> JsString(obj.surname),
      "phoneNumber" -> JsString(obj.phoneNumber),
      "email" -> JsString(obj.email),
      "role" -> JsNumber(Role.INSTRUMENTALIST.id)
    )
  }

  //TODO other professional figures
  implicit val surgeonsJsonFormat: RootJsonFormat[Surgeons] = jsonFormat1(Surgeons)
}
