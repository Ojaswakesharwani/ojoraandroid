// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
val installGitHooks by tasks.registering(Copy::class) {
    description = "Installs git hooks automatically"
    group = "git hooks"

    // File kahan se uthani hai
    from(file("$rootDir/scripts/githooks/pre-commit"))
    // File kahan dalni hai
    into(file("$rootDir/.git/hooks/"))

    doLast {
        // Mac/Linux systems ke liye executable permission set karna
        file("$rootDir/.git/hooks/pre-commit").setExecutable(true, false)
        println("✅ Git pre-commit hook installed successfully!")
    }
}

// 2. Isko 'clean' task par attach kar do (bina Gradle ki speed slow kiye)
tasks.register("clean", Delete::class) {

    delete(rootProject.layout.buildDirectory)


    dependsOn(installGitHooks)
}