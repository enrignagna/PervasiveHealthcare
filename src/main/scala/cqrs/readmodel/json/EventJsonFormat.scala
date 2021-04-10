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

package cqrs.readmodel.json

import cqrs.readmodel.eventsourcing._
import json.IDJsonFormat.{doctorIDJsonFormat, patientIDJsonFormat}
import json.LocalDateTimeJsonFormat.DateTimeFormat
import json.PatientJsonFormat.PatientJsonFormat
import json.RequestJsonFormats.{IntJsonFormat, jsonFormat4}
import json.generalinfo.GeneralInfoJsonFormat.generalInfoJsonFormat
import json.generalpractitionerinfo.GeneralPractitionerInfoJsonFormat.generalPractitionerInfoJsonFormat
import json.medicalrecords.MedicalRecordJsonFormat.medicalRecordJsonFormat
import json.professionalfigure.ProfessionalFigureJsonFormat._
import spray.json.RootJsonFormat

object EventJsonFormat {
  implicit val insertSurgeonEventJsonFormat: RootJsonFormat[InsertSurgeonEvent] = jsonFormat4(InsertSurgeonEvent)
  implicit val updateSurgeonEventJsonFormat: RootJsonFormat[UpdateSurgeonEvent] = jsonFormat4(UpdateSurgeonEvent)

  implicit val insertInstrumentalistEventJsonFormat: RootJsonFormat[InsertInstrumentalistEvent] = jsonFormat4(InsertInstrumentalistEvent)
  implicit val updateInstrumentalistEventJsonFormat: RootJsonFormat[UpdateInstrumentalistEvent] = jsonFormat4(UpdateInstrumentalistEvent)

  implicit val insertAnesthetistEventJsonFormat: RootJsonFormat[InsertAnesthetistEvent] = jsonFormat4(InsertAnesthetistEvent)
  implicit val updateAnesthetistEventJsonFormat: RootJsonFormat[UpdateAnesthetistEvent] = jsonFormat4(UpdateAnesthetistEvent)

  implicit val insertGeneralPractitionerEventJsonFormat: RootJsonFormat[InsertGeneralPractitionerEvent] = jsonFormat4(InsertGeneralPractitionerEvent)
  implicit val updateGeneralPractitionerEventJsonFormat: RootJsonFormat[UpdateGeneralPractitionerEvent] = jsonFormat4(UpdateGeneralPractitionerEvent)

  implicit val insertWardNurseEventJsonFormat: RootJsonFormat[InsertWardNurseEvent] = jsonFormat4(InsertWardNurseEvent)
  implicit val updateWardNurseEventJsonFormat: RootJsonFormat[UpdateWardNurseEvent] = jsonFormat4(UpdateWardNurseEvent)

  implicit val insertRescuerEventJsonFormat: RootJsonFormat[InsertRescuerEvent] = jsonFormat4(InsertRescuerEvent)
  implicit val updateRescuerEventJsonFormat: RootJsonFormat[UpdateRescuerEvent] = jsonFormat4(UpdateRescuerEvent)

  implicit val insertPatientInfoEventJsonFormat: RootJsonFormat[InsertPatientInfoEvent] = jsonFormat4(InsertPatientInfoEvent)
  implicit val updatePatientInfoEventJsonFormat: RootJsonFormat[UpdatePatientInfoEvent] = jsonFormat4(UpdatePatientInfoEvent)

  implicit val insertMedicalRecordEventJsonFormat: RootJsonFormat[InsertMedicalRecordEvent] = jsonFormat4(InsertMedicalRecordEvent)
  implicit val updateMedicalRecordEventJsonFormat: RootJsonFormat[UpdateMedicalRecordEvent] = jsonFormat4(UpdateMedicalRecordEvent)

  implicit val insertGeneralInfoEventJsonFormat: RootJsonFormat[InsertGeneralInfoEvent] = jsonFormat4(InsertGeneralInfoEvent)
  implicit val updateGeneralInfoEventJsonFormat: RootJsonFormat[UpdateGeneralInfoEvent] = jsonFormat4(UpdateGeneralInfoEvent)

  implicit val insertGeneralPractitionerInfoEventJsonFormat: RootJsonFormat[InsertGeneralPractitionerInfoEvent] = jsonFormat4(InsertGeneralPractitionerInfoEvent)
  implicit val updateGeneralPractitionerInfoEventJsonFormat: RootJsonFormat[UpdateGeneralPractitionerInfoEvent] = jsonFormat4(UpdateGeneralPractitionerInfoEvent)

}
