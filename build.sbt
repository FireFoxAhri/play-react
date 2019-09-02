name := "play"

version := "1.0"

lazy val `play` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.9"

libraryDependencies ++= Seq(
  "org.playframework.anorm" %% "anorm" % "2.6.4",
  "org.postgresql" % "postgresql" % "42.2.6",
  jdbc, ehcache, ws, specs2 % Test, guice)

unmanagedResourceDirectories in Test += baseDirectory(_ / "target/web/public/test").value

      