package com.ebusiello.twitter.fetcher.postgres.schema.generic

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.{GetResult => GR}

/**
 * Allows to create a common table type that always has an id field, check also http://stackoverflow.com/questions/23345303/slick-2-0-generic-crud-operations
 */
abstract class RichTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
   val id: Column[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
 }
