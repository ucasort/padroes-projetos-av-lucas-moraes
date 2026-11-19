package fatura;

import java.math.BigDecimal;

/** Decorator - ConcreteDecorator: certificado de autenticidade, R$ 300,00 fixos. */
public class CertificadoAutenticidade extends ServicoAdicional {
    private static final BigDecimal CUSTO = new BigDecimal("300.00");

    public CertificadoAutenticidade(ItemFatura componente) {
        super(componente);
    }

    @Override
    protected String getDescricao() {
        return "Certificado de autenticidade";
    }

    @Override
    protected BigDecimal getCusto() {
        return CUSTO;
    }
}
