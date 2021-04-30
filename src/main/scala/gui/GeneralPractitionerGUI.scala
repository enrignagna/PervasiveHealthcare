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
import client.generalpractitioner.GeneralPractitionerActor
import domainmodel.DoctorID
import domainmodel.generalpractitionerinfo.GeneralPractitionerInfo
import javax.swing.{JList, JOptionPane, JPanel}

import scala.swing.BorderPanel.Position._
import scala.swing.ListView._
import scala.swing.TabbedPane._
import scala.swing._
import scala.swing.event._

class GeneralPractitionerGUI(generalPractitionerID: String, token:String, actorSystem: ActorSystem) extends MainFrame {

  val heightRatio = 1.5
  val widthRatio = 2
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = false

  title = "Scala Swing General Practitioner Demo"

  val id : DoctorID = DoctorID(generalPractitionerID)
  var listGeneralPractitionerInformations: List[GeneralPractitionerInfo] = List()
  val generalPractitionerActor: ActorRef = actorSystem.actorOf(Props(
    new GeneralPractitionerActor(id)
  ), name = "generalPractitioner")

  var generalPractitionerInformations: JList[JPanel] = new JList[JPanel]()
  var newGeneralPractitionerInformation: JPanel= new JPanel
  //generalPractitionerActor ! //TODO: manca mesaggio

  /*
   * The root component in this frame is a panel with a border layout.
   */
  contents = new BorderPanel {

    var reactLive = false

    val tabs: TabbedPane = new TabbedPane {
      val logout: TextArea = new TextArea(5, 25) {
        editable = false
      }

      pages += new Page("Informazioni medice di base", new Panel{generalPractitionerInformations})
      pages += new Page("Nuova informazione medica di base", new Panel{newGeneralPractitionerInformation})
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
    listenTo(list.selection)
    reactions += {
      case ValueChanged(`slider`) =>
        if (!slider.adjusting || reactLive) tabs.selection.index = slider.value
        slider.value match {
          case 0 => println("")//generalPractitionerActor ! //todo:manca messaggio
          case 2 => showDialog()
        }
      case SelectionChanged(`tabs`) =>
        slider.value = tabs.selection.index
        list.selectIndices(tabs.selection.index)
      case SelectionChanged(`list`) =>
        if (list.selection.items.length == 1)
          tabs.selection.page = list.selection.items(0)
      case ListSelectionChanged(list, _, _) =>
        val generalPractitionerInfoGUI = ??? //new GeneralPractitionerInfoGUI(listGeneralPractitionerInformations.filter(genParc => genParc..value.equals(list.selection.items.head.toString)).head, id, generalPractitionerActor)
        //generalPractitionerInfoGUI.visible = true
        println("Selection item", list.selection.items.head)
    }
  }

  def updateGeneralPractitionerInfoRecord(generalPractitionerInformationJson: List[GeneralPractitionerInfo]): Unit = {
    listGeneralPractitionerInformations = generalPractitionerInformationJson
    //listGeneralPractitionerInformations = Seq(listGeneralPractitionerInformations.foreach(x => Seq(x.medicalRecordID.value, String.valueOf(x.isClosed))).toString)
  }

  private def showDialog(): Unit = {
    val dialogResult: Int = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire???", "Logout", JOptionPane.YES_NO_OPTION)
    if (dialogResult == JOptionPane.YES_OPTION) {
      val login = new LoginGUI(???)
      login.visible = true
    }
  }
}