plugins {
    id("java")
    id("io.freefair.lombok") version "8.10.2"
}

group = "ec.edu.uce"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Contenedor CDI Weld
    implementation("org.jboss.weld.se:weld-se-core:5.1.3.Final")
    implementation("io.smallrye:jandex:3.2.3")

    //Base de Datos sqlite
    implementation("org.xerial:sqlite-jdbc:3.47.0.0")
    implementation("com.zaxxer:HikariCP:6.1.0")

    //MongoDB
    implementation ("org.mongodb:mongodb-driver-sync:4.9.1")

}

sourceSets {
    main {
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}