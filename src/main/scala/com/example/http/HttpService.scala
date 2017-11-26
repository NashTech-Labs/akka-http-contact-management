package com.example.http

import akka.http.scaladsl.Http
import com.example.repo.UserRepositoryImpl

object HttpService extends App with Routes with UserRepositoryImpl {
  import RouteComponent._
  ddl.onComplete {
    _ =>
      Http().bindAndHandle(routes, "localhost", 8080)
  }

}
