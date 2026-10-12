plugins {
    id("com.gradleup.shadow")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":v26_3"))
}

tasks.shadowJar {
    destinationDirectory = rootProject.layout.buildDirectory.dir("libs")
    archiveBaseName = "KGenerators"
    archiveVersion = "${project.version}"
    archiveClassifier = ""

    relocate("de.tr7zw.changeme.nbtapi", "me.kryniowesegryderiusz.kgenerators.dependencies.shaded.nbtapi")}

// The plain jar would only contain READ.ME, the shaded one is the deliverable
tasks.jar {
    enabled = false
}
