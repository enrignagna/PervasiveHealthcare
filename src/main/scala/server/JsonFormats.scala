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

package server

import domainmodel.professionalfigure.{DoctorID, Specialization, Surgeon, Surgeons}
import server.models.Protocol.{Accepted, Confirmation}
import spray.json.{DefaultJsonProtocol, DerivedFormats, DeserializationException, JsNumber, JsString, JsValue, RootJsonFormat}

object JsonFormats extends DefaultJsonProtocol with DerivedFormats  {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import spray.json.DefaultJsonProtocol._

  implicit val doctorIDJsonFormat: RootJsonFormat[DoctorID] = jsonFormat1(DoctorID)
  implicit val acceptedJsonFormat: RootJsonFormat[Confirmation] = jsonFormat[Confirmation]

  //With ID, i prefer

  class EnumJsonConverter[T <: scala.Enumeration](enu: T) extends RootJsonFormat[T#Value] {
    override def write(obj: T#Value): JsValue = JsNumber(obj.id)

    override def read(json: JsValue): T#Value = {
      json match {
        case JsNumber(txt) => enu.withName(enu.values.toList(txt.intValue()).toString)
        case somethingElse => throw DeserializationException(s"Expected a value from enum $enu instead of $somethingElse")
      }
    }
  }

  //With string

  /*
  class EnumJsonConverter[T <: scala.Enumeration](enu: T) extends RootJsonFormat[T#Value] {
    override def write(obj: T#Value): JsValue = JsString(obj.toString)

    override def read(json: JsValue): T#Value = {
      json match {
        case JsString(txt) => enu.withName(txt)
        case somethingElse => throw DeserializationException(s"Expected a value from enum $enu instead of $somethingElse")
      }
    }
  }
  */

  implicit val enumConverter: EnumJsonConverter[Specialization.type] = new EnumJsonConverter(Specialization)
  implicit val surgeonJsonFormat: RootJsonFormat[Surgeon] = jsonFormat6(Surgeon)
  implicit val surgeonsJsonFormat: RootJsonFormat[Surgeons] = jsonFormat1(Surgeons)

}
