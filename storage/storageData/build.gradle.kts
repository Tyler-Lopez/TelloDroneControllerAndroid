apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.storageDomain))
    Amazon.amplifyDependencies.forEach {
        "implementation"(it)
    }
}