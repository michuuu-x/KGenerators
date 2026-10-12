dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")

    // WorldGuard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.19") {
        exclude(group = "org.bstats", module = "bstats-bukkit")
    }

    implementation(project(":core"))
}
