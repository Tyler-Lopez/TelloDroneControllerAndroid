apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.corePresentation))
    "implementation"(project(Modules.datastoreDomain))
    "implementation"(project(Modules.storageDomain))
    "implementation"(Coil.coilCompose)
    Accompanist.accompanistDependencies.forEach {
        "implementation"(it)
    }
}