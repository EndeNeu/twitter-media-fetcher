package com.ebusiello.cats.job

import java.io.File

import com.ebusiello.cats.controller.TwitterController
import play.api.{DefaultApplication, Mode, Play}

object  TwitterJob {

  def main(args: Array[String]) {
    val application = new DefaultApplication(new File(args(0)), this.getClass.getClassLoader, null, Mode.Prod)
    Play.start(application)
    val controller = new TwitterController()
    controller.executeCron()
    Play.stop()
  }

}