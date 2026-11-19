package fatura;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Decorator - ConcreteDecorator: seguro de transporte, 1,5% do valor de venda. */
public class SeguroTransporte extends ServicoAdicional {
    private static final BigDecimal PERCENTUAL = new BigDecimal("0.015");

    public SeguroTransporte(ItemFatura componente) {
        super(componente);
    }

    @Override
    protected String getDescricao() {
        return "Seguro de transporte (1,5% do valor de venda)";
    }

    @Override
    protected BigDecimal getCusto() {
        return getValorVenda().multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
    }
}
