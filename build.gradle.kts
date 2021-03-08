plugins {
    application
    scala
    jacoco
    id("org.danilopianini.git-sensitive-semantic-versioning")
}

group = "io.github.enrignagna"



repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}

dependencies {
    val scalaVersion = "2.12"
    implementation("org.scala-lang:scala-library:$scalaVersion")

    //TODO: Remove imperative part of scala version
    //implementation("ch.epfl.scala:scalafix-core_2.12:_")
    implementation("org.mongodb.scala:mongo-scala-driver_$scalaVersion:_")
    implementation("io.spray:spray-json_$scalaVersion:_")
    implementation("com.lightbend.akka:akka-stream-alpakka-mongodb_$scalaVersion:_")
    implementation("com.typesafe.akka:akka-stream_$scalaVersion:_")
    implementation("com.typesafe.akka:akka-slf4j_$scalaVersion:_")


    testImplementation("junit:junit:4.13")
    testImplementation("org.scalatest:scalatest_$scalaVersion:_")
    testImplementation("org.scalatestplus:junit-4-12_$scalaVersion:_")

    testRuntimeOnly("org.scala-lang.modules:scala-xml_$scalaVersion:_")
}


tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        xml.destination = file("${buildDir}/reports/jacoco/report.xml")
        html.isEnabled = true
        html.destination = file("${buildDir}/reports/jacoco/jacocoHtml")
    }
}

gitSemVer {
    version = computeGitSemVer()
}

