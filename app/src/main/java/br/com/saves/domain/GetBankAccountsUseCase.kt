package br.com.saves.domain

import br.com.saves.data.repository.BankAccountRepository
import javax.inject.Inject

class GetBankAccountsUseCase @Inject constructor(
    private val bankAccountRepository: BankAccountRepository
) {

    operator fun invoke() = bankAccountRepository.getAll()
}