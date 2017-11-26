package com.example.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.example.repo.UserRepository
import com.example.utils.db.H2DBImpl
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpec }

import scala.concurrent.ExecutionContextExecutor

class RoutesTest extends WordSpec with Matchers with ScalatestRouteTest with Routes with UserRepository with H2DBImpl with BeforeAndAfterAll {

  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

  override def beforeAll: Unit = {
    //Creating schema
    ddl
  }
  "The Contact service" should {

    "get contact detail by contact name" in {
      Get("/contacts?name=RouteUser") ~> routes ~> check {
        responseAs[String] shouldEqual """This contact does not exist"""
      }
    }

    "able to import a batch of contacts from a json request" in {

      Post("/contacts", """{ "name": "Foo1", "lastName": "Bar1", "phone": "+11 222 333" }""") ~> routes ~> check {
        responseAs[String] shouldEqual "Contact has  been saved successfully"
      }
      Get("/contacts?name=Foo1") ~> routes ~> check {
        responseAs[String] shouldEqual """{"name":"Foo1","lastName":"Bar1","phones":["+11 222 333"]}"""
      }
    }

    "able to merge contacts, if user already exists with same name and last name" in {

      Post("/contacts", """{ "name": "Foo2", "lastName": "Bar1", "phone": "+11 222 334" }""") ~> routes ~> check {
        responseAs[String] shouldEqual "Contact has  been saved successfully"
      }

      Post("/contacts", """{ "name": "Foo2", "lastName": "Bar1", "phone": "+11 222 335" }""") ~> routes ~> check {
        responseAs[String] shouldEqual "Contact has  been saved successfully"
      }

      Get("/contacts?name=Foo2") ~> routes ~> check {
        responseAs[String] shouldEqual """{"name":"Foo2","lastName":"Bar1","phones":["+11 222 334","+11 222 335"]}"""
      }
    }

  }

}
