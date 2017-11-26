package com.example.core

import com.example.utils.db.DBConnection

trait PhoneNumberTable extends UserProfileTable {
  this: DBConnection =>

  import driver.api._

  protected val phoneTableQuery = TableQuery[PhoneNumberTable]

  protected def phoneTableQueryTableAutoInc = phoneTableQuery returning phoneTableQuery.map(_.id)

  class PhoneNumberTable(tag: Tag) extends Table[PhoneNumber](tag, "phone_number") {
    val id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    val phoneNumber = column[String]("PHONENUMBER")
    val userId = column[Int]("USER_ID")
    def userFk = foreignKey("USER_FK", userId, userProfileTableQuery)(_.id, ForeignKeyAction.Restrict, ForeignKeyAction.Cascade)

    def * = (phoneNumber, userId.?, id.?) <> (PhoneNumber.tupled, PhoneNumber.unapply)

  }

}
