package com.ebusiello.cats.query

import java.util

import twitter4j._

class TwitterQuery(val connection: Twitter) {

  def getQuery(lastId: Long) = new Query("#cat+OR+#cats").count(100).sinceId(lastId)

  def getResults(lastId: Long): util.List[Status] = connection.search(getQuery(lastId)).getTweets

}
