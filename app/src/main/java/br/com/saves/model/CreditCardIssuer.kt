package br.com.saves.model

import androidx.annotation.DrawableRes
import br.com.saves.R

enum class CreditCardIssuer(
    @DrawableRes val icon: Int,
    val title: String,
    val background: Long,
    val foreground: Long?
) : FinanceInstitution {
    DEFAULT(R.drawable.creditcard, "default", background = 0xFF242424, foreground = 0xFFFFFFFF),
    NUBANK(R.drawable.nubank, "Nubank", background = 0xFF820AD1, foreground = 0xFFFFFFFF),
    BB(R.drawable.banco_do_brasil, "Banco do Brasil", background = 0xFF0061AA, foreground = 0xFFFFFFFF),
    ITAU(R.drawable.itau, "Itaú", background = 0xFFFFF212, foreground = null),
    C6(R.drawable.c6, "C6 Bank", background = 0xFF242424, foreground = 0xFFFFFFFF),
    BRADESCO(R.drawable.bradesco, "Bradesco", background = 0xFFCC092F, foreground = 0xFFFFFFFF),
    WILL(R.drawable.will, "Will Bank", background = 0xFFFFD900, foreground = 0xFF000000),
    CITI(R.drawable.citi, "Citi Bank", background = 0xFF003B70, foreground = null),
    SANTANDER(R.drawable.santander, "Santander", background = 0xFFEA1D25, foreground = 0xFFFFFFFF),
    CAIXA(R.drawable.caixa_economica, "Caixa Econômica", background = 0xFF0070AF, foreground = null),
    BTG(R.drawable.btg, "BTG Pactual", background = 0xFF001E61, foreground = 0xFFFFFFFF),
    INTER(R.drawable.inter, "Inter", background = 0xFFFF7A00, foreground = 0xFFFFFFFF),
    PAN(R.drawable.pan, "Banco Pan", background = 0xFF414141, foreground = 0xFFFFFFFF);

    override fun getIconRes() = this.icon
    override fun getIconTint() = this.foreground
    override fun getIconBackground() = this.background
}