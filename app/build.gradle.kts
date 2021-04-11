import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
}

group = "com.create"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

sourceSets {
    create("integrationTest") {
        kotlin {
            compileClasspath += main.get().output + configurations.testRuntimeClasspath
            runtimeClasspath += output + compileClasspath
        }
    }
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}
val integrationTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.testRuntimeOnly.get())
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.amazonaws.secretsmanager:aws-secretsmanager-jdbc:1.0.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    integrationTestImplementation("org.springframework.boot:spring-boot-starter-test")
}

val springActiveProfiles = System.getenv("SPRING_PROFILES_ACTIVE")
val isIntegrationTestProfile = springActiveProfiles == "integration-test"

tasks.register<Exec>("waitForSecretsManager") {
    val secretsManagerEndpoint = if (isIntegrationTestProfile) "secretsmanager" else "localhost"
    commandLine("sh", "-c", "localstack/scripts/wait_for_service.sh 'secretsmanager' 'http://${secretsManagerEndpoint}:4566'")
}

tasks.register<Exec>("waitForSQS") {
    val sqsEndpoint = if (isIntegrationTestProfile) "sqs" else "localhost"
    commandLine("sh", "-c", "localstack/scripts/wait_for_service.sh 'sqs' 'http://${sqsEndpoint}:4566'")
}

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter("test")
    dependsOn("waitForSecretsManager", "waitForSQS")
    if (springActiveProfiles == null) {
        systemProperty("spring.profiles.active", "local")
    }
}

tasks.check { dependsOn(integrationTest) }

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
