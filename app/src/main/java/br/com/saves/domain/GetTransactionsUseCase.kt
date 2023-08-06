package br.com.saves.domain

import br.com.saves.data.repository.BankAccountRepository
import br.com.saves.data.repository.CreditCardRepository
import br.com.saves.data.repository.TransactionRepository
import br.com.saves.model.CreditCard
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    operator fun invoke() =
        transactionRepository.getAll()
}