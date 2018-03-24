name := "trunkShareServer2"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies += "com.github.finagle" %% "finch-core" % "0.18.0"
libraryDependencies += "com.github.finagle" %% "finch-circe" % "0.18.0"
libraryDependencies += "com.twitter" %% "finagle-mysql" % "18.3.0"
libraryDependencies += "io.circe" %% "circe-generic" % "0.9.2"
libraryDependencies += "com.twitter" %% "finagle-http" % "18.3.0"
libraryDependencies += "org.skinny-framework" %% "skinny-http-client" % "2.5.2"