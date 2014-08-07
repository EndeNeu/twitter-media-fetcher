package com.ebusiello.twitter.fetcher.postgres.conversion

object SlickHelper {

  /**
   * Define a conversion for slick nullable field from val to option.
   */
  implicit def valToOption[T](value: T): Option[T] = Option(value)

}