package com.ebusiello.twitter.fetcher

import java.util

import play.api.Play
import twitter4j._

class TwitterQuery(val connection: Twitter) {

  val query: String = Play.current.configuration.getString("twitter.query").get

  def getQuery(lastId: Long) = new Query(query).count(100).sinceId(lastId)

  def getResults(lastId: Long): util.List[Status] = connection.search(getQuery(lastId)).getTweets

}