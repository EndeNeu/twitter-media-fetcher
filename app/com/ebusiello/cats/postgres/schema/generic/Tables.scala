package com.ebusiello.cats.postgres.schema.generic

import com.ebusiello.cats.postgres.schema.twitter.TwitterSchema

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.jdbc.{GetResult => GR}

object Tables extends Tables

trait Tables {
  //DDL for all tables. Call .create to execute.
  lazy val ddl = TwitterSchema.TwitterCats.ddl
}