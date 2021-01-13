package UNO.aview.gui

import UNO.controller.controllerComponent.controllerBaseImp.{controller, updateStates}
import UNO.controller.controllerComponent.controllerInterface

import java.awt.Image
import javax.swing.ImageIcon
import scala.swing.BorderPanel.Position
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event.{ButtonClicked, Key}

class SwingGui(controller: controllerInterface) extends Frame {

  listenTo(controller)
  title = " UNO Game"
  peer.setPreferredSize(new Dimension(1200, 900))
  peer.setResizable(true)
  peer.setBackground(java.awt.Color.DARK_GRAY)

  def gamePanel = new GridPanel(5, 1) {

    contents += new GridPanel(1, controller.playerList(1).playerCards.size) {
      border = LineBorder(java.awt.Color.DARK_GRAY, 50)
      background = java.awt.Color.DARK_GRAY

      for (i <- 1 to controller.playerList(1).playerCards.length) {

        val cardPanel = new CardPanel(1, i - 1, controller)
        contents += cardPanel.card
      }

    }

    contents += new GridPanel(1, 4) {
      border = LineBorder(java.awt.Color.DARK_GRAY, 50)
      background = java.awt.Color.DARK_GRAY
      val cardStack = new CardPanel(4, 0, controller)
      contents += cardStack.card
      val playStack = new CardPanel(3, 0, controller)
      contents += playStack.card
      val unoCall = new Button("")
      unoCall.background = java.awt.Color.DARK_GRAY
      unoCall.icon = scaledImageIcon("src\\main\\Pics\\UNO-Button.png", 110, 100)
      contents += unoCall
      listenTo(unoCall)
      reactions += {
        case ButtonClicked(`unoCall`) =>
          controller.unoCall = true
          unoCall.background = java.awt.Color.RED
      }
    }

    contents += new GridPanel(1, controller.playerList(0).playerCards.size) {
      border = LineBorder(java.awt.Color.DARK_GRAY, 50)
      background = java.awt.Color.DARK_GRAY

      for (i <- 1 to controller.playerList(0).playerCards.length) {

        val cardPanel = new CardPanel(0, i - 1, controller)
        contents += cardPanel.card
      }
    }

    contents += new GridPanel(1, 4) {
      border = LineBorder(java.awt.Color.DARK_GRAY, 50)
      background = java.awt.Color.DARK_GRAY

      val buttonGroup = new ButtonGroup
      val red = new RadioButton("")
      red.background = java.awt.Color.DARK_GRAY
      red.icon = scaledImageIcon("src\\main\\Pics\\Red_Radio.png", 110, 100)
      val blue = new RadioButton("")
      blue.background = java.awt.Color.DARK_GRAY
      blue.icon = scaledImageIcon("src\\main\\Pics\\Blue_Radio.png", 110, 100)
      val green = new RadioButton("")
      green.background = java.awt.Color.DARK_GRAY
      green.icon = scaledImageIcon("src\\main\\Pics\\Green_Radio.png", 110, 100)
      val yellow = new RadioButton("")
      yellow.background = java.awt.Color.DARK_GRAY
      yellow.icon = scaledImageIcon("src\\main\\Pics\\Yellow_Radio.png", 110, 100)
      buttonGroup.buttons ++= List(red, blue, green, yellow)
      buttonGroup.select(red)
      contents ++= List(red, blue, green, yellow)
      listenTo(red, green, blue, green, yellow)
      reactions += {
        case ButtonClicked(`yellow`) => {
          controller.colorSet = "yellow"
          yellow.background = java.awt.Color.YELLOW
        }
        case ButtonClicked(`red`) => {
          controller.colorSet = "red"
          red.background = java.awt.Color.RED
        }
        case ButtonClicked(`blue`) => {
          controller.colorSet = "blue"
          blue.background = java.awt.Color.BLUE
        }
        case ButtonClicked(`green`) => {
          controller.colorSet = "green"
          green.background = java.awt.Color.GREEN
        }
      }
    }
    contents += new GridPanel(2, 1) {
      border = LineBorder(java.awt.Color.DARK_GRAY, 50)
      background = java.awt.Color.DARK_GRAY
      val label = new Label {
        text = "Player: " + controller.playerList(0).name
        font = new Font("Arial Black", java.awt.Font.BOLD, 20)
        foreground = java.awt.Color.WHITE
      }

      contents += label
    }
    menuBar = new MenuBar {
      contents += new Menu("File") {
        mnemonic = Key.F
        contents += new MenuItem(Action("New") {
          controller.newGame()
        }) //TODO
        contents += new MenuItem(Action("Save") {
          controller.save
        })
        contents += new MenuItem(Action("Load") {
          controller.load
        })
        contents += new MenuItem(Action("Quit") {
          System.exit(0)
        })
      }
      contents += new Menu("Edit") {
        mnemonic = Key.E
        contents += new MenuItem(Action("Undo") {
          controller.undoGet
        })
        contents += new MenuItem(Action("Redo") {
          controller.redoGet
        })
      }
    }
  }

  contents = new BorderPanel {
    add(gamePanel, Position.Center)
  }

  def endGamePanel = new GridPanel(2, 1) {

    contents += new GridPanel(1, 1) {
      border = LineBorder(java.awt.Color.DARK_GRAY, 50)
      background = java.awt.Color.DARK_GRAY

      val winLabel = new Label("YOU ARE WIN!")
      contents += winLabel
    }

    contents += new GridPanel(1, 3) {
      border = LineBorder(java.awt.Color.DARK_GRAY, 50)
      background = java.awt.Color.DARK_GRAY

      val againLabel = new Label("Play Again?")
      val yesButton = new Button("YES")
      yesButton.background = java.awt.Color.DARK_GRAY
      val noButton = new Button("NO")
      noButton.background = java.awt.Color.DARK_GRAY
      contents += againLabel
      contents += yesButton
      contents += noButton
      listenTo(yesButton, noButton)
      reactions += {
        case ButtonClicked(`yesButton`) => {
          controller.newGame()
        }
        case ButtonClicked(`noButton`) => {
          System.exit(0)
        }
      }
    }
    menuBar = new MenuBar {
      contents += new Menu("File") {
        mnemonic = Key.F
        contents += new MenuItem(Action("New") {
          controller.newGame()
        }) //TODO
        contents += new MenuItem(Action("Save") {
          controller.save
        })
        contents += new MenuItem(Action("Load") {
          controller.load
        })
        contents += new MenuItem(Action("Quit") {
          System.exit(0)
        })
      }
      contents += new Menu("Edit") {
        mnemonic = Key.E
        contents += new MenuItem(Action("Undo") {
          controller.undoGet
        })
        contents += new MenuItem(Action("Redo") {
          controller.redoGet
        })
      }
    }
  }

  def redraw = {
    contents = new BorderPanel {
      add(gamePanel, BorderPanel.Position.Center)
    }
  }

  def redraw2 = {
    contents = new BorderPanel {
      add(endGamePanel, BorderPanel.Position.Center)
    }
  }

  reactions += {
    case event: updateStates => redraw

  }

  visible = true
  redraw

  def scaledImageIcon(path: String, width: Int, height: Int): ImageIcon = {
    val orig = new ImageIcon(path)
    val scaledImage = orig.getImage.getScaledInstance(width, height, Image.SCALE_REPLICATE)
    new ImageIcon(scaledImage)
  }
}
