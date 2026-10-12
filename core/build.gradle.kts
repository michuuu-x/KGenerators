dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")

    // Shaded into the final jar (see :dist)
    implementation("de.tr7zw:item-nbt-api:2.16.0")
    implementation("com.zaxxer:HikariCP:4.0.3")

    compileOnly("com.google.code.findbugs:jsr305:3.0.2")

    // Project Lombok
    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")

    // SuperiorSkyblockAPI
    compileOnly("com.bgsoftware:SuperiorSkyblockAPI:2026.3")

    // Vault
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")

    // ItemsAdder
    compileOnly("com.github.LoneDev6:api-itemsadder:3.6.1")

    // WorldEdit
    compileOnly("com.sk89q.worldedit:worldedit-core:7.4.5")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.4.5")

    // WildStacker
    compileOnly("com.bgsoftware:WildStackerAPI:2026.2")

    // PlaceholderAPI
    compileOnly("me.clip:placeholderapi:2.12.3")

    // LitMinions
    compileOnly("com.github.WaterArchery:LitMinionsAPI:3.1.7")

    // Lands
    compileOnly("com.github.angeschossen:LandsAPI:7.25.4")

    // MythicMobs
    compileOnly("io.lumine:Mythic-Dist:5.13.0")

    // NexoMC
    compileOnly("com.nexomc:nexo:1.29.1")

    // PlayerPoints
    compileOnly("org.black_ixx:playerpoints:3.3.5")
}

// Only plugin.yml is filtered, other resources are copied as they are
val buildNumber: String = System.getenv("BUILD_NUMBER") ?: "Unknown"
val pluginVersion: String = project.version.toString()

tasks.processResources {
    inputs.property("version", pluginVersion)
    inputs.property("buildNumber", buildNumber)

    filesMatching("plugin.yml") {
        expand(
            "project" to mapOf("version" to pluginVersion),
            "BUILD_NUMBER" to buildNumber,
        )
    }
}
