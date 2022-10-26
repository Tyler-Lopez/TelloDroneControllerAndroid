package com.tlopez.tello_controller.presentation

import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify

class MainViewModel {
    init {
        Amplify.addPlugin(AWSCognitoAuthPlugin())
    }
}