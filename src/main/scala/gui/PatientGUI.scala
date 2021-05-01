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
import client.patient.Message.{GetMyInfoMessage}
import client.patient.PatientActor
import domainmodel.PatientID
import domainmodel.medicalrecords.MedicalRecord
import org.bson.json.JsonObject

import scala.swing.BorderPanel.Position._
import scala.swing.ListView._
import scala.swing.TabbedPane._
import scala.swing._
import scala.swing.event._

class PatientGUI(val patientID: String, token: String, actorSystem: ActorSystem) extends MainFrame {

  val heightRatio = 1.5
  val widthRatio = 2
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = false
  visible = true

  val dialogGUI = new DialogGUI()
  val id: PatientID = PatientID(patientID)
  var prescriptions: ListView[String] = createListView()
  val historicalCures: TextArea = createTextArea()
  val generalPractitionerInfo: TextArea = createTextArea()
  var medicalRecords: ListView[String] = createListView()
  var listMedicalRecords: List[MedicalRecord] = List()

  title = "Scala Swing Patient Demo"

  val patientActor: ActorRef = actorSystem.actorOf(Props(
    new PatientActor(PatientID(patientID), token, this)
  ))

  patientActor ! GetMyInfoMessage()

  val generalInfo: TextArea = new TextArea(5, 25) {

    editable = false
  }

  /*
   * The root component in this frame is a panel with a border layout.
   */
  contents = new BorderPanel {

    var reactLive = false

    val tabs: TabbedPane = new TabbedPane {
      val logout: TextArea = createTextArea()

      pages += new Page("Informazioni generali", generalInfo)
      pages += new Page("Informazioni cliniche di base", generalPractitionerInfo)
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
    listenTo(slider, tabs.selection, list.selection, prescriptions.selection, prescriptions, medicalRecords.selection, medicalRecords)
    reactions += {
      case ValueChanged(`slider`) =>
        if (!slider.adjusting || reactLive) tabs.selection.index = slider.value
        slider.value match {
            /*
          case 0 => patientActor ! GeneralInfoMessage
          case 1 => patientActor ! GeneralPractitionerInfoMessage
          case 2 => patientActor ! AllMedicalRecordMessage

             */
          case 3 => dialogGUI.showDialog()
        }
      case SelectionChanged(`tabs`) =>
        slider.value = tabs.selection.index
        list.selectIndices(tabs.selection.index)
      case SelectionChanged(`list`) =>
        if (list.selection.items.length == 1)
          tabs.selection.page = list.selection.items(0)
      case ListSelectionChanged(list, _, _) =>
        val medicalRecordsGUI = new MedicalRecordsGUI(listMedicalRecords.filter(id => id.medicalRecordID.value.equals(list.selection.items.head.toString)).head, id, patientActor)
        medicalRecordsGUI.visible = true
        println("Selection item", list.selection.items.head)
    }
  }

  private def createTextArea(): TextArea = {
    new TextArea(5, 25) {
      editable = false
    }
  }

  private def createListView(): ListView[String] = {
    new ListView[String] {
      listData = Seq("000001", "Battistini", "Ylenia", "BTTYLN97S62C573Y")
      selection.intervalMode = ListView.IntervalMode.Single
    }
  }

  def updateGeneralInfo(generalInfoJson: JsonObject): Unit = {
    generalInfo.text = generalInfoJson.getJson
  }

  def updatePrescriptions(prescriptionJson: JsonObject): Unit = {
    prescriptions.listData = Seq(String.valueOf(prescriptionJson.getJson))
  }

  def updateGeneralPractitionerInfo(generalPractitionerInfoJson: JsonObject): Unit = {
    generalPractitionerInfo.text = generalPractitionerInfoJson.getJson
  }

  def updateMedicalRecord(medicalRecordJson: List[MedicalRecord]): Unit = {
    listMedicalRecords = medicalRecordJson
    medicalRecords.listData = Seq(medicalRecordJson.foreach(x => Seq(x.medicalRecordID.value, String.valueOf(x.isClosed))).toString)
  }

  def updateHistorical(historicalJson: JsonObject): Unit = {
    historicalCures.text = historicalJson.getJson
  }
}

/*
object PatientGUI {

  def main(args: Array[String]): Unit = {
    val gui = PatientGUI("prova")
    gui.visible = true
  }

  def apply(patientID: String): PatientGUI = new PatientGUI(patientID)
}

 */