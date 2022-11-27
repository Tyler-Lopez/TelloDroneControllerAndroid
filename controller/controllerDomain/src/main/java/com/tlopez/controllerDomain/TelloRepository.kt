package com.tlopez.controllerDomain

import android.graphics.Bitmap

interface TelloRepository {

    /** Attempt to connect to the device.
     * Must be invoked before any other command. **/
    suspend fun connect(): Result<TelloResponse>

    /** When flying, lands the device.
     *  If unable to land, returns [Result.Failure] **/
    suspend fun land(): Result<TelloResponse>

    /** Requests the current [TelloState] from the device. **/
    suspend fun receiveTelloState(): Result<TelloState>

    /** Set a listener to the callback which is invoked when a new video frame
     * is received from the device.
     * To prompt the device to receive video-feed, [videoStart] must first be invoked. */
    suspend fun setVideoBitmapListener(onBitmap: ((Bitmap) -> Unit)?)

    /** Sets the lever force of the device. **/
    suspend fun setLeverForce(
        roll: Int,
        pitch: Int,
        throttle: Int,
        yaw: Int
    ): Result<Unit>

    /** When not currently flying, prompts the device to takeoff. **/
    suspend fun takeOff(): Result<TelloResponse>

    /** Starts receiving video from the device. **/
    suspend fun videoStart(): Result<TelloResponse>

    /** Stops receiving video from the device. **/
    suspend fun videoStop(): Result<TelloResponse>
}