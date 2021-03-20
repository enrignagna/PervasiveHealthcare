plugins {
    application
    scala
    jacoco
    id("org.danilopianini.git-sensitive-semantic-versioning")
    //id ("org.sonatype.gradle.plugins.scan") version "2.0.0" // Update the version as needed
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
    implementation("com.typesafe.akka:akka-http_$scalaVersion:_")
    implementation("com.typesafe.akka:akka-http-spray-json_$scalaVersion:_")
    implementation("com.typesafe.akka:akka-persistence-typed_$scalaVersion:_")
    implementation("xyz.driver:spray-json-derivation_$scalaVersion:0.4.3")
    implementation("com.typesafe.akka:akka-actor-typed_$scalaVersion:_")
    //implementation("ch.epfl.scala:scalafix-core_2.12:_")
    //implementation("org.scala-lang:scala-library:2.12")


    testImplementation("junit:junit:_")
    testImplementation("org.scalatest:scalatest_$scalaVersion:_")
    testImplementation("org.scalatestplus:junit-4-12_$scalaVersion:_")
    testImplementation("com.typesafe.akka:akka-http-testkit_$scalaVersion:_")
    testImplementation("com.typesafe.akka:akka-actor-testkit-typed_$scalaVersion:_")

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