package nuvem;

import java.math.BigDecimal;

/** Abstract Factory - interface comum a todos os produtos (recursos de nuvem). */
public interface RecursoNuvem {
    String getId();

    String getProvedor();

    BigDecimal getCustoMensal();

    String getTipo();
}
