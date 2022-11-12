apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.datastoreDomain))
    Amazon.amplifyDependencies.forEach {
        "implementation"(it)
    }
}