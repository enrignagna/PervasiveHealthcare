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

import java.awt.{GridLayout, Toolkit}

import akka.actor.ActorRef
import client.surgeon.SurgeonMessage.UpdateMedicalRecordMessage
import domainmodel.medicalrecords.clinicaldiary.Treatment
import domainmodel.medicalrecords.{AnesthetistReport, InstrumentalistReport, MedicalRecord, SurgeonReport}
import domainmodel._
import domainmodel.medicalrecords.initialanalysis.InitialAnalysis
import javax.swing._

import scala.swing.BorderPanel.Position._
import scala.swing.ListView._
import scala.swing.TabbedPane._
import scala.swing.event._
import scala.swing.{BorderPanel, BoxPanel, Button, Dimension, Label, ListView, MainFrame, Orientation, RadioButton, Slider, SplitPane, TabbedPane, TextArea, _}

class MedicalRecordsGUI(medicalRecord: MedicalRecord, id: ID, actor: ActorRef) extends MainFrame {

  val utility: UtilsGUI = new UtilsGUI
  val previousPathologies = new JList[JPanel]()
  val heightRatio = 1.5
  val widthRatio = 2
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  val componentDimension: Double = windowWidth/1.5
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = false

  override def closeOperation(): Unit = {
    this.visible = false
  }
  private val patientID = new Label {
    text = "ID paziente"
    maximumSize = new Dimension(componentDimension.toInt, 25)
    minimumSize = new Dimension(componentDimension.toInt, 25)
    preferredSize = new Dimension(componentDimension.toInt, 25)
    horizontalTextPosition = Alignment.Left
  }
  private val medicalRecordIDtitle = new Label {
    text = "ID cartella clinica"
  }
  private val updateButton = new Button {
    text = "Aggiorna"
    enabled = utility.checkId(id)
  }
  private val patientIDtxt = new TextArea {
    editable = false
    text = id.value
    maximumSize = new Dimension(componentDimension.toInt, 25)
    minimumSize = new Dimension(componentDimension.toInt, 25)
    preferredSize = new Dimension(componentDimension.toInt, 25)
  }
  private val medicalRecordsIDtxt = new TextArea {
    maximumSize = new Dimension(componentDimension.toInt, 25)
    minimumSize = new Dimension(componentDimension.toInt, 25)
    preferredSize = new Dimension(componentDimension.toInt, 25)
    resizable = false
    text = medicalRecord.medicalRecordID.value
  }
  val isClosedButton: RadioButton = new RadioButton() {
    maximumSize = new Dimension(componentDimension.toInt, 25)
    minimumSize = new Dimension(componentDimension.toInt, 25)
    preferredSize = new Dimension(componentDimension.toInt, 25)
    resizable = false
    name = "Cartella clinica:"
    text = "Aperta"
    text = "Chiusa"
    visible = utility.checkId(id)
  }

