package org.test.trade

import scala.collection.immutable.Queue
import scala.collection.mutable

object Matcher {

  def processBids(bids: List[Bid]): Unit = {
    val toBuy = mutable.Map.empty[Offer, Queue[Bid]].withDefaultValue(Queue.empty[Bid])
    val toSell = mutable.Map.empty[Offer, Queue[Bid]].withDefaultValue(Queue.empty[Bid])

    for (bid <- bids) {
      bid match {
        case s: Sell => handleSell(s)
        case b: Buy => handleBuy(b)
      }

      def handleSell(s: Sell) = {
        val offer = s.offer
        val buyBid = toBuy(offer).find(_.name != s.name)
        if (buyBid.isEmpty)
          toSell(offer) = toSell(offer).enqueue(s)
        else {
          val bb = buyBid.get
          if (ClientStorage.applyOffer(s.name, bb.name, offer))
            toBuy(offer) = toBuy(offer) diff Queue(bb)
        }
      }

      def handleBuy(b: Buy) = {
        val offer = b.offer
        val sellBid = toSell(offer).find(_.name != b.name)
        if (sellBid.isEmpty)
          toBuy(offer) = toBuy(offer).enqueue(b)
        else {
          val sb = sellBid.get
          if (ClientStorage.applyOffer(sb.name, b.name, offer))
            toSell(offer) = toSell(offer) diff Queue(sb)
        }

      }
    }
  }

}
