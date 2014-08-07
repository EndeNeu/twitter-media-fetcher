package com.ebusiello.twitter.fetcher.postgres.checker

import com.ebusiello.twitter.fetcher.TwitterModel

import scala.slick.driver.PostgresDriver.simple._

class PostgresChecker(model: TwitterModel)(implicit s: Session) {

  // max number of tweets to keep in db
  val LIMIT = 8000

  def check(importSize: Int) = if(importSize + model.getTableSize > LIMIT) model.deleteOldTweets(importSize)

}