  val initialAnalysis: JTextArea = createTextArea(medicalRecord.initialAnalysis)
  val clinicalDiary: JTextArea = createTextArea(medicalRecord.clinicalDiary)
  val operatingReports: JTextArea = createTextArea(medicalRecord.operatingReports)
  /*
 * The root component in this frame is a panel with a border layout.
 */
  contents = new BorderPanel {

    var reactLive = false
/*
    var familiarPanel: JList[JPanel] = new JList[JPanel]
    medicalRecord.initialAnalysis.get.anamensis.get.familiars.familiars.foreach { f => createFamiliarPanel(f, familiarPanel) }
    var remotePanel: JList[JPanel] = new JList[JPanel]
    medicalRecord.initialAnalysis.get.anamensis.get.remotes.history.foreach { r => createRemotePanel(r, remotePanel) }
    val physiologicPanel: JPanel = createPhysiologicPanel(medicalRecord.initialAnalysis.get.anamensis.get.physiologic)

    val anamnesiPanel: JPanel = createAnamnesiPanel(familiarPanel, remotePanel, physiologicPanel)

    val physicalExaminationPanel: JPanel = createPhysicalExaminationPanel()

    val stateEvaluationPanel: JPanel = createStateEvaluationPanel()

    initialAnalysis.add(anamnesiPanel)
    initialAnalysis.add(physicalExaminationPanel)
    initialAnalysis.add(stateEvaluationPanel)

    val healthEvolutionPanel: JPanel = createHealthEvolutionPanel()
    var diagnosticTreatments: JList[JPanel] = new JList[JPanel]
    medicalRecord.clinicalDiary.get.diagnosticTreatments.get.diagnosticTreatments.foreach { dt => createTreatmentPanel(dt.treatment, diagnosticTreatments) }
    var therapeuticTreatments: JList[JPanel] = new JList[JPanel]
    medicalRecord.clinicalDiary.get.therapeuticTreatments.get.therapeuticTreatments.foreach { tt => createTreatmentPanel(tt.treatment, therapeuticTreatments) }
    var rehabilitationTreatments: JList[JPanel] = new JList[JPanel]
    medicalRecord.clinicalDiary.get.rehabilitationTreatments.get.rehabilitationTreatments.foreach { rt => createTreatmentPanel(rt.treatment, diagnosticTreatments) }

    clinicalDiary.add(healthEvolutionPanel)
    clinicalDiary.add(diagnosticTreatments)
    clinicalDiary.add(therapeuticTreatments)
    clinicalDiary.add(rehabilitationTreatments)

    val medical: JPanel = createHealthEvolutionPanel()
    var surgeonReports: JList[JPanel] = new JList[JPanel]
    medicalRecord.operatingReports.get.medical.surgeons.foreach { sr => createSurgeonReport(sr, surgeonReports) }
    var anesthetistReports: JList[JPanel] = new JList[JPanel]
    medicalRecord.operatingReports.get.medical.anesthetists.foreach { ar => createAnesthetistReport(ar, anesthetistReports) }
    var instrumentalistReports: JList[JPanel] = new JList[JPanel]
    medicalRecord.operatingReports.get.medical.instrumentalists.foreach { ir => createInstrumentalistReport(ir, instrumentalistReports) }
    var operatingReportsDate: JPanel = utility.createDatePanel(id, medicalRecord.operatingReports.get.datetime.toLocalDate)
    operatingReportsDate.setBorder(BorderFactory.createTitledBorder("Data"))
    var interventionTypePanel = new JPanel
    utility.createLabelTextField("Tipologia di Intervento", medicalRecord.operatingReports.get.interventionType.value, interventionTypePanel, id)

    medical.add(surgeonReports)
    medical.add(anesthetistReports)
    medical.add(instrumentalistReports)

    operatingReports.add(medical)
    operatingReports.add(operatingReportsDate)
    operatingReports.add(interventionTypePanel)
*/
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
      pages += new Page("Analisi iniziale", new Panel {
        initialAnalysis
      })
      pages += new Page("Diario clinico", new Panel {
        clinicalDiary
      })
      pages += new Page("Report delle operazioni", new Panel {
        operatingReports
      })
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
    layout(center) = West

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


  private def createTextArea(value: Option[_]): JTextArea = new JTextArea(5, 25) {
    /*setText(value.get.toString)
    setEditable(utility.checkId(id))*/
  }

  private def recreateMedicalRecord(): Unit = {
    val closed = if (isClosedButton.text.equals("Chiusa")) true else false
   /* val recreateInitialAnalysis = InitialAnalysis(Anamnesis(initialAnalysis.getComponent(1).))
    val newMedicalRecord = MedicalRecord(medicalRecord.doctorID, medicalRecord.patientID, medicalRecord.medicalRecordID, closed,
      INITIALANALYSIS, CLINICALDIARY, medicalRecord.diagnosticServicesRequests, medicalRecord.graphic, medicalRecord.painReliefHistory,
      medicalRecord.singleSheetTherapyHistory, medicalRecord.adviceRequest, medicalRecord.reports, OPERATINGREPORTS,
      medicalRecord.nursingDocumentation, medicalRecord.anesthesiologyRecord, medicalRecord.medicalSurgicalDevices, medicalRecord.dischargeLetter)
*/
   // actor ! UpdateMedicalRecordMessage(newMedicalRecord)
  }

  private def createFamiliarPanel(familiar: Familiar, famPanel: JList[JPanel]): Unit = {
    val panel = new JPanel

    panel.setBorder(BorderFactory.createTitledBorder("Familiare"))
    panel.setLayout(new GridLayout(4, 1))
    utility.createLabelTextField("Nome Cognome", familiar.name, panel, id)

    val kinshipDegree = new JComboBox(Array("MOTHER", "FATHER", "LEGAL TUTOR"))
    kinshipDegree.setEnabled(utility.checkId(id))
    kinshipDegree.setSelectedIndex(familiar.kinshipDegree.id)


    panel.add(kinshipDegree, SwingConstants.RIGHT)
    familiar.previousPathologies.pathologies.foreach { pp => createPathologyPanel(pp) }
    panel.add(previousPathologies, SwingConstants.BOTTOM)

    famPanel.add(panel)
  }

  private def createRemotePanel(remote: Remote, remotePanel: JList[JPanel]): Unit = {
    val panel = new JPanel
    utility.createLabelTextField("Informazioni", remote.info, panel, id)

    val date = utility.createDatePanel(id, remote.date)
    date.setBorder(BorderFactory.createTitledBorder("Remota"))
    panel.add(date)

    remotePanel.add(panel)
  }

  private def createPhysiologicPanel(physiologic: Physiologic): JPanel = {
    val panel = new JPanel
    utility.createLabelTextField("Informazioni", physiologic.info, panel, id)

    val date = utility.createDatePanel(id, physiologic.date)

    date.setBorder(BorderFactory.createTitledBorder("Fisiologica"))
    panel.add(date)

    panel
  }

  private def createPathologyPanel(pathology: Pathology): Unit = {
    val pathologyPanel = new JPanel()
    pathologyPanel.setBorder(BorderFactory.createTitledBorder("Patologie"))

    utility.createLabelTextField("Nome patologia", pathology.pathologyName.toString, pathologyPanel, id)

    val pathologyDate = utility.createDatePanel(id, pathology.detectionDate.value)
    val pathologySeverity = new JComboBox(Array("ONE - Low desease", "TWO - Changes life quality", "THREE - Causes disability", "FOUR - Threatens life"))
    pathologySeverity.setEnabled(utility.checkId(id))
    pathologySeverity.setSelectedIndex(pathology.pathologySeverity.severity.id)

    pathologyPanel.setLayout(new GridLayout(4, 1))
    pathologyPanel.add(pathologyDate, SwingConstants.RIGHT)
    pathologyPanel.add(pathologySeverity, SwingConstants.LEFT)
    previousPathologies.add(pathologyPanel)
  }

  private def createAnamnesiPanel(familiar: JList[JPanel], remote: JList[JPanel], physiologic: JPanel): JPanel = {
    val panel = new JPanel
    panel.setBorder(BorderFactory.createTitledBorder("Anamnesi"))
    panel.setLayout(new GridLayout(1, 3))
    panel.add(familiar, SwingConstants.LEFT)
    panel.add(remote, SwingConstants.CENTER)
    panel.add(physiologic, SwingConstants.RIGHT)

    panel
  }

  private def createPhysicalExaminationPanel(): JPanel = {
    val panel: JPanel = new JPanel()
    panel.setBorder(BorderFactory.createTitledBorder("Esaminazione fisica"))

    utility.createLabelTextField("Motivazione ricovero ospedaliero", medicalRecord.initialAnalysis.get.physicalExamination.hospitalizationMotivation.value, panel, id)
    utility.createLabelTextField("Investigazione", medicalRecord.initialAnalysis.get.physicalExamination.systemsInvestigation.value, panel, id)

    panel
  }

  private def createStateEvaluationPanel(): JPanel = {
    val panel = new JPanel {
      title = "Valutazione dello stato"
    }

    utility.createLabelTextField("Psicologica", medicalRecord.initialAnalysis.get.stateEvaluation.psychological.value, panel, id)
    utility.createLabelTextField("Nutrizionale", medicalRecord.initialAnalysis.get.stateEvaluation.nutritional.value, panel, id)
    utility.createLabelTextField("Educativa", medicalRecord.initialAnalysis.get.stateEvaluation.educational.value, panel, id)
    utility.createLabelTextField("Sociale", medicalRecord.initialAnalysis.get.stateEvaluation.social.value, panel, id)

    panel
  }

  private def createHealthEvolutionPanel(): JPanel = {

    if (medicalRecord.clinicalDiary.get.healthEvolution.isDefined) {
      val panel = new JPanel
      val healthEvolution = medicalRecord.clinicalDiary.get.healthEvolution.get
      utility.createLabelTextField("Informazioni", healthEvolution.info.value, panel, id)

      val date = utility.createDatePanel(id, healthEvolution.dateTime.toLocalDate)

      date.setBorder(BorderFactory.createTitledBorder("Evoluzione sanitaria"))
      panel.add(date)

      panel
    } else null
  }


  private def createTreatmentPanel(treatment: Treatment, treatmentsPanel: JList[JPanel]): Unit = {
    val panel = new JPanel
    utility.createLabelTextField("Descrizione", treatment.description.value, panel, id)

    val date = utility.createDatePanel(id, treatment.date)
    utility.createLabelTextField("Medico", treatment.doctorID.value, panel, id)

    date.setBorder(BorderFactory.createTitledBorder("Trattamento"))
    panel.add(date)

    treatmentsPanel.add(panel)
  }

  private def createSurgeonReport(report: SurgeonReport, panel: JList[JPanel]): Unit = {
    val subPanel = new JPanel()
    utility.createLabelTextField("Note", report.note.getOrElse("").toString, subPanel, id)
    val surgeon: JPanel = new JPanel()
    utility.createLabelTextField("Informazioni generali", report.surgeon.name.concat("").concat(report.surgeon.surname), surgeon, id)
    utility.createLabelTextField("Email", report.surgeon.email.toString, surgeon, id)
    utility.createLabelTextField("Laurea e specializzazione", report.surgeon.medicalDegreeGrade.concat(report.surgeon.specialization.id.toString), surgeon, id)

    subPanel.add(surgeon)
    panel.add(subPanel)
  }

  private def createInstrumentalistReport(report: InstrumentalistReport, panel: JList[JPanel]): Unit = {
    val subPanel = new JPanel()
    utility.createLabelTextField("Note", report.note.getOrElse("").toString, subPanel, id)
    val instrumentalist: JPanel = new JPanel()
    utility.createLabelTextField("Informazioni generali", report.instrumentalist.name.concat("").concat(report.instrumentalist.surname), instrumentalist, id)
    utility.createLabelTextField("Email", report.instrumentalist.email.toString, instrumentalist, id)
    utility.createLabelTextField("Laurea", report.instrumentalist.nursingDegreeGrade, instrumentalist, id)

    subPanel.add(instrumentalist)
    panel.add(subPanel)
  }

  private def createAnesthetistReport(report: AnesthetistReport, panel: JList[JPanel]): Unit = {
    val subPanel = new JPanel()
    utility.createLabelTextField("Note", report.note.getOrElse("").toString, subPanel, id)
    val anesthetist: JPanel = new JPanel()
    utility.createLabelTextField("Informazioni generali", report.anesthetist.name.concat("").concat(report.anesthetist.surname), anesthetist, id)
    utility.createLabelTextField("Email", report.anesthetist.email.toString, anesthetist, id)
    utility.createLabelTextField("Laurea", report.anesthetist.medicalDegreeGrade, anesthetist, id)

    subPanel.add(anesthetist)
    panel.add(subPanel)
  }


}

/*
object MedicalRecordsGUI {
  def apply(medicalRecord: MedicalRecord, id: ID, actorRef: ActorRef): MedicalRecordsGUI = new MedicalRecordsGUI(medicalRecord, id, actorRef)
}*/