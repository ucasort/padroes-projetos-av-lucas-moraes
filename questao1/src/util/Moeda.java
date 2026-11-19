package util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/** Utilitario de formatacao de valores em reais. */
public final class Moeda {
    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private Moeda() {
    }

    public static String formatar(BigDecimal valor) {
        return String.format(PT_BR, "R$ %,.2f", valor.setScale(2, RoundingMode.HALF_UP));
    }
}
