package com.ebusiello.cats.model

import java.util

import com.ebusiello.cats.postgres.conversion.SlickHelper._
import com.ebusiello.cats.postgres.generic.PostgresGeneric
import com.ebusiello.cats.postgres.schema.twitter.TwitterSchema
import com.ebusiello.cats.postgres.schema.twitter.TwitterSchema.{TwitterCats, TwitterCatsRow}
import twitter4j.{MediaEntity, Status}

import scala.slick.driver.PostgresDriver
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

class TwitterModel extends PostgresGeneric[TwitterSchema.TwitterCats, TwitterSchema.TwitterCatsRow] {

  override val tableReference: PostgresDriver.simple.TableQuery[TwitterCats] = TableQuery[TwitterCats]

  def insertAll(iterator: util.Iterator[Status])(implicit s: Session) = {
    while (iterator.hasNext()) {
      var status: Status = iterator.next()
      var entities = status.getMediaEntities
      entities.length match {
        case 0 =>
        case _ if entities.head.getType == "photo" & !exists(status) => insert(buildTwitterRow(entities.head, status))
        case _ =>
      }
    }
  }

  def getLatestId()(implicit s: Session): Long = {
    tableReference.sortBy(_.twitterId.desc.nullsLast).take(1).firstOption.map(x => x.twitter_id).getOrElse(479360717308555264L)
  }

  def exists(status: Status)(implicit s: Session): Boolean = {
    (for {
      row <- tableReference
      if row.tweet === status.getText || row.twitterId === status.getId || row.imageUrl === status.getMediaEntities.head.getMediaURL
    } yield row).firstOption.isDefined
  }

  def getTableSize()(implicit s: Session): Int = tableReference.length.run

  def deleteOldTweets(size: Int)(implicit s: Session): Int =
    tableReference.filter(_.twitterId in tableReference.sortBy(_.twitterId.asc).take(size).map(_.twitterId)).delete

  def buildTwitterRow(media: MediaEntity, status: Status): TwitterCatsRow = TwitterCatsRow(
    twitter_id = status.getId,
    tweet = status.getText,
    imageUrl = media.getMediaURL
  )

  def getAnimalsN(n: Int, page: Int)(implicit s: Session): List[TwitterCats#TableElementType] = {
    tableReference.sortBy(_.twitterId.desc.nullsLast).drop(page * n).take(n).list()
  }

}
