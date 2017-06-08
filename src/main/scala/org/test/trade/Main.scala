package org.test.trade

object Main extends App{
  FileUtil.readClientsStates("/clients.txt")
  val bids = FileUtil.readBids("/orders.txt")
  Matcher.processBids(bids)
  ClientStorage.clients.values.toList.sortBy(t => t.name).foreach(println)
}
