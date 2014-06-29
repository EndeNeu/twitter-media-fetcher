package com.ebusiello.cats.controller

import java.util

import com.ebusiello.cats.connection.TwitterConnection
import com.ebusiello.cats.model.TwitterModel
import com.ebusiello.cats.postgres.checker.PostgresChecker
import com.ebusiello.cats.postgres.schema.twitter.TwitterSchema.TwitterCats
import com.ebusiello.cats.query.TwitterQuery
import play.api.Play.current
import play.api.db.slick._
import twitter4j._

class TwitterController {

  val model = new TwitterModel()

  val postgresChecker = new PostgresChecker()

  val connector = new TwitterConnection()

  def executeCron() = {
    DB.withSession {
      implicit s: Session => {
        val connection: Twitter = connector.getConnection()
        val lastId = model.getLatestId()
        val query = new TwitterQuery(connection)
        val results: util.List[Status] = query.getResults(lastId)
        postgresChecker.check(results.size())
        val iterator: util.Iterator[Status] = results.iterator()
        model.insertAll(iterator)
      }
    }
  }

  def getAnimals(page: Int): List[TwitterCats#TableElementType] = {
    DB.withSession {
      implicit s: Session => {
        model.getAnimalsN(99, page)
      }
    }
  }

}
