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

package domainmodel

import domainmodel.Familiars.Familiars
import domainmodel.Remotes.Remotes
import org.junit.runner.RunWith
import org.scalatest.freespec._
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AnamnesisTest extends AnyFreeSpec {
  val remote: Remote = Remote("first anamnesis")
  val familiar: Familiar = Familiar("Rossi Mario", Father, PreviousPathologies() ,"3387514876")
  val anamnesis: Anamensis = Anamensis(familiar, remote, Physiologic("self conscious"))
  "An anamnesis should have" - {
    "a familiar information" in {
      assert(anamnesis.familiar != null)
    }
    "a remote information" in {
      assert(anamnesis.remote != null)
    }
    "a physiologic information" in {
      assert(anamnesis.physiologic != null)
    }
  }

  val remotes: Remotes = domainmodel.Remotes()
    "A remotes" - {
      "should be initially empty" in {
        assert(remotes.remotes.isEmpty)
      }

      "can added" in {
        assert(remotes.addNewRemote(remote).remotes.nonEmpty)
      }
  }

  val familiars: Familiars = domainmodel.Familiars()
  "A familiars" - {
    "should be initially empty" in {
      assert(familiars.familiars.isEmpty)
    }

    "can added" in {
      assert(familiars.addNewFamiliar(familiar).familiars.nonEmpty)
    }
  }

}