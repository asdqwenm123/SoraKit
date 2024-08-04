plugins {
    id("java")
    kotlin("jvm")
}

group = "io.github.asdqwenm123"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    implementation("io.github.monun:kommand-api:3.1.7")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.jar {
    destinationDirectory = File("/home/sorayumi/Desktop/cpvp/cpvp/plugins")
}
kotlin {
    jvmToolchain(17)
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}