package nuvem.aws;

import java.math.BigDecimal;

import nuvem.MaquinaVirtual;

/** Abstract Factory - ConcreteProductA1 (Aws EC2). Construtor de pacote: so a AwsFactory instancia. */
class AwsMaquinaVirtual implements MaquinaVirtual {
    static final BigDecimal CUSTO_HORA = new BigDecimal("0.10");
    private final String id;

    AwsMaquinaVirtual(int numero) {
        this.id = String.format("i-%04d", numero);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getProvedor() {
        return "Aws";
    }

    @Override
    public BigDecimal getCustoPorHora() {
        return CUSTO_HORA;
    }
}
