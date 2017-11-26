package com.example.core

case class UserProfile(name: String, lastName: String, id: Option[Int] = None) {
  require(name.nonEmpty, "name.empty")
}

case class User(name: String, lastName: String, phones: Set[String])

case class DBUser(name: String, lastName: String, phone: String)