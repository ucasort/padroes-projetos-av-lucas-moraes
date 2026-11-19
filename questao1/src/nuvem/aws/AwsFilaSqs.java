package nuvem.aws;

import java.math.BigDecimal;

import nuvem.FilaMensagens;

/** Abstract Factory - ConcreteProductC1 (Aws SQS). Construtor de pacote: so a AwsFactory instancia. */
class AwsFilaSqs implements FilaMensagens {
    static final BigDecimal CUSTO_MENSAL = new BigDecimal("0.40");
    private final String id;

    AwsFilaSqs(int numero) {
        this.id = String.format("sqs-%04d", numero);
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
    public BigDecimal getCustoMensal() {
        return CUSTO_MENSAL;
    }
}
