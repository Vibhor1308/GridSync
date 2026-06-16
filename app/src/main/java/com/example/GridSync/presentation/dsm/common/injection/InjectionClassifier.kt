package com.example.GridSync.presentation.dsm.common.injection

import java.math.BigDecimal

object InjectionClassifier {

    fun classifyInjectionType(
        deviation: BigDecimal
    ): InjectionType {

        return if (deviation > BigDecimal.ZERO) {
            InjectionType.OverInjection
        } else {
            InjectionType.UnderInjection
        }
    }
}