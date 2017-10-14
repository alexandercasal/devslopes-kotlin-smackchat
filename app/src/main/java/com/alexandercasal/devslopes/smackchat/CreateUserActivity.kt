package com.alexandercasal.devslopes.smackchat

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.alexandercasal.devslopes.smackchat.services.AuthService
import com.alexandercasal.devslopes.smackchat.services.UserDataService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatar(view: View) {
        var random = Random()
        val color = random.nextInt(2)
        val avatarNum = random.nextInt(28)

        if (color == 0) {
            userAvatar = "light$avatarNum"
        } else {
            userAvatar = "dark$avatarNum"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", this.packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View) {
        val random = Random()
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)

        createAvatarImageView.setBackgroundColor(Color.rgb(r, g, b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = g.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"
    }

    fun createUserClicked(view: View) {
        enableSpinner(true)
        val username = createUserNameText.text.toString()
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()

        if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(this, username, email, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess) {
                                    val userDataChange = Intent("BROADCAST_USER_DATA_CHANGE")
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        } else {
            Toast.makeText(this,"Make sure user name, email, and password are filled in.", Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }
    }

    fun enableSpinner(enable: Boolean) {
        if (enable) {
            createSpinner.visibility = View.VISIBLE
        } else {
            createSpinner.visibility = View.INVISIBLE
        }

        createUserButton.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorButton.isEnabled = !enable
    }

    fun errorToast() {
        Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }
}
