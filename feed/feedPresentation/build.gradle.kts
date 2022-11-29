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
    // todo, this probably isn't needed it was added because can't use telloflight w/o
    Amazon.amplifyDependencies.forEach {
        "implementation"(it)
    }
}