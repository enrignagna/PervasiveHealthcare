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

import java.awt.Toolkit

import akka.actor.{ActorRef, ActorSystem, Props}
import client.login.LoginActor
import client.login.Message.LoginMessage
import javax.swing.border.Border
import javax.swing.plaf.basic.BasicBorders.MarginBorder

import scala.swing.event._
import scala.swing.{BoxPanel, Button, Dimension, Label, MainFrame, Orientation, _}

class LoginGUI(actorSystem: ActorSystem) extends MainFrame {

  title = "Login Demo"
  visible = true
  val heightRatio = 5
  val widthRatio = 5
  val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / heightRatio
  val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / widthRatio
  val componentDimension: Double = windowWidth/1.5
  preferredSize = new Dimension(windowWidth.toInt, windowHeight.toInt)
  resizable = false
  val dialogGUI = new DialogGUI()
  var userID: String = _

  private val loginLabel = new Label {
    text = "Login"
  }
  private val userIDLabel = new Label() {
    text = "User ID"
    horizontalTextPosition = Alignment.Left

  }
  private val passwordLabel = new Label {
    text = "Password"
  }
  private val loginButton = new Button {
    text = "Login"
  }

  private val txtUserID: TextField = new TextField() {
    maximumSize = new Dimension(componentDimension.toInt, 25)
    minimumSize = new Dimension(componentDimension.toInt, 25)
    preferredSize = new Dimension(componentDimension.toInt, 25)
    resizable = false
  }
  private val txtPwd = new PasswordField() {
    maximumSize = new Dimension(componentDimension.toInt, 25)
    minimumSize = new Dimension(componentDimension.toInt, 25)
    preferredSize = new Dimension(componentDimension.toInt, 25)
    resizable = false
  }

  contents = new GridPanel(6, 1) {
    contents += loginLabel
    contents += userIDLabel
    contents += txtUserID
    contents += passwordLabel
    contents += txtPwd
    contents += loginButton
  }

  listenTo(loginButton)
  reactions += {
    case ButtonClicked(_) =>
      val passwordText = String.valueOf(txtPwd.password)
      if (txtUserID.text.nonEmpty && passwordText.nonEmpty) {
        userID = txtUserID.text
        checkLogin(userID, passwordText)
      } else dialogGUI.showErrorDialog("Credenziali errate!!")
  }

  val loginActor: ActorRef = actorSystem.actorOf(Props(
    new LoginActor(this)))


  private def checkLogin(userID: String, password: String): Unit = {
    loginActor ! LoginMessage(userID, password)
  }

  def responseLogin(responseRole: Option[String], responseToken: Option[String]): Unit = {

    val role = responseRole.getOrElse("")
    val token = responseToken.getOrElse("")

    role match {
      case "0" => new PatientGUI(userID, token, actorSystem);
      case "1" => new GeneralPractitionerGUI(userID, token, actorSystem)
      case "2" => new SurgeonGUI(userID, token, actorSystem)
      case "8" => new CardiologistGUI(userID)
      case _ => dialogGUI.showErrorDialog("Errore!")
    }
    close()
  }

  def showAlert(message: String): Unit = dialogGUI.alertDialog(message)
}