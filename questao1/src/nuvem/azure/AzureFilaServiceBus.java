package nuvem.azure;

import java.math.BigDecimal;

import nuvem.FilaMensagens;

/** Abstract Factory - ConcreteProductC2 (Azure Service Bus). Construtor de pacote: so a AzureFactory instancia. */
class AzureFilaServiceBus implements FilaMensagens {
    static final BigDecimal CUSTO_MENSAL = new BigDecimal("0.50");
    private final String id;

    AzureFilaServiceBus(int numero) {
        this.id = String.format("sb-%04d", numero);
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
    public BigDecimal getCustoMensal() {
        return CUSTO_MENSAL;
    }
}
