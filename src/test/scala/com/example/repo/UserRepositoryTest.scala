package com.example.repo

import com.example.core.UserProfile
import com.example.utils.db.H2DBImpl
import org.scalatest.FreeSpec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.prop.TableDrivenPropertyChecks._

class UserRepositoryTest extends FreeSpec with UserRepository with H2DBImpl with ScalaFutures {

  //Creating schema
  ddlTest

  " UserRepository" - {
    "should be able to" - {
      val testData =
        Table(
          ("clue",
            "intial function call",
            "response",
            "result"),
         ("get empty result, if user doesn't exist", (),
            findByName("Albert"),
            None),
        )
      forAll(testData) {
        case (clue, initialFunctionCall, response, result) =>
          s"$clue" in {
            initialFunctionCall
            Thread.sleep(500)
            whenReady(response) { expextedResult =>
              assert(expextedResult === result)
            }
          }
      }
    }

    "should not able to " - {
      "create user with empty name" in {
        assertThrows[IllegalArgumentException] {
          UserProfile("", "Mathew")
        }
      }
    }
  }
}
