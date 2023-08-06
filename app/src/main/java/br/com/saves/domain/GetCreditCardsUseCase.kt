package br.com.saves.domain

import br.com.saves.data.repository.CreditCardRepository
import br.com.saves.model.CreditCard
import javax.inject.Inject

class GetCreditCardsUseCase @Inject constructor(
    private val creditCardRepository: CreditCardRepository
) {

    operator fun invoke() =
        creditCardRepository.getAll()
}