package com.example.semestralka.service

import android.util.Log
import okhttp3.internal.and
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class PasswordHasher {

    fun hashPassword(passwordToHash: String): String
    {
        val salt: String = generateSalt()

        var generatedPassword: String? = null

        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA-512")
            md.update(salt.toByteArray())
            val bytes: ByteArray = md.digest(passwordToHash.toByteArray())
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(
                    Integer.toString((bytes[i] and 0xff) + 0x100, 16)
                        .substring(1)
                )
            }
            generatedPassword = sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        Log.e("PASS", generatedPassword.toString())

        return generatedPassword!!
    }

    private fun generateSalt(): String {
        return "supertajnysalt"
    }
}