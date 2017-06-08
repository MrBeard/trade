package org.test.trade

import scala.collection.immutable.Queue
import scala.collection.mutable

object Matcher {

  def processBids(bids: List[Bid]): Unit = {
    //val toBuy = mutable.Map.empty[Offer, Queue[Bid]].withDefaultValue(Queue.empty[Bid])
    //val toSell = mutable.Map.empty[Offer, Queue[Bid]].withDefaultValue(Queue.empty[Bid])

    def iterate(bids: List[Bid], toBuy: Map[Offer, Queue[Bid]], toSell: Map[Offer, Queue[Bid]]): Unit ={
      if (bids.isEmpty) return
      else {
        bids.head match {
          case s: Sell =>
            val offer = s.offer
            val buyBid = toBuy(offer).find(_.name != s.name)
            if (buyBid.isEmpty)
              iterate(bids.tail, toBuy, toSell.updated(offer, toSell(offer).enqueue(s)) )
            else {
              ClientStorage.applyOffer(s.name, buyBid.get.name, offer)
              iterate(bids.tail, toBuy.updated(offer, toBuy(offer) diff Queue(buyBid.get)), toSell)
            }
          case b: Buy =>
            val offer = b.offer
            val sellBid = toSell(offer).find(_.name != b.name)
            if (sellBid.isEmpty)
              iterate(bids.tail, toBuy.updated(offer, toBuy(offer).enqueue(b)), toSell )
            else {
              ClientStorage.applyOffer(sellBid.get.name, b.name, offer)
              iterate(bids.tail, toBuy, toSell.updated(offer, toSell(offer) diff Queue(sellBid.get)))
            }
        }

      }
    }

    val map = Map.empty[Offer, Queue[Bid]].withDefaultValue(Queue.empty[Bid])
    iterate(bids, map, map)
  }

}
