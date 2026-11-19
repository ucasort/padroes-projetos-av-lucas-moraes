package nuvem;

import java.math.BigDecimal;

/** Abstract Factory - AbstractProductB: armazenamento de objetos. */
public interface Armazenamento extends RecursoNuvem {
    int getCapacidadeGb();

    BigDecimal getCustoPorGbMes();

    @Override
    default BigDecimal getCustoMensal() {
        return getCustoPorGbMes().multiply(BigDecimal.valueOf(getCapacidadeGb()));
    }

    @Override
    default String getTipo() {
        return "Armazenamento (" + getCapacidadeGb() + " GB)";
    }
}
