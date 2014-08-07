package com.ebusiello.twitter.fetcher

import java.util

import com.ebusiello.twitter.fetcher.postgres.conversion.SlickHelper._
import com.ebusiello.twitter.fetcher.postgres.generic.PostgresGeneric
import com.ebusiello.twitter.fetcher.postgres.schema.twitter.TwitterSchema.{Tweets, TweetsRow}
import twitter4j.{MediaEntity, Status}

import scala.slick.driver.PostgresDriver
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

class TwitterModel(implicit s: Session) extends PostgresGeneric[Tweets, TweetsRow] {

  override val tableReference: PostgresDriver.simple.TableQuery[Tweets] = TableQuery[Tweets]

  /**
   * Insert all tweets from an iterator
   *
   * @param iterator: util.Iterator[Status]
   */
  def insertAll(iterator: util.Iterator[Status]) = {
    while (iterator.hasNext()) {
      var status: Status = iterator.next()
      var entities = status.getMediaEntities
      entities.length match {
        case 0 =>
        case _ if entities.head.getType == "photo" & !exists(status) => insert(buildTweet(entities.head, status))
        case _ =>
      }
    }
  }

  def getLatestId: Long =
    tableReference.sortBy(_.twitterId.desc.nullsLast).take(1).firstOption.map(x => x.twitter_id).getOrElse(10000)

  def exists(status: Status): Boolean = {
    (for {
      row <- tableReference
      if row.tweet === status.getText || row.twitterId === status.getId || row.imageUrl === status.getMediaEntities.head.getMediaURL
    } yield row).firstOption.isDefined
  }

  def getTableSize: Int =
    tableReference.length.run

  def deleteOldTweets(size: Int): Int =
    tableReference.filter(_.twitterId in tableReference.sortBy(_.twitterId.asc).take(size).map(_.twitterId)).delete

  def buildTweet(media: MediaEntity, status: Status): TweetsRow =
    TweetsRow(
      twitter_id = status.getId,
      tweet = status.getText,
      imageUrl = media.getMediaURL
    )

  def getNTweets(n: Int, page: Int): List[TweetsRow] =
    tableReference.sortBy(_.twitterId.desc.nullsLast).drop(page * n).take(n).list()

}
