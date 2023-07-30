package br.com.saves.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object FirebaseAuthManager {

    val auth: FirebaseAuth by lazy { Firebase.auth }
}