package com.example.utils.db

import slick.jdbc.JdbcProfile

trait DBConnection {
  val driver: JdbcProfile

  import driver.api._

  val db: Database
}
