apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    Amazon.amplifyDependencies.forEach {
        "implementation"(it)
    }
}