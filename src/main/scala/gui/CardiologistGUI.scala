package gui

import java.awt.Toolkit

import akka.actor.{ActorRef, ActorSystem, Props}
import client.cardiologist.CardiologistActor
import client.cardiologist.Message.AllCardiologyVisitsMessage
import client.logout.LogoutActor
import domainmodel._
import domainmodel.generalpractitionerinfo.VisitDate

import scala.swing.BorderPanel.Position.Center
import scala.swing.TabbedPane.Page
import scala.swing.event.{ListSelectionChanged, MouseClicked, SelectionChanged, ValueChanged}
import scala.swing.{BorderPanel, Dimension, ListView, MainFrame, Orientation, ScrollPane, Slider, SplitPane, TabbedPane, TextArea}

class CardiologistGUI(cardiologistID: String, token: String, actorSystem: ActorSystem) extends MainFrame {

  val heightRatio = 1.5
  val widthRatio = 2
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = true
  visible = true

  title = "Scala Swing Cardiologist Demo"


  val id: DoctorID = DoctorID(cardiologistID)
  val cardiologistActor: ActorRef = actorSystem.actorOf(Props(new CardiologistActor(id, token, this)))
  val logoutActor: ActorRef = actorSystem.actorOf(Props(new LogoutActor(token)))

  val visit: CardiologyVisit = CardiologyVisit(
    PatientID("000006"),
    id,
    CardiologyVisitID("CV000000"),
    ChestPainType.ASYMPTOMATIC,
    RestingBloodPressure(146),
    Cholesterol(250),
    FastingBloodSugar(true),
    RestingElectrocardiographic.NORMAL,
    MaxHeartRate(200),
    isAnginaInducted = false,
    OldPeakST(2.3),
    SlopeST.UP_SLOPING,
    NumberVesselColoured(0),
    Thal.FIXED_DEFECT,
    VisitDate()
  )

  cardiologistActor ! AllCardiologyVisitsMessage()

  val dialogGui = new DialogGUI()
  var listCardiologyVisits: List[CardiologyVisit] = List()
  val cardiologyVisits: ListView[String] = new ListView[String]{
    listenTo(mouse.clicks)
    reactions+= {
      case _: MouseClicked =>
        val index = this.peer.getSelectedIndex - 1
        if(index >= 0){
         //TODO open gui
        }

    }
  }
  val cardiologyVisitLabel = s"Cardiology Visit ID          Patient ID"
  val labelCardiologyVisits = Seq(cardiologyVisitLabel)




  cardiologyVisits.listData = labelCardiologyVisits

  /*
  * The root component in this frame is a panel with a border layout.
  */
  contents = new BorderPanel {

    var reactLive = false

    val tabs: TabbedPane = new TabbedPane {
      val logout: TextArea = new TextArea(5, 25) {
        editable = false
      }

      pages += new Page("Visite cardiologiche", cardiologyVisits)
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

    def negativeLogout(): Unit = {

    }

    /*
     * Establish connection between the tab pane, slider, and list view.
     */
    listenTo(slider)
    listenTo(tabs.selection)
    listenTo(list.selection)
    listenTo(cardiologyVisits.selection)
    reactions += {
      case ValueChanged(`slider`) =>
        if (!slider.adjusting || reactLive) tabs.selection.index = slider.value
        slider.value match {
          case 0 => cardiologistActor ! AllCardiologyVisitsMessage()
          case 1 => dialogGui.logoutDialog(logoutActor,
            () => {
              close()
            },
            () => {
            slider.value = 0
            list.selectIndices(0)
          })
        }
      case SelectionChanged(`tabs`) =>
        slider.value = tabs.selection.index
        list.selectIndices(tabs.selection.index)
      case SelectionChanged(`list`) =>
        if (list.selection.items.length == 1)
          tabs.selection.page = list.selection.items.head
    }
  }



  def refreshCardiologyVisits(cardiologyVisitsJson: List[CardiologyVisit]): Unit = {
    listCardiologyVisits = cardiologyVisitsJson
    cardiologyVisits.listData = labelCardiologyVisits ++ listCardiologyVisits.map(v => s"${v.cardiologyVisitID.value}" +
      "                        " +
      s"${v.patientID.value}"
    )
    cardiologyVisits.peer.repaint()
  }


}
