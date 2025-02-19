plugins {
    id("java")
    id("com.github.spotbugs") version "6.1.3"
    `maven-publish`
    `java-library`
}

group = "xyz.astradev"
version = "1.0.0"

repositories {
    mavenCentral()
}

publishing {
    repositories {
        maven {
            name = "local"
            url = uri("https://m2.astradev.xyz/snapshots")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.astradev"
            artifactId = "tools"
            version = "0.0.1"
            from(components["java"])
        }
    }
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = file("${layout.buildDirectory.get()}/reports/spotbugs.html")
        setStylesheet("fancy-hist.xsl")
    }
}

dependencies {
    api ("com.squareup.okhttp3:okhttp:4.12.0")
    api ("com.google.code.gson:gson:2.11.0")
}
