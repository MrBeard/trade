package org.test.trade

case class Offer(stock: String, price: Int, count: Int)
abstract class Bid{
  val name: String
  val offer: Offer
}

case class Sell(name: String, offer: Offer) extends Bid
case class Buy(name: String, offer: Offer) extends Bid

object Bid{
  def fromFileString(string: String): Bid = {
    val s = string.split("\\t")
    val clientName = s(0)
    val stock = s(2)
    val price = s(3).toInt
    val count = s(4).toInt
    val offer = Offer(stock, price, count)
    s(1) match{
      case "b" => Buy( clientName, offer)
      case "s" => Sell(clientName, offer)
    }
  }
}