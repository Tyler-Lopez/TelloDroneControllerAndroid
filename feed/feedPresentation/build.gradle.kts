apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.datastoreDomain))
    Accompanist.accompanistDependencies.forEach {
        "implementation"(it)
    }
}