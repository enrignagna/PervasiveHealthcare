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

import domainmodel.professionalfigure._
import spray.json.DefaultJsonProtocol._
import spray.json.{DeserializationException, JsNumber, JsObject, JsString, JsValue, RootJsonFormat}


object DoctorJsonFormat {

  implicit val doctorIDJsonFormat: RootJsonFormat[DoctorID] = jsonFormat1(DoctorID)

  implicit object DoctorJsonFormat extends RootJsonFormat[Doctor] {
    override def read(json: JsValue): Doctor = {
      json.asJsObject.getFields(
        "doctorID", "name", "surname", "phoneNumber", "email", "medicalDegreeGrade") match {
        case Seq(doctorID, JsString(name), JsString(surname), JsString(phoneNumber), JsString(email), JsString(medicalDegreeGrade),
          JsNumber(specialization), JsNumber(_)) =>
          Surgeon(doctorID.convertTo[DoctorID], name, surname, phoneNumber,
            email, medicalDegreeGrade, Specialization(specialization.toInt))
        case Seq(doctorID, JsString(name), JsString(surname), JsString(phoneNumber),
          JsString(email), JsString(medicalDegreeGrade), JsNumber(_)) =>
          Anesthetist(doctorID.convertTo[DoctorID], name, surname, phoneNumber, email, medicalDegreeGrade)
        case Seq(doctorID, JsString(name), JsString(surname), JsString(phoneNumber),
          JsString(email), JsString(medicalDegreeGrade), JsNumber(_)) =>
          GeneralPractitioner(doctorID.convertTo[DoctorID], name, surname, phoneNumber, email, medicalDegreeGrade)
        case _ => throw DeserializationException("Doctor expected")
      }
    }

    override def write(obj: Doctor): JsValue = JsObject(
      "doctorID" -> JsObject("value" -> JsString(obj.doctorID.value)),
      "name" -> JsString(obj.name),
      "surname" -> JsString(obj.surname),
      "phoneNumber" -> JsString(obj.phoneNumber),
      "email" -> JsString(obj.email),
      "medicalDegreeGrade" -> JsString(obj.medicalDegreeGrade)
    )
  }

}
