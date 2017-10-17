package com.alexandercasal.devslopes.smackchat.services

import android.content.Context
import android.util.Log
import com.alexandercasal.devslopes.smackchat.controller.App
import com.alexandercasal.devslopes.smackchat.model.Channel
import com.alexandercasal.devslopes.smackchat.model.Message
import com.alexandercasal.devslopes.smackchat.utils.URL_GET_CHANNELS
import com.alexandercasal.devslopes.smackchat.utils.URL_GET_MESSAGES
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONException

/**
 * Created by Alexander on 10/15/2017.
 */
object MessageService {

    val channels = ArrayList<Channel>()
    val messages = ArrayList<Message>()

    fun getChannels(complete: (Boolean) -> Unit) {
        val channelsRequest = object : JsonArrayRequest(Method.GET, URL_GET_CHANNELS, null, Response.Listener { response ->
            try {
                for (x in 0 until response.length()) {
                    val channel = response.getJSONObject(x)
                    val name = channel.getString("name")
                    val channelDesc = channel.getString("description")
                    val channelId = channel.getString("_id")
                    val newChannel = Channel(name, channelDesc, channelId)
                    channels.add(newChannel)
                }
                complete(true)
            } catch (e: JSONException) {
                Log.d("MessageService", "EXC: ${e.localizedMessage}")
            }
        }, Response.ErrorListener { error ->
            Log.d("MessageService", "Could not retrieve channels")
            complete(false)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        App.prefs.requestQueue.add(channelsRequest)
    }

    fun getMessages(chanelId: String, complete: (Boolean) -> Unit) {
        val url = "$URL_GET_MESSAGES$chanelId"
        val messageRequest = object : JsonArrayRequest(Method.GET, url, null, Response.Listener { response ->
            clearMessages()
            try {
                for (x in 0 until response.length()) {
                    val message = response.getJSONObject(x)
                    val msgBody = message.getString("messageBody")
                    val channelId = message.getString("channelId")
                    val id = message.getString("_id")
                    val userName = message.getString("userName")
                    val userAvatar = message.getString("userAvatar")
                    val userAvatarColor = message.getString("userAvatarColor")
                    val timeStamp = message.getString("timeStamp")

                    val newMessage = Message(msgBody, userName, channelId, userAvatar, userAvatarColor, id, timeStamp)
                    messages.add(newMessage)
                }
                complete(true)
            } catch (e: JSONException) {
                Log.d("MessageService", "EXC: ${e.localizedMessage}")
            }
        }, Response.ErrorListener { error ->
            Log.d("MessageService", "Unable to get messages")
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        App.prefs.requestQueue.add(messageRequest)
    }

    fun clearMessages() {
        messages.clear()
    }

    fun clearChannels() {
        channels.clear()
    }
}