package com.example.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.example.core.{ DBUser, PhoneNumber, UserProfile }
import com.example.repo.UserRepository
import com.example.utils.common.JsonHelper

import scala.concurrent.ExecutionContextExecutor

object RouteComponent {
  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer = ActorMaterializer()
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

}

trait Routes extends JsonHelper {
  this: UserRepository =>

  import RouteComponent._

  val routes = {
    path("contacts") {
      parameter('name) { name =>
        complete {
          findByName(name).map {
            case Some(result) => HttpResponse(entity = write(result))
            case None => HttpResponse(entity = "This contact does not exist")
          }
        }
      } ~
        post {
          entity(as[String]) { json =>
            complete {
              val user = parse(json).extract[DBUser]
              create(UserProfile(user.name, user.lastName), PhoneNumber(user.phone)).map { result => HttpResponse(entity = "Contact has  been saved successfully") }
            }
          }
        }
    }
  }

}