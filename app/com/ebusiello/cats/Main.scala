package com.ebusiello.cats

import java.io.File

import com.ebusiello.cats.controller.TwitterController
import play.api.{Play, Mode, DefaultApplication}

object  Main {

  def main(args: Array[String]) {
    val application = new DefaultApplication(new File(args(0)), this.getClass.getClassLoader, null, Mode.Prod)
    Play.start(application)
    val controller = new TwitterController()
    controller.execute()
    Play.stop()
  }

}
