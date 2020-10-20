package org.bitbucket.yujiorama.sakilaapp

import com.intuit.karate.gatling.PreDef.{karateFeature, karateProtocol, pauseFor}
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import scala.util.Random

class LoadSimulation extends Simulation {

  val protocol = karateProtocol(
    "/actors/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/addresses/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/categories/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/cities/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/countries/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/customers/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/films/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/film_actors/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/film_categories/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/inventories/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/languages/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/payments/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/rentals/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/staffs/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25),
    "/stores/{id}" ->
      pauseFor("get" -> 50, "post" -> 25, "delete" -> 15, "put" -> 25)
  )

  // ヘッダー値に応じてリクエスト送信先を制御する場合などに使う
  protocol.nameResolver = (req, ctx) => req.getHeader("test-host")

  val actor: ScenarioBuilder = scenario("read actor")
    .forever(
      exec(karateFeature("classpath:feature/sakila/actor.feature", "@read", "~@ignore"))
    )


  val httpProtocol = http
    .baseUrl(System.getProperty("app.url"))

  val rentalIdFeeder = Iterator.continually(
    Map("id" -> (Random.nextInt(16048) + 1).toString)
  )
  val paymentIdFeeder = Iterator.continually(
    Map("id" -> (Random.nextInt(16048) + 1).toString)
  )
  val inventoryIdFeeder = Iterator.continually(
    Map("id" -> (Random.nextInt(4500) + 1).toString)
  )

  val rental = scenario("rental")
    .feed(rentalIdFeeder)
    .forever(exec(http("read rental")
      .get("/rentals/${id}")
      .check(status.is(200)))
      .pause(11 millis))

  val payment = scenario("payment")
    .feed(paymentIdFeeder)
    .forever(exec(http("read payment")
      .get("/payments/${id}")
      .check(status.is(200)))
      .pause(23 millis))

  val inventory = scenario("inventory")
    .feed(inventoryIdFeeder)
    .forever(exec(http("read inventory")
      .get("/inventories/${id}")
      .check(status.is(200)))
      .pause(37 millis))

  setUp(
    actor.inject(atOnceUsers(3)).protocols(protocol),
    rental.inject(atOnceUsers(3)).protocols(httpProtocol),
    inventory.inject(atOnceUsers(3)).protocols(httpProtocol),
    payment.inject(atOnceUsers(3)).protocols(httpProtocol),
  )
    .maxDuration(60 seconds)
}
