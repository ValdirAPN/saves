package br.com.saves.domain

import br.com.saves.data.repository.CreditCardRepository
import br.com.saves.model.CreditCard
import javax.inject.Inject

class CreateCreditCardUseCase @Inject constructor(
    private val creditCardRepository: CreditCardRepository
) {

    suspend operator fun invoke(creditCard: CreditCard) {
        creditCardRepository.create(creditCard)
    }
}