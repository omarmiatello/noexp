plugins {
    kotlin("jvm") version "1.3.72"
}

group = "com.github.omarmiatello.noexp"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("com.squareup.okhttp3:okhttp:4.4.0")
    implementation(project(":categories-parser"))
    implementation(project(":domain"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}