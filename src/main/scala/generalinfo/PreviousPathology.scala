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

package generalinfo

import java.time.LocalDate

import generalinfo.PathologySeverity.PathologySeverity

//Four levels of severity
object PathologySeverityLevels extends Enumeration {
  type PathologySeverityLevels = Value
  val ONE, TWO, THREE, FOUR = Value
}

import PathologySeverityLevels.PathologySeverityLevels

//TODO maybe is more correct use case objects
object PathologySeverity{
  case class PathologySeverity private(description: String, severity: PathologySeverityLevels)

  def apply(pathologySeverityLevels: PathologySeverityLevels): PathologySeverity = pathologySeverityLevels match {
    case PathologySeverityLevels.ONE => PathologySeverity(description = "Low disease", severity = PathologySeverityLevels.ONE)
    case PathologySeverityLevels.TWO => PathologySeverity(description = "Changes life quality", severity = PathologySeverityLevels.TWO)
    case PathologySeverityLevels.THREE => PathologySeverity(description = "Causes disability", severity = PathologySeverityLevels.THREE)
    case PathologySeverityLevels.FOUR => PathologySeverity(description = "Threatens life", severity = PathologySeverityLevels.FOUR)
  }
}

//TODO: check correctness
case class DetectionDate(detectionDate: LocalDate = LocalDate.now())

case class PathologyName(name: String)

case class PreviousPathology(pathologyName: PathologyName, detectionDate: DetectionDate, pathologySeverity: PathologySeverity)

object PreviousPathologies{
  case class PreviousPathologies private (previousPathologies: Set[PreviousPathology] = Set.empty){
    def addNewPathology(previousPathology: PreviousPathology): PreviousPathologies = PreviousPathologies(this.previousPathologies + previousPathology)
  }

  def apply(): PreviousPathologies = PreviousPathologies()
}

