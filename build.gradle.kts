buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        // buildscript 단계에서 MySQL 드라이버를 사용할 수 있도록
        classpath("com.mysql:mysql-connector-j:8.0.33")
    }
}

import nu.studer.gradle.jooq.JooqEdition
import java.sql.DriverManager
import java.sql.Connection

val dbUrl: String       = project.findProperty("dbUrl")?.toString() ?: "";
val dbUser: String      = project.findProperty("dbUser")?.toString() ?: "";
val dbPasswd: String    = project.findProperty("dbPasswd")?.toString() ?: "";
val dbSchema: String    = project.findProperty("dbSchema")?.toString() ?: "";

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "6.0.1.5171"
    id("nu.studer.jooq") version "9.0"
}

group = "org.scaleadvisor"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // jooq
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    jooqGenerator("com.mysql:mysql-connector-j")
    jooqGenerator("org.jooq:jooq-meta:3.19.22")
    jooqGenerator("org.jooq:jooq-codegen:3.19.22")
    // spring
    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation("org.springframework.security:spring-security-test")
    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    // lombok
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testCompileOnly("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")
    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")
    // swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    // discord logback
    implementation("com.github.napstr:logback-discord-appender:1.0.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

//tasks.withType<Test> {
//    useJUnitPlatform()
//}

// 테스트 코드를 제외한 빌드 수행
tasks.withType<Test> {
    enabled = false
}

sonar {
    properties {
        property("sonar.projectKey", "scale-advisor_back-end")
        property("sonar.organization", "scale-advisor")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.exclusions", "src/generated")
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

// jooq generate 설정
jooq {
    version.set("3.19.22")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "com.mysql.cj.jdbc.Driver"
                    url      = dbUrl
                    user     = dbUser
                    password = dbPasswd
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.mysql.MySQLDatabase"
                        isUnsignedTypes = true
                        excludes = "sys"

                        inputSchema = dbSchema

                        forcedTypes.addAll(
                            listOf(
                                org.jooq.meta.jaxb.ForcedType().apply {
                                    userType = "java.lang.Long"
                                    includeTypes = "int unsigned"
                                },
                                org.jooq.meta.jaxb.ForcedType().apply {
                                    userType = "java.lang.Integer"
                                    includeTypes = "tinyint unsigned"
                                },
                                org.jooq.meta.jaxb.ForcedType().apply {
                                    userType = "java.lang.Integer"
                                    includeTypes = "smallint unsigned"
                                }
                            )
                        )
                    }
                    generate.apply {
                        isDaos = true
                        isJavaTimeTypes = true
                        isFluentSetters = true
                        isRecords = true
                        isDeprecated = false
                    }
                    target.apply {
                        directory = "src/generated"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

// DB 연결 가능 여부를 체크하는 헬퍼 함수
fun isDbReachable(): Boolean {
    return try {
        // (Optional) 명시적 드라이버 로딩
        Class.forName("com.mysql.cj.jdbc.Driver")
        DriverManager.getConnection(dbUrl, dbUser, dbPasswd).use { it.isValid(2) }
    } catch (e: Exception) {
        logger.lifecycle("⚠️ DB 연결 실패: ${e.message}")
        false
    }
}

tasks.named("generateJooq") {
    onlyIf {
        // 이 조건이 false일 때는 아예 실행되지 않음
        isDbReachable()
    }
}