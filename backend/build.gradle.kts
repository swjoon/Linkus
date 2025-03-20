plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    jacoco
}

group = "com.app"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

jacoco{
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport{
    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
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
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-core")

    // Prometheus
    implementation("io.micrometer:micrometer-registry-prometheus")

    // WebSocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    // MongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    //Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    //Jackson Datatype: JSR310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    //Querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    //JJWT :: API
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    //JJWT :: Impl
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    //JJWT :: Extensions :: Jackson
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    annotationProcessor("org.projectlombok:lombok")
    //Jakarta Annotations API
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    //Jakarta Persistence API
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    //Querydsl
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    //test lombok
    testImplementation("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // env 파일 사용 (카카오 보안 키)
    implementation("me.paulschwarz:spring-dotenv:4.0.0")
}

//tasks.jacocoTestCoverageVerification {
//    violationRules {
//        rule {
//            // 'element'가 없으면 프로젝트의 전체 파일을 합친 값을 기준으로 한다.
//            limit {
//                // 'counter'를 지정하지 않으면 default는 'INSTRUCTION'
//                // 'value'를 지정하지 않으면 default는 'COVEREDRATIO'
//                minimum = "0.30".toBigDecimal()
//            }
//        }
//
//        rule {
//            // 룰을 간단히 켜고 끌 수 있다.
//            enabled = true
//
//            // 룰을 체크할 단위는 클래스 단위
//            element = "CLASS"
//
//            // 브랜치 커버리지를 최소한 90% 만족시켜야 한다.
//            limit {
//                counter = "BRANCH"
//                value = "COVEREDRATIO"
//                minimum = "0.90".toBigDecimal()
//            }
//
//            // 라인 커버리지를 최소한 80% 만족시켜야 한다.
//            limit {
//                counter = "LINE"
//                value = "COVEREDRATIO"
//                minimum = "0.80".toBigDecimal()
//            }
//
//            // 빈 줄을 제외한 코드의 라인수를 최대 200라인으로 제한한다.
//            limit {
//                counter = "LINE"
//                value = "TOTALCOUNT"
//                maximum = "200".toBigDecimal()
////              maximum = "8".toBigDecimal()
//            }
//
//            // 커버리지 체크를 제외할 클래스들
//            excludes = listOf(
////                    "*.test.*",
////                    "*.Kotlin*"
//            )
//        }
//    }
//}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

//Querydsl - Start
val generatedDir = file("src/main/generated")

sourceSets {
    main {
        java {
            srcDir(generatedDir)
        }
    }
}

tasks {
    compileJava {
        options.annotationProcessorGeneratedSourcesDirectory = generatedDir
    }

    clean {
        doFirst {
            delete(generatedDir)
        }
    }
}
//Querydsl - End