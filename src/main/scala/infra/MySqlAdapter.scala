package infra

import com.twitter.finagle.Mysql

trait MySqlAdapter {
  lazy val client = Mysql.client
    .withCredentials("bidder_user", "bidder_pass")
    .withDatabase("junctiontokyo")
    .newRichClient("127.0.0.1:3306")
}

object MySqlAdapter extends MySqlAdapter

trait UsesMySqlAdapter {
  val mySqlAdapter: MySqlAdapter
}

trait MixInMySqlAdapter {
  val mySqlAdapter: MySqlAdapter = MySqlAdapter
}
