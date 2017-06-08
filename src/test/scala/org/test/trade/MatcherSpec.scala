package org.test.trade

import org.scalatest.{Matchers, FlatSpec}

import scala.collection.mutable

class MatcherSpec extends FlatSpec with Matchers{
  def fillClients() = {
    ClientStorage.add(Client("c1", 100, mutable.Map("A" -> 50, "B" -> 0)))
    ClientStorage.add(Client("c2", 20, mutable.Map("A" -> 50, "B" -> 20)))
    ClientStorage.add(Client("c3", 5000, mutable.Map("A" -> 0, "B" -> 0)))
  }

  "Matching" should "find two trade operations with save offer signature" in {
    fillClients()
    val bids = List(
      Sell("c1", Offer("A", 10, 5)),
      Buy("c2", Offer("A", 10, 5)))
    Matcher.processBids(bids)
    ClientStorage.get("c1").get.pouch should be (150)
    ClientStorage.get("c1").get.stocks("A") should be (45)
    ClientStorage.get("c2").get.pouch should be (-30)
    ClientStorage.get("c2").get.stocks("A") should be (55)
  }

  it should "do same in different order" in {
    fillClients()
    val bids = List(
      Buy("c2", Offer("A", 10, 5)),
      Sell("c1", Offer("A", 10, 5)))
    Matcher.processBids(bids)
    ClientStorage.get("c1").get.pouch should be (150)
    ClientStorage.get("c1").get.stocks("A") should be (45)
    ClientStorage.get("c2").get.pouch should be (-30)
    ClientStorage.get("c2").get.stocks("A") should be (55)
  }

  it should "process only first buy offer" in {
    fillClients()
    val bids = List(
      Sell("c1", Offer("A", 10, 5)),
      Buy("c2", Offer("A", 10, 5)),
      Buy("c3", Offer("A", 10, 5)))
    Matcher.processBids(bids)
    ClientStorage.get("c1").get.pouch should be (150)
    ClientStorage.get("c1").get.stocks("A") should be (45)
    ClientStorage.get("c2").get.pouch should be (-30)
    ClientStorage.get("c2").get.stocks("A") should be (55)
    ClientStorage.get("c3").get.pouch should be (5000)
    ClientStorage.get("c3").get.stocks("A") should be (0)

  }
}
