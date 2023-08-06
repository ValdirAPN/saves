package br.com.saves.domain

import br.com.saves.data.repository.TransactionRepository
import br.com.saves.model.Transaction
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend operator fun invoke(transaction: Transaction) {
        transactionRepository.create(transaction)
    }
}