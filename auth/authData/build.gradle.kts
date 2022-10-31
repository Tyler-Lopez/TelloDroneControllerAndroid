apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.authDomain))
    Amazon.amplifyDependencies.forEach { "implementation"(it) }
    Amazon.awsDependencies.forEach { "implementation"(it) }
}