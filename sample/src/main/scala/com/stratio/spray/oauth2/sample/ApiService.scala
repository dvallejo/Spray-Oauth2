/**
 * Copyright (C) 2016 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.spray.oauth2.sample

import akka.http.scaladsl.server.RouteConcatenation
import com.stratio.spray.oauth2.client.{OauthClient, OauthClientHelper}

trait ApiService extends RouteConcatenation with OauthClient {

  import OauthClientHelper._

  val myRoutes = {
    {
      path("p1") {
        get {
          complete(s"inn at  p1 ")
        }
      } ~
        path("p2") {
          secured { user =>
            authorize(hasRole(Seq("RoleWithoutPermision"), user)) {
              get {
                complete(s"inn at  p2")
              }
            }
          }
        } ~
        path("p3") {
          get {
            complete(s"inn at  p3")
          }
        }
    }
  }

  val otherRoute = path("noSecured") {
    get {
      complete("no secured zone")
    }
  }
  val authorizedRoute = authorized { user =>
    path("authorized") {
      get {
        complete("no secured zone")
      }
    }
  }
  val routes = otherRoute ~ secRoute ~ myRoutes ~ authorizedRoute
}



