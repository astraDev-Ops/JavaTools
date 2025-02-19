plugins {
    id("java")
    id("com.github.spotbugs") version "6.1.3"
    id ("maven")
}

group = "xyz.astradev"
version = "1.0.0"

repositories {
    mavenCentral()
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = file("${layout.buildDirectory.get()}/reports/spotbugs.html")
        setStylesheet("fancy-hist.xsl")
    }
}

dependencies {
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation ("com.google.guava:guava:33.3.1-jre")
}
