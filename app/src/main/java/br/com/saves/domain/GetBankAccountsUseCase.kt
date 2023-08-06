package br.com.saves.domain

import br.com.saves.data.repository.BankAccountRepository
import br.com.saves.data.repository.CreditCardRepository
import br.com.saves.model.CreditCard
import javax.inject.Inject

class GetBankAccountsUseCase @Inject constructor(
    private val bankAccountRepository: BankAccountRepository
) {

    operator fun invoke() =
        bankAccountRepository.getAll()
}