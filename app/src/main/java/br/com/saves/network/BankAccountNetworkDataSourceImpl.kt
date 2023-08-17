package br.com.saves.network

import android.util.Log
import br.com.saves.model.Bank
import br.com.saves.network.model.NetworkBankAccount
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val TAG = "BankAccountNetworkDataSourceImpl"

class BankAccountNetworkDataSourceImpl @Inject constructor() : BankAccountNetworkDataSource {
    private val db: DocumentReference? by lazy {
        Firebase.auth.currentUser?.uid?.let { uid ->
            Firebase.firestore.collection("users").document(uid)
        }
    }

    override suspend fun create(bankAccount: NetworkBankAccount) {
        db?.update("bankAccounts", FieldValue.arrayUnion(bankAccount))
            ?.addOnSuccessListener {
                Log.d(TAG, "New account successfully added! $bankAccount")
            }
            ?.addOnFailureListener { e ->
                Log.w(TAG, "Failed to push new bank account", e)
            }
    }

    override suspend fun get(): Flow<List<NetworkBankAccount>> = callbackFlow {
        if (db == null) trySendBlocking(emptyList())

        val listener = db?.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                trySendBlocking(emptyList())
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val bankAccounts = (snapshot.get("bankAccounts") as List<HashMap<String, *>>)
                    .map { mapToNetworkBankAccount(it) }
                trySendBlocking(bankAccounts)
            } else {
                trySendBlocking(emptyList())
            }
        }

        awaitClose { listener?.remove() }
    }

    private fun mapToNetworkBankAccount(hashMap: HashMap<String, *>): NetworkBankAccount {
        val id = hashMap["id"] ?: throw Exception("No id found")
        val bankValue = hashMap["bank"] ?: throw Exception("No bank found")
        val name = hashMap["name"] ?: throw Exception("No name found")
        val amount = hashMap["balance"] ?: throw Exception("No balance found")

        val bank = if (bankValue.toString().isNotBlank()) {
            Bank.valueOf(bankValue.toString())
        } else Bank.DEFAULT

        return NetworkBankAccount(id.toString(), bank, name.toString(), amount.toString().toDouble())
    }
}