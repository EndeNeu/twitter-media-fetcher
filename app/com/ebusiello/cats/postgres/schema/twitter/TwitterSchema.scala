package com.ebusiello.cats.postgres.schema.twitter

import com.ebusiello.cats.postgres.schema.generic.RichTable

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.{GetResult => GR}
import scala.slick.lifted.TableQuery

object TwitterSchema {

  /** Entity class storing rows of table TwitterCats
    * @param id Database column id AutoInc, PrimaryKey
    * @param tweet Database column tweet
    * @param imageUrl Database column image_url  */
  case class TwitterCatsRow(id: Long = 0, twitter_id: Long, tweet: Option[String], imageUrl: String)

  /** GetResult implicit for fetching TwitterCatsRow objects using plain SQL queries */
  implicit def GetResultTwitterCatsRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[String]): GR[TwitterCatsRow] = GR{
    prs => import prs._
      TwitterCatsRow.tupled((<<[Long], <<[Long], <<?[String], <<[String]))
  }

  /** Table description of table twitter_cats. Objects of this class serve as prototypes for rows in queries. */
  class TwitterCats(tag: Tag) extends RichTable[TwitterCatsRow](tag, "twitter_cats") {
    def * = (id, twitterId, tweet, imageUrl) <> (TwitterCatsRow.tupled, TwitterCatsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, twitterId.?, tweet, imageUrl.?).shaped.<>({r=>import r._; _1.map(_=> TwitterCatsRow.tupled((_1.get, _2.get, _3, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id AutoInc, PrimaryKey */
    override val id: Column[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column twitter_id  */
    val twitterId: Column[Long] = column[Long]("twitter_id")
    /** Database column tweet  */
    val tweet: Column[Option[String]] = column[Option[String]]("tweet")
    /** Database column image_url  */
    val imageUrl: Column[String] = column[String]("image_url")
  }

  /** Collection-like TableQuery object for table TwitterCats */
  lazy val TwitterCats = new TableQuery(tag => new TwitterCats(tag))

}
