plugins {
    id("java")
    id("maven-publish")
    id("signing")
    id("com.gradleup.nmcp").version("0.1.4")
}

group = "de.clickism"
version = property("plugin_version").toString()

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:24.0.0")
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

tasks.register<Jar>("javadocJar") {
    from(tasks.javadoc)
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            groupId = group.toString()
            artifactId = "click-gui"
            version = version.toString()
            pom {
                name.set("ClickGUI")
                description.set("A simple GUI library for Spigot.")
                url.set("https://github.com/Clickism/ClickGUI")
                licenses {
                    license {
                        name.set("GNU General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                    }
                }
                developers {
                    developer {
                        id.set("Clickism")
                        name.set("Clickism")
                        email.set("dev@clickism.de")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/Clickism/ClickGUI.git")
                    developerConnection.set("scm:git:ssh://github.com/Clickism/ClickGUI.git")
                    url.set("https://github.com/Clickism/ClickGUI")
                }
            }
        }
    }
    signing {
        sign(publishing.publications["mavenJava"])
    }
}

nmcp {
    centralPortal {
        username = findProperty("ossrhUsername") as String?
        password = findProperty("ossrhPassword") as String?
        publishingType = "USER_MANAGED"
    }
}

tasks.named("publish") {
    dependsOn(tasks["sourcesJar"], tasks["javadocJar"])
}