package org.test.trade

import scala.collection.mutable

case class Client(name: String,var pouch: Int, stocks : mutable.Map[String, Int])
object Client{

  def fromFileString(string: String): Client = {
    val s = string.split("\\t")
    val name = s(0)
    val pouch = s(1).toInt
    val stock = StockList.stocks.zip(s.drop(2).map(_.toInt))
    Client(name, pouch, collection.mutable.Map(stock.toSeq: _*).withDefaultValue(0))
  }

}
