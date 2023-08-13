package br.com.saves.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import br.com.saves.R
import br.com.saves.ui.theme.CategoryEight
import br.com.saves.ui.theme.CategoryFive
import br.com.saves.ui.theme.CategoryFour
import br.com.saves.ui.theme.CategoryOne
import br.com.saves.ui.theme.CategorySeven
import br.com.saves.ui.theme.CategorySix
import br.com.saves.ui.theme.CategoryThree
import br.com.saves.ui.theme.CategoryTwo

enum class Category(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val color: Color
) {
    SHOPPING(R.drawable.shoppingbag, R.string.shopping, CategoryOne),
    FOOD(R.drawable.forkknife, R.string.food, CategoryTwo),
    SUPERMARKET(R.drawable.shoppingcart, R.string.suppermarket, CategoryThree),
    CLOTHING(R.drawable.coathanger, R.string.clothing, CategoryFour),
    FUEL(R.drawable.gaspump, R.string.fuel, CategoryFive),
    LEISURE(R.drawable.swimmingpool, R.string.leisure, CategorySix),
    HEALTH(R.drawable.firstaid, R.string.health, CategorySeven),
    BEAUTY_SALON(R.drawable.scissors, R.string.beauty_salon, CategoryEight),
    EDUCATION(R.drawable.student, R.string.education, CategoryOne),
    PET(R.drawable.pawprint, R.string.pet, CategoryTwo),
    GYM(R.drawable.barbell, R.string.gym, CategoryThree),
    HOME(R.drawable.houseline, R.string.home, CategoryFour),
    FURNITURE(R.drawable.armchair, R.string.furniture, CategoryFive),
    CONSTRUCTION(R.drawable.hammer, R.string.construction, CategorySix),
    SALARY(R.drawable.coins, R.string.salary, CategorySeven),
    TRANSPORT(R.drawable.train, R.string.transport, CategoryEight),
    CREDIT_CARDS(R.drawable.creditcard, R.string.credit_cards, CategoryOne),
    GIFT(R.drawable.gift, R.string.gift, CategoryTwo),
    DONATION(R.drawable.handheart, R.string.donation, CategoryThree),
    TRIP(R.drawable.airplane, R.string.trip, CategoryFour),
    OTHER(R.drawable.sealquestion, R.string.other, CategoryFive),
}