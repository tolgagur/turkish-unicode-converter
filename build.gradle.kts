plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.9.25"
  id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.tolgagureli"
version = "1.2"  // Burayı gerektiği şekilde güncelleyebilirsiniz.

repositories {
  mavenCentral()
}

// JUnit 5 bağımlılığını ekliyoruz
dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
  testImplementation("org.mockito:mockito-core:3.9.0")
  testImplementation("org.assertj:assertj-core:3.18.1")
}

intellij {
  version.set("2023.2.6")
  type.set("IC") // Target IDE Platform
  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "17"
    targetCompatibility = "17"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
  }

  patchPluginXml {
    sinceBuild.set("232")
    untilBuild.set("") // Sınırsız uyumluluk için boş bırakıyoruz
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }

  // Testlerin JUnit 5 ile çalışması için ayar
  test {
    useJUnitPlatform()  // JUnit 5'i kullanabilmek için bu satırı ekliyoruz
  }
}