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

package json.generalinfo

import domainmodel.generalinfo.{GeneralInfo, Height, Weight}
import json.PathologyJsonFormat.previousPathologiesJsonFormat
import json.RequestJsonFormats.DoubleJsonFormat
import json.generalinfo.AllergyJsonFormat.allergiesJsonFormat
import json.generalinfo.BloodGroupJsonFormat.bloodGroupJsonFormat
import json.generalinfo.ExamJsonFormat.examHistoryJsonFormat
import json.generalinfo.PrescriptionJsonFormat.prescriptionHistoryJsonFormat
import spray.json.DefaultJsonProtocol.{IntJsonFormat, jsonFormat1, jsonFormat7}
import spray.json.RootJsonFormat

/**
 * Json format for general info object.
 */
object GeneralInfoJsonFormat {

  /**
   * Implicit for weight object.
   */
  implicit val weightJsonFormat: RootJsonFormat[Weight] = jsonFormat1(Weight)

  /**
   * Implicit for optional weight object.
   */
  //  implicit object weightJsonFormat extends RootJsonFormat[Weight] {
  //    override def read(json: JsValue): Weight =
  //      json.asJsObject.getFields("value") match {
  //        case Seq(JsNumber(value)) => Weight(
  //          //value.doubleValue()
  //          if (json.asJsObject.getFields("value").nonEmpty)
  //            Some(json.asJsObject.getFields("value").head.convertTo[Double])
  //          else
  //            None
  //
  //        )
  //        case _ => throw DeserializationException("Weight expected")
  //
  //      }
  //
  //    override def write(obj: Weight): JsValue = {
  //      JsObject("value" -> JsNumber(obj.value))
  //    }
  //}

  /**
   * Implicit for height object.
   */
  implicit val heightJsonFormat: RootJsonFormat[Height] = jsonFormat1(Height)

  /**
   * Implicit for general info object.
   */
  implicit val generalInfoJsonFormat: RootJsonFormat[GeneralInfo] = jsonFormat7(GeneralInfo)
}
