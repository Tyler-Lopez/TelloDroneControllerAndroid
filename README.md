# Tello Drone Controller Android

This multi-module Android application allows the user to control a Ryze Tech Tello EDU drone using an Android device with virtual joysticks and is written in Kotlin using the Jetpack Compose framework. The 3.0 SDK provided by Ryze Tech was used to create a wrapper class to interact with the robot over WIFI UDP.

Utilizing AWS Amplify tools, the app features additional functionality to share drone flights with other users. Registration and login are implemented utilizing AWS Cognito. With AWS AppSync, flights may be saved locally and later uploaded to a DynamoDB data store.

## Feature Overview

* Live-stream video-feed from the front-facing camera. 
* Control roll, pitch, throttle, and yaw from virtual joysticks.
* Track the time-of-flight, determined behind-the-scenes by the difference in the time of a successful landing and a successful takeoff.
* Record and share flight summaries and more detailed in-flight realtime data.
* Register and login to an account.

## Features Todo

* Save videos taken during flight.
* Upload saved videos to an S3 Bucket.

## Data Schema

Three models exist in the GraphQL schema: Challenge, TelloFlight, TelloFlightData.

## Running This Application

*From an APK (easy)*

https://github.com/Tyler-Lopez/TelloDroneControllerAndroid/blob/main/tellodronecontroller.apk

Open this link on your phone to install the pre-built application directly.

*Building the Application Yourself (hard)*
1. If not yet downloaded, download the latest version of Android Studio.
2. Download this repository and extract it.
3. Open the extracted folder in Android Studio.
4. Once Gradle builds the project, either run it on an emulated device or by connecting a physical device by USB.

## References
Tello SDK 3.0 User Guide
https://dl.djicdn.com/downloads/RoboMaster+TT/Tello_SDK_3.0_User_Guide_en.pdf 

Medium Article on another dev's experience making a Tello controller based in XML
https://medium.com/@jithin8mathew/building-an-android-application-to-control-tello-drone-flight-and-perform-real-time-object-ab953f6c5f5b
