package com.example.repositories.slick

import com.example.repositories.slick.ThingsTable._
import com.example.repositories.{Things, ThingsRepository}
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._

import java.util.{Calendar, TimeZone}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ThingsRepositoryImpl(db: Database) extends ThingsRepository {
  val s3Bucket = "https://towncrierbanner.s3.ap-south-1.amazonaws.com/"

  override def all(limit: Int, offset: Int): Future[Seq[Things]] = db.run {
    val aGMTCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    aGMTCalendar.set(Calendar.HOUR_OF_DAY, 0)
    aGMTCalendar.set(Calendar.MINUTE, 0)

    things
      .filter(thing => thing.date >= aGMTCalendar.getTimeInMillis)
      .sortBy(thing => thing.date)
      .drop(offset)
      .take(limit)
      .map(identity)
      .result
  }.map(_.map(th => th.copy(bannerImagePath = s"$s3Bucket${th.bannerImagePath}")))
}
