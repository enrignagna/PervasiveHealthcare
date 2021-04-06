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

package json.medicalrecords.initialanalysis

import domainmodel.medicalrecords.initialanalysis.InitialAnalysis
import spray.json.DefaultJsonProtocol.jsonFormat3
import spray.json.RootJsonFormat
import json.medicalrecords.initialanalysis.StateEvaluationJsonFormat.stateEvaluationJsonFormat
import json.AnamnesisJsonFormat.anamnesisJsonFormat
import json.medicalrecords.initialanalysis.PhysicalExaminationJsonFormat.physicalExaminationJsonFormat

/**
 * Json format for initial analysis object.
 */
object InitialAnalysisJsonFormat {

  /**
   * Implicit for initial analysis object.
   */
  implicit val initialAnalysisJsonFormat: RootJsonFormat[InitialAnalysis] = jsonFormat3(InitialAnalysis)

}
