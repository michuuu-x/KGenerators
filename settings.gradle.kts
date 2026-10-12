plugins {
    // Downloads the JDK 25 toolchain when it is not installed
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "kgenerators-parent"

include("core", "v26_3", "dist")

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS

    repositories {
        mavenCentral()

        // Paper API
        maven("https://repo.papermc.io/repository/maven-public/")

        // jitpack
        maven("https://jitpack.io")

        // NBT-API
        maven("https://repo.codemc.io/repository/maven-public/")

        // WorldEdit, WorldGuard
        maven("https://maven.enginehub.org/repo/")

        // WildStacker, SuperiorSkyblock2
        maven("https://repo.bg-software.com/repository/api/")

        // PlaceholderAPI
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

        // MythicMobs
        maven("https://mvn.lumine.io/repository/maven-public/")

        // NexoMC
        maven("https://repo.nexomc.com/releases")

        // PlayerPoints
        maven("https://repo.rosewooddev.io/repository/public/")
    }
}
