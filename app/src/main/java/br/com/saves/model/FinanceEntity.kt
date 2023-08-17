package br.com.saves.model

interface FinanceInstitution {
    fun getIconRes(): Int
    fun getIconTint(): Long?
    fun getIconBackground(): Long
}

interface FinanceEntity {
    val id: String
    val name: String
    val institution: FinanceInstitution
}