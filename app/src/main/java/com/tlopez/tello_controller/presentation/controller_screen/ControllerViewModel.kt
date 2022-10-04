package com.tlopez.tello_controller.presentation.controller_screen

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.tlopez.tello_controller.domain.services.SocketService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(
    @ApplicationContext context: Context

) {

    init {
        Intent(context, SocketService::class.java)
        val b = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder =  service as SocketService.SocketBinder
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                TODO("Not yet implemented")
            }

        }
    }

}