package com.alexandercasal.devslopes.smackchat.services

import android.graphics.Color
import com.alexandercasal.devslopes.smackchat.controller.App
import java.util.*

/**
 * Created by Alexander on 10/14/2017.
 */
object UserDataService {

    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun getAvatarColor(components: String) : Int {
        val strippedColor = components
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
        var r = 0
        var g = 0
        var b = 0
        var a = 1.0

        val scanner = Scanner(strippedColor)
        if (scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
            a = scanner.nextDouble()
        }

        if (a > 0.0) {
            return Color.rgb(r, g, b)
        } else {
            return Color.TRANSPARENT
        }
    }

    fun logout() {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        App.prefs.authToken = ""
        App.prefs.userEmail = ""
        App.prefs.isLoggedIn = false
        MessageService.clearMessages()
        MessageService.clearChannels()
    }
}