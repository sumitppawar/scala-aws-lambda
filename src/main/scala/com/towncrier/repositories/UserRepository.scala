package com.towncrier.repositories

import scala.concurrent.Future

trait UserRepository {
  def findBy(mobile: String, pwd: String): Future[Option[User]]
  def findByToken(token: String): Future[Option[User]]
}


case class User(
                 id: Long,
                 fName: String,
                 lName: String,
                 mobile: String,
                 pwd: String,
                 token: String
               )