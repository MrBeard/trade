package org.test.trade

import java.io.InputStream

import scala.io.Source

object FileUtil {
  def readClientsStates(file: String) = {
    val stream : InputStream = getClass.getResourceAsStream(file)
    Source.fromInputStream(stream)
      .getLines()
      .map(Client.fromFileString)
      .foreach(ClientStorage.add)
  }

  def readBids(file: String) = {
    val stream : InputStream = getClass.getResourceAsStream(file)
    Source.fromInputStream(stream)
      .getLines()
      .map(Bid.fromFileString).toList
  }
}
