package com.example.repositories.slick

import com.example.repositories.slick.UsersTable.users
import com.example.repositories.{User, UserRepository}

import scala.concurrent.Future
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._

class UserRepositoryImpl(db: Database) extends UserRepository {
  override def findBy(mobile: String, pwd: String): Future[Option[User]] = db.run {
    users
      .filter(user => user.mobile === mobile && user.pwd === pwd)
      .result
      .headOption

  }

  override def findByToken(token: String): Future[Option[User]] =
    db.run {
      users
        .filter(user => user.token === token)
        .result
        .headOption
    }
}
