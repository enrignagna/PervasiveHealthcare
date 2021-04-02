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

package cqrs.readmodel.eventsourcing

import java.util

import domainmodel.DoctorID

//TODO: conversion of hashmap in scala collection
object EventStore {
  private val store = new util.HashMap[DoctorID, Set[Event]]

  def addEvent(id: DoctorID, event: Event): Unit = {
    var events = store.get(id)
    if (events == null) {
      events = Set.empty
      store.put(id, events + event)
    }
    else store.replace(id, events + event)
  }

  def getEvents(id: DoctorID): Set[Event] = store.get(id)
}
