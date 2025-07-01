import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

configurations.all {
    resolutionStrategy {
        force(libs.kotlinx.coroutines.core)
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "composeresources.generated.resources"
    generateResClass = auto

}
kotlin {

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "app"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "blog.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                        port = 8080
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {

        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.coroutines.core)
            implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.3")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta03")

            implementation(libs.bundles.ktor.common)

            implementation("com.mikepenz:multiplatform-markdown-renderer:0.35.0")
            implementation("com.mikepenz:multiplatform-markdown-renderer-code:0.35.0")
            implementation("com.mikepenz:multiplatform-markdown-renderer-m3:0.35.0")
            implementation("com.mikepenz:multiplatform-markdown-renderer-coil3:0.35.0")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    sourceSets.all { 
        languageSettings.optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
    }
}
