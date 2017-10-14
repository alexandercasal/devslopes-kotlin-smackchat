package com.alexandercasal.devslopes.smackchat

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alexandercasal.devslopes.smackchat.services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
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
        AuthService.registerUser(this, "test@test.com", "123456") { complete ->
            if (complete) {
                Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
