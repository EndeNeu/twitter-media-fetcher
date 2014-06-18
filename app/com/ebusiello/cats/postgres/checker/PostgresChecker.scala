package com.ebusiello.cats.postgres.checker

import com.ebusiello.cats.model.TwitterModel

import scala.slick.driver.PostgresDriver.simple._

class PostgresChecker {

  val LIMIT = 10000

  val model = new TwitterModel()

  def check(importSize: Int)(implicit s: Session) = if(importSize + model.getTableSize > LIMIT) model.deleteOldTweets(importSize)

}
