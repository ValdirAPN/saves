package br.com.saves.domain

import br.com.saves.data.repository.BankAccountRepository
import br.com.saves.data.repository.CreditCardRepository
import br.com.saves.model.BankAccount
import br.com.saves.model.CreditCard
import javax.inject.Inject

class CreateBankAccountUseCase @Inject constructor(
    private val bankAccountRepository: BankAccountRepository
) {

    suspend operator fun invoke(bankAccount: BankAccount) {
        bankAccountRepository.create(bankAccount)
    }
}