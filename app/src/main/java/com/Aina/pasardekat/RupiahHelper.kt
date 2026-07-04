package com.Aina.pasardekat

import java.text.NumberFormat
import java.util.Locale

object RupiahHelper {

    fun format(
        nominal: Int
    ): String {

        return NumberFormat
            .getCurrencyInstance(
                Locale("in", "ID")
            )
            .format(nominal)
            .replace(",00", "")
    }
}