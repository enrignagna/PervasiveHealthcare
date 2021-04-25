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

import domainmodel.CardiologyVisitHistory.CardiologyVisitHistory
import domainmodel.{CardiologyVisit, CardiologyVisitID, ChestPainType, Cholesterol, FastingBloodSugar, MaxHeartRate, NumberVesselColoured, OldPeakST, RestingBloodPressure, RestingElectrocardiographic, SlopeST, Thal}
import json.EnumerationJsonFormat.EnumJsonConverter
import json.IDJsonFormat.{doctorIDJsonFormat, patientIDJsonFormat}
import json.RequestJsonFormats.{BooleanJsonFormat, DoubleJsonFormat, IntJsonFormat, StringJsonFormat, immSetFormat, jsonFormat1, jsonFormat13, jsonFormat14, jsonFormat15}
import json.generalpractitionerinfo.VisitJsonFormat.visitDateJsonFormat
import spray.json.RootJsonFormat

object CardiologyVisitJsonFormat {

  implicit val chestPainTypeJsonFormat: EnumJsonConverter[ChestPainType.type] = new EnumJsonConverter(ChestPainType)

  implicit val restingElectrocardiographicJsonFormat: EnumJsonConverter[RestingElectrocardiographic.type] = new EnumJsonConverter(RestingElectrocardiographic)

  implicit val slopeSTJsonFormat: EnumJsonConverter[SlopeST.type] = new EnumJsonConverter(SlopeST)

  implicit val thalJsonFormat: EnumJsonConverter[Thal.type] = new EnumJsonConverter(Thal)

  implicit val restingBloodPressureJsonFormat: RootJsonFormat[RestingBloodPressure] = jsonFormat1(RestingBloodPressure)

  implicit val cholesterolJsonFormat: RootJsonFormat[Cholesterol] = jsonFormat1(Cholesterol)

  implicit val fastingBloodSugarJsonFormat: RootJsonFormat[FastingBloodSugar] = jsonFormat1(FastingBloodSugar)

  implicit val maxHeartRateJsonFormat: RootJsonFormat[MaxHeartRate] = jsonFormat1(MaxHeartRate)

  implicit val oldPeakSTJsonFormat: RootJsonFormat[OldPeakST] = jsonFormat1(OldPeakST)

  implicit val numberVesselColouredJsonFormat: RootJsonFormat[NumberVesselColoured] = jsonFormat1(NumberVesselColoured)

  implicit val cardiologyVisitIDJsonFormat: RootJsonFormat[CardiologyVisitID] = jsonFormat1(CardiologyVisitID)

  implicit val cardiologyVisitJsonFormat: RootJsonFormat[CardiologyVisit] = jsonFormat15(CardiologyVisit)

  implicit val cardiologyVisitHistoryJsonFormat: RootJsonFormat[CardiologyVisitHistory] = jsonFormat1(CardiologyVisitHistory)

}
