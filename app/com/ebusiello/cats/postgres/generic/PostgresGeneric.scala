package com.ebusiello.cats.postgres.generic

import com.ebusiello.cats.postgres.schema.generic.RichTable

import scala.slick.driver.PostgresDriver.simple._

/**
 * Implements a set of generic functionality for tables.
 *
 * @tparam T: the table object e.g. Salesforce, Campaign etc.
 * @tparam A: the Row object e.g. SalesforceRow, CampaignRow etc.
 */
trait PostgresGeneric[T <: RichTable[A], A] {

  /**
   * The table reference impelmentation is left to the implementing entity, the scala compiler cannot resolve object instanciation
   * with generic type, e.g. this won't compile without using reflection:
   *
   * val tableReference: TableQuery[T] = TableQuery[T]
   */
  val tableReference: TableQuery[T]

  /**
   * Insert table row.
   *
   * @param row: TableElementType
   *
   * @return T#TableElementType
   */
  def insert(row: T#TableElementType)(implicit s: Session): T#TableElementType = {
    tableReference.insert(row)
    row
  }

  /**
   * Delete row by primary key, true on success, false otherwise.
   *
   * @param id Long: primary key.
   *
   * @return Boolean
   */
  def deleteById(id: Long)(implicit s: Session): Boolean = tableReference.filter(_.id === id).delete == 1

  /**
   * Update row by primary key, true on success, false otherwise.
   *
   * @param id Long: primary key.
   * @param row TableElementType
   *
   * @return Boolean
   */
  def updateById(id: Long, row: T#TableElementType)(implicit s: Session): Boolean = tableReference.filter(_.id === id).update(row) == 1

  /**
   * Select row by primary key returning an option.
   *
   * @param id Long: primary key.
   *
   * @return Option[T#TableElementType]
   */
  def selectById(id: Long)(implicit s: Session): Option[T#TableElementType] = tableReference.where(_.id === id).firstOption

  /**
   * CVheck if a row exists using id.
   *
   * @param id Long: primary key.
   *
   * @return Boolean
   */
  def existsById(id: Long)(implicit s: Session): Boolean = {
    (for {
      row <- tableReference
      if row.id === id
    } yield row).firstOption.isDefined
  }

}