plugins {
    id("java")
    id("application")
}
group = "com.practice.game"
version = "1.0-SNAPSHOT"

val lwjglVersion = "3.3.6"
val jomlVersion = "1.10.7"
val lwjglNatives = "natives-macos-arm64"

repositories {
    mavenCentral()
}

dependencies {
    // Testing dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.joml", "joml", jomlVersion)

    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-assimp")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-nfd")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")

    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.practice.game.Main")
    applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.practice.game.Main"
        )
    }

    from(configurations.runtimeClasspath.get().filter { it.isDirectory }.map { it }) // to include all the dependency
    from(configurations.runtimeClasspath.get().filter { !it.isDirectory }.map { zipTree(it) })
}