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

package server.models

import cqrs.writemodel.Role
import server.models.JwtAuthentication.Tokens.Tokens

object JwtAuthentication {

  var tokens: Tokens = Tokens()

  case class Token(token: (String, Int))

  object Tokens {

    case class Tokens private(tokens: Map[String, Int] = Map.empty){
      def addNewToken(token: Token): Tokens = Tokens(tokens + token.token)
    }

    def apply(): Tokens = Tokens()

  }

  def hasAdminPermissions(token: String): Boolean = tokens.tokens.contains(token) && tokens.tokens(token) == Role.ADMIN.id

  def hasDoctorPermissions(token: String): Boolean = tokens.tokens.contains(token) && tokens.tokens(token) != Role.PATIENT.id

  def hasHospitalPermissions(token: String): Boolean = tokens.tokens.contains(token) && tokens.tokens(token) == Role.SURGEON.id || tokens.tokens(token) == Role.WARD_NURSE.id || tokens.tokens(token) == Role.INSTRUMENTALIST.id || tokens.tokens(token) == Role.ANESTHETIST.id

  def hasRescuerPermissions(token: String): Boolean = tokens.tokens.contains(token) && tokens.tokens(token) == Role.RESCUER.id
}
