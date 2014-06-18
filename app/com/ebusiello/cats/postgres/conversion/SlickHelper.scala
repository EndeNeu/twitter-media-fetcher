package com.ebusiello.cats.postgres.conversion

object SlickHelper {

  /**
   * Define a conversion for slick nullable field from val to option.
   *
   * @note Option(null) == None
   */
  implicit def valToOption[T](value: T): Option[T] = Option(value)

}