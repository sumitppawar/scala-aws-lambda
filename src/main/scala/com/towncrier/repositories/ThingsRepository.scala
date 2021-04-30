package com.towncrier.repositories

import scala.concurrent.Future

trait ThingsRepository {
  def all(limit: Int, offset: Int): Future[Seq[Things]]
}

case class Things(
                   id: Long,
                   creatorId: Long,
                   createdTime: Long,
                   bannerImagePath: String,
                   kind: String,
                   thingDate: Long,
                   hasTime: Boolean,
                   title: String,
                   description: Option[String],
                   likeCount: Long
                 )
