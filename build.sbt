name := "tofu-logs-example"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "ru.tinkoff" %% "tofu" % "0.9.0"
libraryDependencies += "ru.tinkoff" %% "tofu-logging" % "0.9.0"
libraryDependencies += "org.manatki" %% "derevo-core" % "0.11.6"

scalacOptions ++= Seq(
  "-deprecation",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-feature",
  "-Ymacro-annotations",
  "-language:higherKinds",
)
