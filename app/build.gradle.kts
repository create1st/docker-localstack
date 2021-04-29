import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.5.0"
}

group = "com.create"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    jcenter()
}

sourceSets {
    create("integrationTest") {
        kotlin {
            compileClasspath += main.get().output + test.get().output + configurations.testRuntimeClasspath.get()
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

sourceSets {
    create("acceptanceTest") {
        kotlin {
            compileClasspath += main.get().output + test.get().output + configurations.testRuntimeClasspath.get()
            runtimeClasspath += output + compileClasspath
        }
    }
}

val acceptanceTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}
val acceptanceTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.testRuntimeOnly.get())
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.addons:reactor-extra")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.amazonaws.secretsmanager:aws-secretsmanager-jdbc:1.0.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    acceptanceTestImplementation("io.cucumber:cucumber-java8:6.10.2")
    acceptanceTestImplementation("io.cucumber:cucumber-junit:6.10.2")
    acceptanceTestImplementation("io.cucumber:cucumber-spring:6.10.2")
}

val springActiveProfiles = System.getenv("SPRING_PROFILES_ACTIVE")
val isIntegrationTestProfile = springActiveProfiles == "integration-test"
val isAcceptanceTestProfile = springActiveProfiles == "acceptance-test"

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

val cucumberRuntime: Configuration by configurations.creating {
    extendsFrom(configurations["acceptanceTestImplementation"])
}

val acceptanceTest = task<Test>("acceptanceTest") {
    description = "Runs acceptance tests."
    group = "verification"

    testClassesDirs = sourceSets["acceptanceTest"].output.classesDirs
    classpath = sourceSets["acceptanceTest"].runtimeClasspath
    shouldRunAfter("assemble", "test")
    dependsOn("waitForSecretsManager", "waitForSQS")
    if (springActiveProfiles == null) {
        systemProperty("spring.profiles.active", "local")
    }
    doLast {
        javaexec {
            main = "io.cucumber.core.cli.Main"
            classpath = cucumberRuntime + sourceSets["acceptanceTest"].runtimeClasspath + sourceSets["acceptanceTest"].output
            args = listOf("--plugin", "pretty", "--glue", "com.create.dockerlocalhost", "src/acceptanceTest/resources")
        }
    }
}

tasks.check { dependsOn(integrationTest) }

tasks.register<Exec>("waitForSecretsManager") {
    val secretsManagerEndpoint = if (isIntegrationTestProfile || isAcceptanceTestProfile) "secretsmanager" else "localhost"
    commandLine("sh", "-c", "localstack/scripts/wait_for_service.sh 'secretsmanager' 'http://${secretsManagerEndpoint}:4566'")
}

tasks.register<Exec>("waitForSQS") {
    val sqsEndpoint = if (isIntegrationTestProfile || isAcceptanceTestProfile) "sqs" else "localhost"
    commandLine("sh", "-c", "localstack/scripts/wait_for_service.sh 'sqs' 'http://${sqsEndpoint}:4566'")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
