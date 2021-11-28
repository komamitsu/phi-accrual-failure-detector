plugins {
    idea
    `java-library`
    `maven-publish`
    signing
}

group = "org.komamitsu"
version = "1.0.0-SNAPSHOT"

base {
    archivesBaseName = "phi-accrual-failure-detector"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_6
    targetCompatibility = JavaVersion.VERSION_1_6

    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.12")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "phi-accrual-failure-detector"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("phi-accrual-failure-detector")
                description.set("A port of Akka's Phi Accrual Failure Detector")
                url.set("https://github.com/komamitsu/phi-accrual-failure-detector")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("komamitsu")
                        name.set("Mitsunori Komatsu")
                        email.set("komamitsu@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/komamitsu/phi-accrual-failure-detector.git")
                    developerConnection.set("scm:git:git@github.com:komamitsu/phi-accrual-failure-detector.git")
                    url.set("https://github.com/komamitsu/phi-accrual-failure-detector")
                }
            }
        }
    }
    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            val releasesRepoUrl = uri(layout.buildDirectory.dir("repos/releases"))
            val snapshotsRepoUrl = uri(layout.buildDirectory.dir("repos/snapshots"))
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = if (project.hasProperty("ossrhUsername")) {
                    project.property("ossrhUsername").toString()
                }
                else {
                    ""
                }
                password = if (project.hasProperty("ossrhPassword")) {
                    project.property("ossrhPassword").toString()
                }
                else {
                    ""
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
    setRequired(project.hasProperty("signing.gnupg.keyName"))
    sign(configurations.archives.get())
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

