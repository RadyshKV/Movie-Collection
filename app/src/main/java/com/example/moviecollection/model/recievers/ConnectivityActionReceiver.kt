package com.example.moviecollection.model.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.moviecollection.R

class ConnectivityActionReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, context?.getString(R.string.connection_changet), Toast.LENGTH_SHORT).show()
    }
}