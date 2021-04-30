package com.towncrier.repositories.slick

import com.towncrier.repositories.Things
import slick.jdbc.MySQLProfile.api._

class ThingsTable(tag: Tag) extends Table[Things](tag, _tableName = "things") {

  def * =
    (id, creatorId, createdTime, bannerImagePath, kind, date, hasTime, title, description, likeCount) <> (Things.tupled, Things.unapply)

  def id = column[Long]("id", O.PrimaryKey)

  def creatorId = column[Long]("creator_id")

  def createdTime = column[Long]("created_time")

  def bannerImagePath = column[String]("banner_image_path")

  def kind = column[String]("kind")

  def date = column[Long]("thing_date")

  def hasTime = column[Boolean]("has_time")

  def title = column[String]("title")

  def description = column[Option[String]]("description")

  def likeCount = column[Long]("like_count")
}

object ThingsTable {
  val things = TableQuery[ThingsTable]
}