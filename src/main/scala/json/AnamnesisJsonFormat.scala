/*
 * Copyright (c) 2021.  Ylenia Battistini, Enrico Gnagnarella, Matteo Scucchia
 *
 *                              Licensed under the Apache License, Version 2.0 (the "License");
 *                              you may not use this file except in compliance with the License.
 *                              You may obtain a copy of the License at
 *
 *                                  http://www.apache.org/licenses/LICENSE-2.0
 *
 *                              Unless required by applicable law or agreed to in writing, software
 *                              distributed under the License is distributed on an "AS IS" BASIS,
 *                              WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *                              See the License for the specific language governing permissions and
 *                              limitations under the License.
 */

package json

import domainmodel.Familiars.Familiars
import domainmodel.Remotes.Remotes
import domainmodel._
import json.LocalDateJsonFormat.DateFormat
import json.PathologyJsonFormat.previousPathologiesJsonFormat
import json.RequestJsonFormats.{StringJsonFormat, immSetFormat, jsonFormat, jsonFormat3, jsonFormat4}
import spray.json.DefaultJsonProtocol.{jsonFormat1, jsonFormat2}
import spray.json.RootJsonFormat

object AnamnesisJsonFormat {


  implicit val kinshipDegreeJsonFormat: RootJsonFormat[KinshipDegree] = jsonFormat[KinshipDegree]

  implicit val remoteJsonFormat: RootJsonFormat[Remote] = jsonFormat2(Remote)

  implicit val familiarJsonFormat: RootJsonFormat[Familiar] = jsonFormat4(Familiar)

  implicit val physiologicJsonFormat: RootJsonFormat[Physiologic] = jsonFormat2(Physiologic)

  implicit val anamnesisJsonFormat: RootJsonFormat[Anamnesis] = jsonFormat3(Anamnesis)

  implicit val remotesJsonFormat: RootJsonFormat[Remotes] = jsonFormat1(Remotes)

  implicit val familiarsJsonFormat: RootJsonFormat[Familiars] = jsonFormat1(Familiars)

}
