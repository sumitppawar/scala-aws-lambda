package com.example.repositories.slick

import com.example.repositories.User
import slick.jdbc.MySQLProfile.api._

class UsersTable(tag: Tag) extends Table[User](tag, _tableName = "users") {


  def * =
    (id, fName, lName, mobile, pwd, token) <> (User.tupled, User.unapply)

  def id = column[Long]("id", O.PrimaryKey)

  def fName = column[String]("fname")

  def lName = column[String]("lname")

  def mobile = column[String]("mobile")

  def pwd = column[String]("pwd")

  def token = column[String]("token")

}

object UsersTable {
  val users = TableQuery[UsersTable]
}