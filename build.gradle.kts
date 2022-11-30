import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    kotlin("kapt") version "1.6.21"
}

kapt {
    keepJavacAnnotationProcessors = true
}

group = "com.order"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.security:spring-security-crypto")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    //Using Springdoc instead of springfox
    implementation("org.springdoc:springdoc-openapi-ui:1.6.11")

    //Postgres
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.4")
    implementation("org.postgresql:postgresql:42.5.0")

    //JunitMockito
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.junit.platform:junit-platform-runner:1.9.0")
    testImplementation("org.mockito:mockito-inline:4.8.0")
    testImplementation("org.assertj:assertj-core:3.23.1")

    //lombok

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

    //mapstruct
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //EmbeddedDB for tests
    testImplementation("io.zonky.test:embedded-postgres:2.0.0")
    testImplementation("io.zonky.test:embedded-database-spring-test:2.1.2")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

java.sourceSets["main"].java {
    srcDir("build/generated/source/kapt/main")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

allOpen {
    annotations(
        "javax.persistence.Entity",
        "javax.persistence.MappedSuperclass",
        "javax.persistence.Embedabble"
    )
}
