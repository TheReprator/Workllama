//include(":appModules:factList")
include(
    ":app",
    ":base-android",
    ":base",
    ":navigation",
    ":database",
    ":appModules:contactSharedModule",
    ":appModules:contactList",
    ":appModules:contactDetail"
)

rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "Workllama"
