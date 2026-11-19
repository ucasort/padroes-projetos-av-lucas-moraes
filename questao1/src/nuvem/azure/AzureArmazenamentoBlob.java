package nuvem.azure;

import java.math.BigDecimal;

import nuvem.Armazenamento;

/** Abstract Factory - ConcreteProductB2 (Azure Blob). Construtor de pacote: so a AzureFactory instancia. */
class AzureArmazenamentoBlob implements Armazenamento {
    static final BigDecimal CUSTO_GB_MES = new BigDecimal("0.02");
    private final String id;
    private final int capacidadeGb;

    AzureArmazenamentoBlob(int numero, int capacidadeGb) {
        this.id = String.format("blob://%04d", numero);
        this.capacidadeGb = capacidadeGb;
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
    public int getCapacidadeGb() {
        return capacidadeGb;
    }

    @Override
    public BigDecimal getCustoPorGbMes() {
        return CUSTO_GB_MES;
    }
}
