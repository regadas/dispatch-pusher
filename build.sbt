organization := "com.regadas"

name := "dispatch-pusher"

version := "0.1"

seq(lsSettings :_*)


libraryDependencies += "net.databinder" %% "dispatch-core" % "0.8.6"

libraryDependencies += "org.specs2" %% "specs2" % "1.5" % "test"

libraryDependencies += "net.databinder" %% "dispatch-http" % "0.8.6" % "test"
