package br.com.saves.feature.authentication

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.saves.BuildConfig
import br.com.saves.R
import br.com.saves.common.FirebaseAuthManager
import br.com.saves.ui.composables.SavesButton
import br.com.saves.ui.theme.SavesTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

private const val TAG = "AuthenticationScreen"

@Composable
fun AuthenticationRoute() {
    AuthenticationScreen()
}

@Composable
fun AuthenticationScreen() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            if (result.data?.action == ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST) {
                val exception: Exception? =
                    result.data?.getSerializableExtra(ActivityResultContracts.StartIntentSenderForResult.EXTRA_SEND_INTENT_EXCEPTION) as Exception?
                Log.e(TAG, "Couldn't start One Tap UI: ${exception?.localizedMessage}")
            }
            return@rememberLauncherForActivityResult
        }

        val oneTapClient = Identity.getSignInClient(context)
        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
        val idToken = credential.googleIdToken
        if (idToken != null) {
            Log.d(TAG, "AuthenticationScreen: $idToken")
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            FirebaseAuthManager.auth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener { task -> 
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential:success")
                    } else {
                        Log.w(TAG, "signInWithCredential:faillure", task.exception)
                    }
                }
        } else {
            Log.d(TAG, "AuthenticationScreen: Null token")
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 16.dp, vertical = 72.dp)
    ) {
        Icon(
            modifier = Modifier.weight(1f),
            painter = painterResource(id = R.drawable.saves_vertical_logo),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        val scope = rememberCoroutineScope()

        SavesButton(
            onClick = {
                scope.launch {
                    signIn(
                        context = context,
                        launcher = launcher
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = Color.Unspecified
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_colorfull_icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Entrar com o Google", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Preview
@Composable
private fun AuthenticationScreenPreview() {
    SavesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AuthenticationScreen()
        }
    }
}

private suspend fun signIn(
    context: Context,
    launcher: ActivityResultLauncher<IntentSenderRequest>
) {
    val oneTapClient = Identity.getSignInClient(context)
    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(BuildConfig.GOOGLE_SIGNIN_SERVER_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    try {
        val result = oneTapClient.beginSignIn(signInRequest).await()
        val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
        launcher.launch(intentSenderRequest)
    } catch (e: Exception) {
        Log.e(TAG, "SignIn Error", e)
    }
}
