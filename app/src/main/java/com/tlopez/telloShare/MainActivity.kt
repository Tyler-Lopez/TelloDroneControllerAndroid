package com.tlopez.telloShare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Action
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.DataStoreConfiguration
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.tlopez.corePresentation.theme.TelloShareTheme
import com.tlopez.datastoreDomain.models.AmplifyModelProvider
import com.tlopez.navigation.TelloNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Amplify.addPlugin(AWSApiPlugin())
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.addPlugin(AWSS3StoragePlugin())
        Amplify.addPlugin(
            AWSDataStorePlugin
                .builder()
                .dataStoreConfiguration(DataStoreConfiguration.defaults())
                .modelProvider(AmplifyModelProvider.getInstance())
                .build()
        )


        Amplify.configure(applicationContext)
        setContent {
            TelloShareTheme {
                TelloNavHost(navController = rememberAnimatedNavController())
            }
        }
    }
}