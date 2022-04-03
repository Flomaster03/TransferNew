import kotlin.math.roundToInt

const val LIMIT_CARD_MOUNTH = 600_000_00
const val LIMIT_CARD_DAY = 150_000_00
const val LIMIT_VK_PAY_MONTH = 40_000_00
const val LIMIT_VK_PAY_TRANSFER = 15_000_00
const val TAX_FREE_CARD_MAX = 75_000_00
const val TAX_FREE_CARD_MIN = 300_00
const val COMMISSION_MAESTRO_MASTERCARD = 0.006
const val COMMISSION_VISA_MIR = 0.0075


fun main() {
    val typeOfCard = "Mastercard"
    val amountMonth = 0
    val amountPay = 1000_00

    val checkOfTransfer = check(typeOfCard, amountMonth, amountPay)
    if (checkOfTransfer == true) {
        separation(typeOfCard, amountMonth, amountPay)
    } else {
        println("Ваш лимит на перевод исчерпан")
    }

}

fun check(typeOfCard: String, amountMonth: Int, amountPay: Int): Boolean {
    return when (typeOfCard) {
        "VK Pay" -> if (amountMonth <= LIMIT_VK_PAY_MONTH && amountPay <= LIMIT_VK_PAY_TRANSFER) true else false
        "Mastercard", "Maestro", "Visa", "Mir" -> if (amountMonth + amountPay <= LIMIT_CARD_MOUNTH && amountPay <= LIMIT_CARD_DAY) true else false
        else -> error("Такой платежной системы не существует")
    }
}

fun calculation(typeOfCard: String, amountMonth: Int, amountPay: Int): Int {
    return when (typeOfCard) {
        "VK Pay" -> 0
        "Mastercard", "Maestro" -> if (amountMonth + amountPay <= TAX_FREE_CARD_MAX && amountMonth + amountPay >= TAX_FREE_CARD_MIN) 0 else (amountPay * COMMISSION_MAESTRO_MASTERCARD + 20_00).roundToInt()
        "Visa", "Mir" -> if (amountPay * COMMISSION_VISA_MIR > 35_00.0) (amountPay * COMMISSION_VISA_MIR).roundToInt() else 35_00
        else -> error("Такой платежной системы не существует")
    }
}

fun separation(typeOfCard: String, amountMonth: Int, amountPay: Int) {
    val commission = calculation(typeOfCard, amountMonth, amountPay)
    val commissionRuble = commission / 100
    val commissionPenny = commission % 100
    println("Комиссия за перевод составляет  $commissionRuble рублей $commissionPenny копеек")
}
