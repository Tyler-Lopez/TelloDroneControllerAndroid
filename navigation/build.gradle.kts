apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.authPresentation))
    "implementation"(project(Modules.controllerPresentation))
    "implementation"(project(Modules.feedPresentation))
    "implementation"(project(Modules.settingsPresentation))
    Accompanist.accompanistDependencies.forEach {
        "implementation"(it)
    }
}