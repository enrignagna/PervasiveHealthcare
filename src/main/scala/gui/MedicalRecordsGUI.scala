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

import java.awt.{Color => _, Dimension => _, TextArea => _, _}

import akka.actor.ActorRef
import domainmodel.medicalrecords.MedicalRecord
import domainmodel.{ID, PatientID}
import javax.swing._

import scala.swing.BorderPanel.Position._
import scala.swing.ListView._
import scala.swing.TabbedPane._
import scala.swing.event._
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, Label, ListView, MainFrame, Orientation, RadioButton, Slider, SplitPane, TabbedPane, TextArea}

class MedicalRecordsGUI(medicalRecord: MedicalRecord, id: ID, actor: ActorRef) extends MainFrame {

  val heightRatio = 1.5
  val widthRatio = 2
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = false

  private val patientID = new Label {
    text = "ID paziente"
  }
  private val medicalRecordIDtitle = new Label {
    text = "ID cartella clinica"
  }
  private val updateButton = new Button {
    text = "Aggiorna"
    enabled = checkId()
  }
  private val patientIDtxt = new TextArea {
    editable = false
    text = id.value
    preferredSize = new Dimension(5, 3)
  }
  private val medicalRecordsIDtxt = new TextArea {
    preferredSize = new Dimension(2, 1)
    text = medicalRecord.medicalRecordID.value
    editable = false
  }
  val isClosedButton: RadioButton = new RadioButton() {
    name = "Cartella clinica:"
    text = "Aperta"
    text = "Chiusa"
    visible = checkId()
  }

