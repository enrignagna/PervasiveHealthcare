package gui

import javax.swing.JOptionPane

class DialogGUI {

  def showDialog(): Unit = {
    val dialogResult: Int = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire???", "Logout", JOptionPane.YES_NO_OPTION)
    if (dialogResult == JOptionPane.YES_OPTION) {
      //val login = new LoginGUI()
      //login.visible = true
    }
  }

  def alertDialog(message: String): Unit = {
    JOptionPane.showMessageDialog(null, message)
  }

  def showErrorDialog(message: String): Unit = {
    JOptionPane.showConfirmDialog(null, message, "Errore", JOptionPane.ERROR_MESSAGE)
  }
}
