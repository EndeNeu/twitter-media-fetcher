package com.ebusiello.twitter.fetcher

import java.io.File

import play.api.{DefaultApplication, Mode, Play}

object  TwitterJob {

  /**
   * Cron job.
   *
   * @param args
   */
  def main(args: Array[String]) {
    val application = new DefaultApplication(new File(args(0)), this.getClass.getClassLoader, null, Mode.Prod)
    Play.start(application)
    val controller = new TwitterController()
    controller.executeCron()
    Play.stop()
  }

}