package fatura;

import java.math.BigDecimal;

/** Decorator - ConcreteDecorator: entrega expressa, R$ 450,00 fixos. */
public class EntregaExpressa extends ServicoAdicional {
    private static final BigDecimal CUSTO = new BigDecimal("450.00");

    public EntregaExpressa(ItemFatura componente) {
        super(componente);
    }

    @Override
    protected String getDescricao() {
        return "Entrega expressa";
    }

    @Override
    protected BigDecimal getCusto() {
        return CUSTO;
    }
}
