lazy val akkaHttpVersion = "10.0.10"
lazy val akkaVersion    = "2.5.4"
lazy val slickVersion   = "3.2.1"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "com.example",
      scalaVersion    := "2.12.3"
    )),
    name := "contacts-management",
    libraryDependencies ++= Seq(
      "mysql"             % "mysql-connector-java"  % "5.1.36",
      "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
      "org.json4s"        %% "json4s-native"        % "3.5.3",
      "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
      "com.typesafe.slick"%% "slick"                % slickVersion,
      "com.typesafe.slick"%% "slick-hikaricp"       % slickVersion,

      "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
      "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"            % "3.0.1"         % Test,
      "com.h2database"    %  "h2"                   % "1.4.196"       % "test"
    )
  )

coverageMinimum := 87

coverageFailOnMinimum := true

coverageHighlighting := true
