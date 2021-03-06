package com.alexandercasal.devslopes.smackchat.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.alexandercasal.devslopes.smackchat.R
import com.alexandercasal.devslopes.smackchat.model.Message
import com.alexandercasal.devslopes.smackchat.services.UserDataService
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alexander on 10/16/2017.
 */
class MessageAdapter(val context: Context, val messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override  fun getItemCount(): Int {
        return messages.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMessage(context, messages[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage = itemView.findViewById<ImageView>(R.id.messageUserImage)
        val userName = itemView.findViewById<TextView>(R.id.messageUserNameLbl)
        val timeStamp = itemView.findViewById<TextView>(R.id.timestampLbl)
        val msgBody = itemView.findViewById<TextView>(R.id.messageBodyLbl)

        fun bindMessage(context: Context, message: Message) {
            val resourceId = context.resources.getIdentifier(message.userAvatar, "drawable", context.packageName)
            userImage.setImageResource(resourceId)
            userImage.setBackgroundColor(UserDataService.getAvatarColor(message.userAvatarColor))
            userName.text = message.userName
            timeStamp.text = returnDateString(message.timestamp)
            msgBody.text = message.message
        }

        private fun returnDateString(isoString: String): String {
            val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
            var convertedDate = Date()
            try {
                convertedDate = isoFormatter.parse(isoString)

            } catch (e: ParseException) {
                Log.d("MessageAdapter", "Can't parse date")
            }

            val outDateString = SimpleDateFormat("E, h:mm a", Locale.getDefault())
            return outDateString.format(convertedDate)
        }
    }
}