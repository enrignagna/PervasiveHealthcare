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
package gui

import java.awt.{Dimension, Toolkit}

import akka.actor.{ActorRef, ActorSystem, Props}
import client.patient.Message.AllMedicalRecordMessage
import client.surgeon.SurgeonActor
import domainmodel.DoctorID
import domainmodel.medicalrecords.MedicalRecord

import scala.swing.BorderPanel.Position._
import scala.swing.ListView._
import scala.swing.TabbedPane._
import scala.swing._
import scala.swing.event._

class SurgeonGUI(surgeonID: String, actorSystem: ActorSystem) extends MainFrame {

  val heightRatio = 1.5
  val widthRatio = 2
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = false

  title = "Scala Swing Surgeon Demo"

  val id : DoctorID = DoctorID(surgeonID)
  val surgeonActor: ActorRef = actorSystem.actorOf(Props(new SurgeonActor(id)), name = "surgeon")

  var medicalRecords: ListView[String] = new ListView[String]()
  var listMedicalRecords: List[MedicalRecord] = List()
  val dialogGui = new DialogGUI()
  /*
   * The root component in this frame is a panel with a border layout.
   */
  contents = new BorderPanel {

    var reactLive = false

    val tabs: TabbedPane = new TabbedPane {
      val logout: TextArea = new TextArea(5, 25) {
        editable = false
      }

      pages += new Page("Cartelle cliniche", medicalRecords)
      pages += new Page("Logout", logout)
    }

    val list = new ListView(tabs.pages) {
      selectIndices(0)
      selection.intervalMode = ListView.IntervalMode.Single
      renderer = ListView.Renderer(_.title)
    }
    val center: SplitPane = new SplitPane(Orientation.Vertical, new ScrollPane(list), tabs) {
      oneTouchExpandable = true
      continuousLayout = true
    }
    layout(center) = Center

    /*
     * This slider is used above, so we need lazy initialization semantics.
     * Objects or lazy vals are the way to go, but objects give us better
     * type inference at times.
     */
    object slider extends Slider {
      min = 0
      value = tabs.selection.index
      max = tabs.pages.size - 1
      majorTickSpacing = 1
    }

    /*
     * Establish connection between the tab pane, slider, and list view.
     */
    listenTo(slider)
    listenTo(tabs.selection)
    listenTo(list.selection)
    reactions += {
      case ValueChanged(`slider`) =>
        if (!slider.adjusting || reactLive) tabs.selection.index = slider.value
        slider.value match {
          case 0 => surgeonActor ! AllMedicalRecordMessage
          case 1 => dialogGui.showDialog()
        }
      case SelectionChanged(`tabs`) =>
        slider.value = tabs.selection.index
        list.selectIndices(tabs.selection.index)
      case SelectionChanged(`list`) =>
        if (list.selection.items.length == 1)
          tabs.selection.page = list.selection.items.head
      case ListSelectionChanged(list, _, _) =>
        val medicalRecordsGUI = MedicalRecordsGUI()(listMedicalRecords.filter(id => id.medicalRecordID.value.equals(list.selection.items.head.toString)).head, id, surgeonActor)
        medicalRecordsGUI.visible = true
        println("Selection item", list.selection.items.head)
    }
  }

  def updateMedicalRecord(medicalRecordJson: List[MedicalRecord]): Unit = {
    listMedicalRecords = medicalRecordJson
    medicalRecords.listData = Seq(medicalRecordJson.foreach(x => Seq(x.medicalRecordID.value, String.valueOf(x.isClosed))).toString)
  }
}


object SurgeonGUI {

  def main(args: Array[String]): Unit = {
    val gui = SurgeonGUI("prova")
    gui.visible = true
  }

  def apply(surgeonID: String): SurgeonGUI = new SurgeonGUI(surgeonID)


}
