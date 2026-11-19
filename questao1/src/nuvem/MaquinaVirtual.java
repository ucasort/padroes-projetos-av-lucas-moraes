package nuvem;

import java.math.BigDecimal;

/** Abstract Factory - AbstractProductA: maquina virtual. */
public interface MaquinaVirtual extends RecursoNuvem {
    BigDecimal HORAS_POR_MES = new BigDecimal("730");

    BigDecimal getCustoPorHora();

    @Override
    default BigDecimal getCustoMensal() {
        return getCustoPorHora().multiply(HORAS_POR_MES);
    }

    @Override
    default String getTipo() {
        return "Maquina virtual";
    }
}
