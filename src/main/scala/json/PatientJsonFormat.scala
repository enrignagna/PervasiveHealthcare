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

package json

import java.time.LocalDate

import cqrs.writemodel.Role
import domainmodel.{CF, PatientID}
import domainmodel.Patient.Patient
import domainmodel.generalinfo.GeneralInfo
import domainmodel.generalpractitionerinfo.GeneralPractitionerInfo
import domainmodel.medicalrecords.MedicalRecordHistory.MedicalRecordHistory
import json.AnamnesisJsonFormat.anamnesisJsonFormat
import spray.json.{DeserializationException, JsNull, JsNumber, JsObject, JsString, JsValue, RootJsonFormat}
import json.LocalDateJsonFormat.DateFormat
import json.generalpractitionerinfo.GeneralPractitionerInfoJsonFormat.generalPractitionerInfoJsonFormat
import json.medicalrecords.MedicalRecordJsonFormat.medicalRecordsJsonFormat
import json.generalinfo.GeneralInfoJsonFormat.generalInfoJsonFormat
import json.IDJsonFormat.patientIDJsonFormat
import spray.json.DefaultJsonProtocol.jsonFormat1
import json.RequestJsonFormats.{StringJsonFormat, jsonFormat16}
import spray.json.ImplicitDerivedJsonProtocol.implicitJsonFormat
/**
 * Json format for patient object.
 */
object PatientJsonFormat {

  /**
   * Implicit for surgeon object.
   */

  implicit object PatientJsonFormat extends RootJsonFormat[Patient] {
    override def read(json: JsValue): Patient = {
      json.asJsObject.getFields(
        "patientID", "cf", "name", "surname", "birthDate", "birthplace", "gender", "phone", "mobilePhone",
          "address", "residenceAddress", "residenceCity", "province", "role") match {
        case Seq(patientID, cf, JsString(name), JsString(surname), birthDate, JsString(birthplace), JsString(gender),
             JsString(phone), JsString(mobilePhone), JsString(address), JsString(residenceAddress), JsString(residenceCity),
             JsString(province), JsNumber(_)) =>
          Patient(patientID.convertTo[PatientID],cf.convertTo[CF], name, surname, birthDate.convertTo[LocalDate], birthplace, gender,
            phone, mobilePhone, address, residenceAddress, residenceCity, province,
            if(json.asJsObject.getFields("generalInfo").nonEmpty) Some(json.asJsObject.getFields("generalInfo").head.convertTo[GeneralInfo]) else None,
            if(json.asJsObject.getFields("generalPractitionerInfo").nonEmpty) Some(json.asJsObject.getFields("generalPractitionerInfo").head.convertTo[GeneralPractitionerInfo]) else None,
            if(json.asJsObject.getFields("medicalRecords").nonEmpty) Some(json.asJsObject.getFields("medicalRecords").head.convertTo[MedicalRecordHistory]) else None)

        case _ => throw DeserializationException("Patient expected")
      }
    }

    override def write(obj: Patient): JsValue = JsObject(
      "patientID" -> JsObject("value" -> JsString(obj.patientID.value)),
      "cf" -> JsObject("value" -> JsString(obj.cf.value)),
      "name" -> JsString(obj.name),
      "surname" -> JsString(obj.surname),
      "birthDate" -> JsObject("value" -> JsString(obj.birthDate.toString)),
      "birthplace" -> JsString(obj.birthplace),
      "gender" -> JsString(obj.gender),
      "phone" -> JsString(obj.phone),
      "mobilePhone" -> JsString(obj.mobilePhone),
      "address" -> JsString(obj.address),
      "residenceAddress" -> JsString(obj.residenceAddress),
      "residenceCity" -> JsString(obj.residenceCity),
      "province" -> JsString(obj.province),
      "generalInfo" -> obj.generalInfo.map(o => JsObject("value" -> JsString(o.toString))).getOrElse(JsNull), //da cambiare
      "generalPractitionerInfo" -> obj.generalPractitionerInfo.map(o => JsObject("value" -> JsString(o.toString))).getOrElse(JsNull),//da cambiare
      "medicalRecords" -> obj.medicalRecords.map(o => JsObject("value" -> JsString(o.toString))).getOrElse(JsNull),//da cambiare
      "role" -> JsNumber(Role.PATIENT.id)
    )
  }




  /**
   * Implicit for CF object.
   */
  implicit val cfJsonFormat: RootJsonFormat[CF] = jsonFormat1(CF)

  //implicit val patientJsonFormat: RootJsonFormat[Patient] = jsonFormat16(Patient)

}