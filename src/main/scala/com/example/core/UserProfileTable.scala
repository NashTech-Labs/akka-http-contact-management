package com.example.core

import com.example.utils.db.DBConnection

trait UserProfileTable {
  this: DBConnection =>

  import driver.api._

  protected val userProfileTableQuery = TableQuery[UserProfileTable]

  protected def userProfileTableAutoInc = userProfileTableQuery returning userProfileTableQuery.map(_.id)

  class UserProfileTable(tag: Tag) extends Table[UserProfile](tag, "user_profile") {
    val id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    val name = column[String]("NAME")
    val lastName = column[String]("LASTNAME")

    def * = (name, lastName, id.?) <> (UserProfile.tupled, UserProfile.unapply)

  }

}
