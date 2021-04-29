package gui

import akka.actor.ActorRef
import domainmodel.ID
import domainmodel.generalpractitionerinfo.GeneralPractitionerInfo

import scala.swing.MainFrame

class GeneralPractitionerInfoGUI(generalPractitionerInfo: GeneralPractitionerInfo, id: ID, actor: ActorRef) extends MainFrame {
}
