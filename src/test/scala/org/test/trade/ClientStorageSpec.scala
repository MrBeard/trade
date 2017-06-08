package org.test.trade

import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable

class ClientStorageSpec extends FlatSpec with Matchers{
  val client = Client("test", 100, mutable.Map("A" -> 50))
  ClientStorage.add(client)

  "ClientStorage" should "find client than was saved before" in {
    ClientStorage.get("test") should be (Some(client))
  }

  it should "correct increase money" in {
    ClientStorage.get("test").get.pouch +=100
    ClientStorage.get("test").get.pouch should be (200)
  }

  it should "correct sell some stock" in {
    ClientStorage.sell("test", Offer("A", 5, 5))
    ClientStorage.get("test").get.stocks("A") should be (45)
    ClientStorage.get("test").get.pouch should be (225)
  }

  it should "correct buy some stock" in {
    ClientStorage.buy("test", Offer("A", 1, 5))
    ClientStorage.get("test").get.stocks("A") should be (50)
    ClientStorage.get("test").get.pouch should be (220)
  }


}
