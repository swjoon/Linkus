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