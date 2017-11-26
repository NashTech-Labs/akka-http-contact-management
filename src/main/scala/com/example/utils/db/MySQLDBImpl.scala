package com.example.utils.db

import slick.jdbc.MySQLProfile

trait MySQLDBImpl extends DBConnection {

  val driver = MySQLProfile

  import driver.api._

  val db: Database = MySqlDB.connectionPool

}

private[db] object MySqlDB {

  import slick.jdbc.MySQLProfile.api._

  val connectionPool = Database.forConfig("mysql")

}