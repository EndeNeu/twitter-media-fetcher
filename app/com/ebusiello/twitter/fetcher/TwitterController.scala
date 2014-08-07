package com.ebusiello.twitter.fetcher

import java.util

import com.ebusiello.twitter.fetcher.postgres.checker.PostgresChecker
import com.ebusiello.twitter.fetcher.postgres.schema.twitter.TwitterSchema.Tweets
import play.api.Play.current
import play.api.db.slick._
import twitter4j._

class TwitterController extends TwitterConnection {

  def executeCron() = {
    DB.withSession {
      implicit s: Session => {
        val connection: Twitter = getConnection
        val model = new TwitterModel()
        val postgresChecker = new PostgresChecker(model)
        // get the latest id we have in the database, this will be used
        // to build the query to the twitter API and avoid refetching the same tweets.
        val lastId = model.getLatestId
        val query = new TwitterQuery(connection)
        val results: util.List[Status] = query.getResults(lastId)

        // this function was build to avoid going over the 10000 row limit on the free plan
        // for postgres on heroku.
        postgresChecker.check(results.size())

        val iterator: util.Iterator[Status] = results.iterator()
        model.insertAll(iterator)
      }
    }
  }

  def getAnimals(page: Int): List[Tweets#TableElementType] = {
    DB.withSession {
      implicit s: Session => {
        val model = new TwitterModel()
        model.getNTweets(99, page)
      }
    }
  }

}
