package nuvem.aws;

import java.math.BigDecimal;

import nuvem.Armazenamento;

/** Abstract Factory - ConcreteProductB1 (Aws S3). Construtor de pacote: so a AwsFactory instancia. */
class AwsArmazenamentoS3 implements Armazenamento {
    static final BigDecimal CUSTO_GB_MES = new BigDecimal("0.023");
    private final String id;
    private final int capacidadeGb;

    AwsArmazenamentoS3(int numero, int capacidadeGb) {
        this.id = String.format("s3://%04d", numero);
        this.capacidadeGb = capacidadeGb;
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
    public int getCapacidadeGb() {
        return capacidadeGb;
    }

    @Override
    public BigDecimal getCustoPorGbMes() {
        return CUSTO_GB_MES;
    }
}
