organization := "me.regadas"

name := "dispatch-pusher"

version := "0.1"

seq(lsSettings :_*)

(LsKeys.tags in LsKeys.lsync) := Seq("dispatch", "pusher", "client")

(externalResolvers in LsKeys.lsync) := Seq("my very own resolver" at "https://github.com/regadas/repo/raw/master")

(description in LsKeys.lsync) :="Scala dispatch client library for Pusher."

libraryDependencies += "net.databinder" %% "dispatch-core" % "0.8.6"

libraryDependencies += "org.specs2" %% "specs2" % "1.5" % "test"

libraryDependencies += "net.databinder" %% "dispatch-http" % "0.8.6" % "test"

publishTo :=  Some(Resolver.file("regadas repo", new java.io.File("/tmp/repo")))
