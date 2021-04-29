package gui

import java.awt.{CardLayout, Toolkit}
import java.time.LocalDate

import domainmodel.{ID, PatientID}
import javax.swing.{BorderFactory, JComboBox, JLabel, JPanel, JTextField}

import scala.swing.Dimension

class UtilsGUI {

  def createDatePanel(id:ID, localDate: LocalDate): JPanel = {
    val yearNumber = 1 to 12 toArray
    val year= new JComboBox[Int]()
    yearNumber.foreach{x => year.addItem(x)}
    year.setSelectedItem(localDate.getYear)
    year.setEnabled(checkId(id))
    val dayNumber = 1 to 10 toArray
    val day = new JComboBox[Int]()
    dayNumber.foreach{ x => day.addItem(x)}
    day.setSelectedItem(localDate.getDayOfYear)
    day.setEnabled(checkId(id))
    val monthNumber = 1 to 31 toArray
    val month = new JComboBox[Int]()
    monthNumber.foreach{ x => month.addItem(x)}
    month.setSelectedItem(localDate.getMonth)
    month.setEnabled(checkId(id))

    val date = new JPanel(new CardLayout())
    date.setBorder(BorderFactory.createTitledBorder("Data AAAA-MM-GG"))
    date.add(year)
    date.add(month)
    date.add(day)

    date
  }


  def checkId(id: ID): Boolean = {
    if (id.equals(PatientID(id.value))) {
      false
    } else true
  }

  def createLabelTextField(labelTitle: String, txtText: String, panel: JPanel, id:ID): Unit = {
    val note = new JLabel {
      setText(labelTitle)
    }
    val txtNote: JTextField = new JTextField() {
      setText(txtText)
      val windowHeight: Double = Toolkit.getDefaultToolkit.getScreenSize.height / 1000
      val windowWidth: Double = Toolkit.getDefaultToolkit.getScreenSize.width / 500
      setPreferredSize(new Dimension(windowWidth.toInt, windowHeight.toInt))
      setEditable(checkId(id))
    }

    panel.add(note)
    panel.add(txtNote)
  }

}
