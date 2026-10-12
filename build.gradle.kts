plugins {
    // Applied in the submodules, declared here so they share one classloader
    id("com.gradleup.shadow") version "9.6.1" apply false
}

allprojects {
    group = "me.kryniowesegryderiusz"
    version = "8.0.0"
}

subprojects {
    apply(plugin = "java-library")

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }

    // Third party plugin APIs pull in their own (old) Spigot API, which clashes with paper-api
    configurations.matching { it.name == "compileClasspath" }.configureEach {
        exclude(group = "org.spigotmc", module = "spigot-api")
        exclude(group = "org.bukkit", module = "bukkit")
        exclude(group = "org.bukkit", module = "craftbukkit")
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release = 25
    }
}
