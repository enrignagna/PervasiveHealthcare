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

package cqrs.writemodel

object Repository {
  val auth: Auth = new Auth()
  val adminRepository: AdminCRUD = new AdminCRUD()
  val surgeonRepository: SurgeonCRUD = new SurgeonCRUD()
  val rescuerRepository: RescuerCRUD = new RescuerCRUD()
  val wardNurseRepository: WardNurseCRUD = new WardNurseCRUD()
  val generalPractitionerRepository: GeneralPractitionerCRUD = new GeneralPractitionerCRUD()
  val instrumentalistRepository: InstrumentalistCRUD = new InstrumentalistCRUD()
  val anesthetistRepository: AnesthetistCRUD = new AnesthetistCRUD()

  def initialize(): Unit = {
    RoleCollection.initialize()
    GenderCollection.initialize()
    AllergyClassCollection.initialize()
    BloodTypeCollection.initialize()
    RhCollection.initialize()
  }
}
