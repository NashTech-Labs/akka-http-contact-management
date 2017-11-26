package com.example.repo

import com.example.core._
import com.example.utils.db.{ DBConnection, MySQLDBImpl }
import slick.jdbc.meta.MTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait UserRepository extends UserProfileTable with PhoneNumberTable {
  this: DBConnection =>

  import driver.api._

  def create(userProfile: UserProfile, phoneNumber: PhoneNumber): Future[Int] = {

    val query = findUserProfileByName(userProfile).map {
      case Some(u) =>
        for {
          user <- phoneTableQueryTableAutoInc += phoneNumber.copy(userId = u.id)
        } yield user
      case None =>
        for {
          uId <- userProfileTableAutoInc += userProfile
          user <- phoneTableQueryTableAutoInc += phoneNumber.copy(userId = Option(uId))
        } yield user
    }
    query.map(q => db.run(q.transactionally)).flatten
  }

  def findUserProfileByName(userProfile: UserProfile): Future[Option[UserProfile]] = db.run {
    userProfileTableQuery.filter(_.name === userProfile.name).filter(_.lastName === userProfile.lastName).result.headOption
  }

  def findByName(name: String): Future[Option[User]] = {
    val res = db.run(
      (for {
      user <- userProfileTableQuery if user.name === name
      address <- phoneTableQuery if address.userId === user.id
    } yield (user, address)).result
    )

    res.map { result =>
      val phoneNumbers = result.map(_._2.phoneNumber).toSet
      result.map(_._1).headOption.map(userProfile => User(userProfile.name, userProfile.lastName, phoneNumbers))
    }
  }

  def ddlTest = {
    val schema = userProfileTableQuery.schema ++ phoneTableQuery.schema
    db.run {
      schema.create
    }
  }

  def ddl = {
    val tables = List(userProfileTableQuery, phoneTableQuery)

    val existing = db.run(MTable.getTables)
    existing.flatMap(v => {
      val names = v.map(mt => mt.name.name)
      val createIfNotExist = tables.filter(table =>
        !names.contains(table.baseTableRow.tableName)).map(_.schema.create)
      db.run(DBIO.sequence(createIfNotExist))
    })
  }
}

trait UserRepositoryImpl extends UserRepository with MySQLDBImpl
