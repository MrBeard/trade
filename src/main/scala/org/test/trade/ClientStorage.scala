package org.test.trade

import scala.collection.mutable

object ClientStorage {
  var clients = mutable.HashMap.empty[String, Client]

  def add(client: Client): Unit = clients(client.name) = client

  def get(name: String): Option[Client] = clients.get(name)

  def sell(name: String, offer: Offer) = {
    clients.get(name).foreach({ client =>
      client.stocks(offer.stock) -= offer.count
      client.pouch += offer.price * offer.count
    })
  }

  def buy(name: String, offer: Offer) = {
    clients.get(name).foreach({ client =>
      client.stocks(offer.stock) += offer.count
      client.pouch -= offer.price * offer.count
    })
  }

  def applyOffer(seller: String, buyer: String, offer: Offer): Boolean = {
    sell(seller, offer)
    buy(buyer, offer)
    true
  }
}
