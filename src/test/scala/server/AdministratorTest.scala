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

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.junit.runner.RunWith
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AdministratorTest extends AnyFreeSpec with Matchers with ScalaFutures with ScalatestRouteTest {
//
//  lazy val testKit: ActorTestKit = ActorTestKit()
//
//  implicit def typedSystem: ActorSystem[Nothing] = testKit.system
//
//  override def createActorSystem(): akka.actor.ActorSystem =
//    testKit.system.classicSystem
//
//  val id = "surgeon1"
//  val administratorController: ActorRef[Protocol.Command] = testKit.spawn(AdministratorController())
//  lazy val routes: Route = new AdministratorRoutes(administratorController).administratorRoutes
//
//  "AdministratorRoute should" - {
//    "return no surgeon if no present (GET /surgeons)" in {
//      val request = HttpRequest(uri = "/surgeons")
//
//      request ~> routes ~> check {
//        status should ===(StatusCodes.OK)
//
//        contentType should ===(ContentTypes.`application/json`)
//        entityAs[String] should ===("""{"surgeons":[]}""")
//      }
//    }
//
//    /*
//        "be able to add surgeons (POST /surgeons)" in {
//          val surgeon = Surgeon(DoctorID("12345"), "Marco", "Rossi", "333444555", "marco@gmail.com", Specialization.GENERAL_SURGERY)
//          val surgeonEntity = Marshal(surgeon).to[MessageEntity].futureValue // futureValue is from ScalaFutures
//
//          val request = Post("/surgeons").withEntity(surgeonEntity)
//
//          request ~> routes ~> check {
//            status should ===(StatusCodes.Created)
//
//            contentType should ===(ContentTypes.`application/json`)
//
//            entityAs[String] should ===("""{"doctorID":{"value":"12345"},"email":"marco@gmail.com","name":"Marco","phoneNumber":"333444555","specialization":0,"surname":"Rossi"}""")
//          }
//        }
//
//    */
//
//  }

}