  val initialAnalysis: TextArea = createTextArea(medicalRecord.initialAnalysis)
  val clinicalDiary: TextArea = createTextArea(medicalRecord.clinicalDiary)
  val operatingReports: TextArea = createTextArea(medicalRecord.operatingReports)
  /*
 * The root component in this frame is a panel with a border layout.
 */
  contents = new BorderPanel {

    var reactLive = false

    val familiarPanel: JPanel = createFamiliarPanel()
    val remotePanel: JPanel = createRemotePhysiologicPanel("Remota")
    val physiologicPanel: JPanel = createRemotePhysiologicPanel("Fisiologica")

    val anamnesiPanel: JPanel = new JPanel
    anamnesiPanel.setBorder(BorderFactory.createTitledBorder("Anamnesi"))
    anamnesiPanel.setLayout(new GridLayout(1, 3))
    anamnesiPanel.add(familiarPanel, SwingConstants.LEFT)
    anamnesiPanel.add(remotePanel, SwingConstants.CENTER)
    anamnesiPanel.add(physiologicPanel, SwingConstants.RIGHT)

    val hospitalizationMotivation: JLabel = new JLabel {
      setText("Motivazione ricovero ospedaliero")
    }

    val systemsInvestigation: JLabel = new JLabel {
      setText("Investigazione")
    }

    val hospitalizationMotivationtxt: JTextArea = new JTextArea {
      setEditable(checkId())
      setPreferredSize(new Dimension(5, 3))
    }

    val systemsInvestigationtxt: JTextArea = new JTextArea {
      setEditable(checkId())
      setPreferredSize(new Dimension(5, 3))
    }

    val physicalExaminationPanel: JPanel = new JPanel
    physicalExaminationPanel.setBorder(BorderFactory.createTitledBorder("Esaminazione fisica"))
    physicalExaminationPanel.add(hospitalizationMotivation)
    physicalExaminationPanel.add(hospitalizationMotivationtxt)
    physicalExaminationPanel.add(systemsInvestigation)
    physicalExaminationPanel.add(systemsInvestigationtxt)


    val stateEvaluationPanel: JPanel = new JPanel {
      title = "Valutazione dello stato"

    }
    stateEvaluationPanel.add(familiarPanel)
    stateEvaluationPanel.add(remotePanel)
    stateEvaluationPanel.add(physiologicPanel)
    val tabs: TabbedPane = new TabbedPane {

      val home: BoxPanel = new BoxPanel(Orientation.Vertical) {
        contents += new BoxPanel(Orientation.Vertical) {
          contents += patientID
          contents += patientIDtxt
        }
        contents += new BoxPanel(Orientation.Vertical) {
          contents += medicalRecordIDtitle
          contents += medicalRecordsIDtxt
        }
        contents += new BoxPanel(Orientation.Vertical) {
          contents += isClosedButton
          contents += updateButton
        }
      }

      pages += new Page("Home", home)
      pages += new Page("Analisi iniziale", initialAnalysis)
      pages += new Page("Diario clinico", clinicalDiary)
      //TODO-- ClinicalDiary(HealthEvolution(Info(value), dateTime), DiagnosticTreatments(Set(DiagnosticTreatment(date,description(value)))), TherapeuticTreatments(Set(TherapeuticTreatment(date, description(value)))),
      //RehabilitationTreatments(Set(RehabilitationTreatment(date, description(value)))))
      pages += new Page("Report delle operazioni", operatingReports) //TODO:---OperatingReports(Medical(Set(surgeon), Set(anesthetist), Set(instrumentalist)), data, interventionType(value))
    }

    val list: ListView[Page] = new ListView(tabs.pages) {
      selectIndices(0)
      selection.intervalMode = ListView.IntervalMode.Single
      renderer = ListView.Renderer(_.title)
    }
    val center: SplitPane = new SplitPane(Orientation.Vertical, ???/*new ScrollPane(list)*/, tabs) {
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
    listenTo(slider, tabs.selection, list.selection, isClosedButton, updateButton)
    reactions += {
      case ValueChanged(`slider`) =>
        if (!slider.adjusting || reactLive) tabs.selection.index = slider.value
      case SelectionChanged(`tabs`) =>
        slider.value = tabs.selection.index
        list.selectIndices(tabs.selection.index)
      case SelectionChanged(`list`) =>
        if (list.selection.items.length == 1)
          tabs.selection.page = list.selection.items.head
      case ButtonClicked(`isClosedButton`) =>
        isClosedButton.text match {
          case "Chiusa" => println("chiudi cartella clinica")
        }
      case ButtonClicked(`updateButton`) =>
        recreateMedicalRecord()
    }
  }

  private def checkId(): Boolean = {
    if (id.equals(PatientID(id.value))) {
      false
    } else true
  }

  private def createTextArea(value: Option[_]): TextArea = {
    new TextArea(5, 25) {
      text = value.get.toString
      editable = checkId()
    }
  }

  private def recreateMedicalRecord(): Unit = {
    val closed = if (isClosedButton.text.equals("Chiusa")) true else false
    /*val newMedicalRecord = MedicalRecord(medicalRecord.doctorID, medicalRecord.patientID, medicalRecord.medicalRecordID, closed,
      if(initialAnalysis.text.nonEmpty) Some() else None, if())*/
  }

  private def createFamiliarPanel(): JPanel = {
    val nameSurname = new JLabel {
      title = "Nome Cognome"
    }
    val txtUserName: JTextField = new JTextField() {
      val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / 1000
      val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / 500
      setPreferredSize(new Dimension(windowWidth.toInt, windowHeight.toInt))
      setEditable(checkId())
    }

    val kinshipDegree = new JComboBox(Array("MOTHER", "FATHER", "LEGAL TUTOR"))
    kinshipDegree.setEnabled(checkId())

    val panel = new JPanel
    panel.setBorder(BorderFactory.createTitledBorder("Familiare"))
    panel.setLayout(new GridLayout(4, 1))
    panel.add(nameSurname, SwingConstants.TOP)
    panel.add(txtUserName, SwingConstants.CENTER)
    panel.add(kinshipDegree, SwingConstants.RIGHT)
    //TODO MANCA LA LISTA DI previousPathologies
    panel
  }

  private def createRemotePhysiologicPanel(titleGui: String): JPanel = {
    val info = new JLabel {
      title = "Informazioni"
    }
    val txtInfo: JTextField = new JTextField() {
      val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / 1000
      val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / 500
      setPreferredSize(new Dimension(windowWidth.toInt, windowHeight.toInt))
      setEditable(checkId())
    }
    val year = new JComboBox(Array("MOTHER", "FATHER", "LEGAL TUTOR"))
    year.setEnabled(checkId())
    /*val dayNumber = String.va1 to 10 toArray)
    val day = new JComboBox(dayNumber)
    day.setEnabled(checkId())
    val monthNumber = 1 to 10 toArray
    val month = new JComboBox(monthNumber)
    month.setEnabled(checkId())*/

    val date = new JPanel(new CardLayout())
    date.setBorder(BorderFactory.createTitledBorder("Data AAAA-MM-GG"))
    date.add(year)
    //date.add(month)
    //date.add(day)

    val panel = new JPanel
    date.setBorder(BorderFactory.createTitledBorder(titleGui))
    panel.add(info)
    panel.add(txtInfo)
    panel.add(date)

    panel
  }
}


object MedicalRecordsGUI {
  def apply()(medicalRecord: MedicalRecord, id: ID, actorRef: ActorRef): MedicalRecordsGUI = new MedicalRecordsGUI(medicalRecord, id, actorRef)
}