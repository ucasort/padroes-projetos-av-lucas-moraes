package nuvem.azure;

import java.math.BigDecimal;

import nuvem.MaquinaVirtual;

/** Abstract Factory - ConcreteProductA2 (Azure VM). Construtor de pacote: so a AzureFactory instancia. */
class AzureMaquinaVirtual implements MaquinaVirtual {
    static final BigDecimal CUSTO_HORA = new BigDecimal("0.12");
    private final String id;

    AzureMaquinaVirtual(int numero) {
        this.id = String.format("vm-%04d", numero);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getProvedor() {
        return "Azure";
    }

    @Override
    public BigDecimal getCustoPorHora() {
        return CUSTO_HORA;
    }
}
