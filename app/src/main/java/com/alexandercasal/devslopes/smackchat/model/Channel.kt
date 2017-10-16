package com.alexandercasal.devslopes.smackchat.model

/**
 * Created by Alexander on 10/15/2017.
 */
class Channel(val name: String, val description: String, val id: String) {
    override fun toString(): String {
        return "#$name"
    }
}