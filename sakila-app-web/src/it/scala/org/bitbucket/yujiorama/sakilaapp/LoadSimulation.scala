package org.bitbucket.yujiorama.sakilaapp

import com.intuit.karate.gatling.PreDef.{karateFeature, karateProtocol, pauseFor}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

import scala.concurrent.duration.DurationInt

class LoadSimulation extends Simulation {

  val protocol = karateProtocol(
    "/actors/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/addresses/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/categories/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/cities/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/countries/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/customers/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/films/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/film_actors/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/film_categories/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/inventories/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/languages/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/payments/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/rentals/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/staffs/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/stores/{id}" -> pauseFor("get" -> 15, "post" -> 25, "delete" -> 15, "put" -> 25)
  )

  // ヘッダー値に応じてリクエスト送信先を制御する場合などに使う
  protocol.nameResolver = (req, ctx) => req.getHeader("test-host")

  val actors: ScenarioBuilder = scenario("actors").exec(karateFeature("classpath:feature/sakila/actor.feature", "@read", "~@ignore"))

  setUp(
    actors.inject(rampUsers(10) during (5 seconds)).protocols(protocol)
  )
}
