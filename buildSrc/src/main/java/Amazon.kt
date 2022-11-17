object Amazon {
    private const val AMPLIFY_VERSION = "1.37.5"
    private const val AWS_VERSION = "2.55.0"

    const val COGNITO_IDENTITY_PROVIDER =
        "com.amazonaws:aws-android-sdk-cognitoidentityprovider:$AWS_VERSION"

    val amplifyDependencies = listOf(
        "com.amplifyframework:core:$AMPLIFY_VERSION",
        "com.amplifyframework:aws-api:$AMPLIFY_VERSION",
        "com.amplifyframework:aws-auth-cognito:$AMPLIFY_VERSION",
        "com.amplifyframework:aws-datastore:$AMPLIFY_VERSION",
        "com.amplifyframework:aws-storage-s3:$AMPLIFY_VERSION"
    )

    val awsDependencies = listOf(
        "com.amazonaws:aws-android-sdk-core:$AWS_VERSION",
        "com.amazonaws:aws-android-sdk-cognitoauth:$AWS_VERSION",
        COGNITO_IDENTITY_PROVIDER,
        "com.amazonaws:aws-android-sdk-cognitoidentityprovider-asf:$AWS_VERSION"
    )
}