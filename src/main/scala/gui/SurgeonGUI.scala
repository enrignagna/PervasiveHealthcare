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

import java.awt.{Graphics, Toolkit}

import akka.actor.{ActorRef, ActorSystem, Props}
import client.surgeon.SurgeonActor
import client.surgeon.SurgeonMessage.AllMedicalRecordsMessage
import domainmodel._
import domainmodel.medicalrecords.MedicalRecord
import javax.swing._
import javax.swing.DefaultListModel
import scala.swing.BorderPanel.Position._
import scala.swing.ListView._
import scala.swing.TabbedPane._
import scala.swing.event._
import scala.swing.{BorderPanel, Dimension, ListView, MainFrame, Orientation, Slider, SplitPane, TabbedPane, TextArea, _}
import javax.swing.DefaultListModel
import java.awt.event.MouseAdapter
import java.awt.event.MouseListener

class SurgeonGUI(surgeonID: String, token: String, actorSystem: ActorSystem) extends MainFrame {

  val heightRatio = 1.5
  val widthRatio = 2
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = false
  visible = true

  title = "Scala Swing Surgeon Demo"

  val id: DoctorID = DoctorID(surgeonID)
  var i: Int = 0
  val surgeonActor: ActorRef = actorSystem.actorOf(Props(new SurgeonActor(id, token, this)))
  surgeonActor ! AllMedicalRecordsMessage()
  /*
  surgeonActor ! AllMedicalRecordsMessage()
  Thread.sleep(1000)
  surgeonActor ! UpdateMedicalRecordMessage(MedicalRecord(DoctorID("000000"), PatientID("000006"),
    MedicalRecordsID("MR000001"), isClosed = true, initialAnalysis = Some(InitialAnalysis(
      physicalExamination = PhysicalExamination(HospitalizationMotivation("Rottura dell'omero"), SystemsInvestigation("Nessuna")),
      stateEvaluation = StateEvaluation(Psychological("OK"), Nutritional("OK"), Educational("OK"), Social("OK"))))))
   */
  var listMedicalRecords: List[MedicalRecord] = List()
  val dialogGui = new DialogGUI()
  val medicalRecords = new ListView[String]

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

    val list: ListView[Page] = new ListView(tabs.pages) {
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
    listenTo(list)
    listenTo(list.selection)
    listenTo(medicalRecords.selection)
    listenTo(mouse.clicks)
    reactions += {
      case ValueChanged(`slider`) =>
        if (!slider.adjusting || reactLive) tabs.selection.index = slider.value
        slider.value match {
          case 0 => surgeonActor ! AllMedicalRecordsMessage()
          case 1 => dialogGui.showDialog()
        }
      case SelectionChanged(`tabs`) =>
        slider.value = tabs.selection.index
        list.selectIndices(tabs.selection.index)
      case SelectionChanged(`list`) =>
        if (list.selection.items.length == 1)
          tabs.selection.page = list.selection.items.head
      case ListSelectionChanged(_) =>
        val index = medicalRecords.peer.getSelectedIndex + 1

        val medicalRecordsGUI = new MedicalRecordsGUI(listMedicalRecords.take(index).head, id, surgeonActor)
        medicalRecordsGUI.visible = true
    }
  }

  def updateMedicalRecord(medicalRecordJson: List[MedicalRecord]): Unit = {
    listMedicalRecords = medicalRecordJson
    medicalRecords.listData = listMedicalRecords.map(m => m.medicalRecordID.value.concat(" ").concat(m.patientID.value.concat(" ").concat(m.isClosed.toString).concat("\n")))
    medicalRecords.peer.repaint()
  }

}

/*
object SurgeonGUI {

  def main(args: Array[String]): Unit = {
    val gui = SurgeonGUI("prova")
    gui.visible = true
  }

  def apply(surgeonID: String): SurgeonGUI = new SurgeonGUI(surgeonID)


}

 */
