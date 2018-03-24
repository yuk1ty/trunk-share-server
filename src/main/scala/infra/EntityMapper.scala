package infra

import com.twitter.finagle.mysql.Row

trait EntityMapper[T] extends (Row => T)
