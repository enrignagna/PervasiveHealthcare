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


import MedicalRecords.InitialAnalysis.{Anamensis, Familiar, Physiologic, Remote}
import Utility.Info
import org.scalatest.freespec.AnyFreeSpec

class TestAnamensis extends AnyFreeSpec {

  "An Anamensis" - {
    "should have an optional familiar" in {
      val remote = Remote(Info("Patient with Covid-19"))
      val physiologic = Physiologic(Info("Patient have low level of emoglobine in blood"))
      val anamensis = Anamensis(Option.empty, remote, physiologic)
      assert(anamensis.familiar.get equals  Familiar(Option.empty))
    }

    "should produce NoSuchElementException when head is invoked" in {
      assertThrows[NoSuchElementException] {
        Set.empty.head
      }
    }
  }
}