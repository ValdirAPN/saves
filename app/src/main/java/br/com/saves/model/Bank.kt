package br.com.saves.model

import androidx.annotation.DrawableRes
import br.com.saves.R

enum class Bank(@DrawableRes val icon: Int, val title: String) {
    WALLET(R.drawable.wallet, "default"),
    NUBANK(R.drawable.nubank, "Nubank"),
    BB(R.drawable.banco_do_brasil, "Banco do Brasil"),
    ITAU(R.drawable.itau, "Itaú"),
    C6(R.drawable.c6, "C6 Bank"),
    BRADESCO(R.drawable.bradesco, "Bradesco"),
    WILL(R.drawable.will, "Will Bank"),
    CITI(R.drawable.citi, "Citi Bank"),
    SANTANDER(R.drawable.santander, "Santander"),
    CAIXA(R.drawable.caixa_economica, "Caixa Econômica"),
    BTG(R.drawable.btg, "BTG Pactual"),
    INTER(R.drawable.inter, "Inter"),
    PAN(R.drawable.pan, "Banco Pan")
}