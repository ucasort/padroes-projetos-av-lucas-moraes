package fatura;

import java.math.BigDecimal;

/** Decorator - ConcreteDecorator: embalagem especial, R$ 180,00 fixos. */
public class EmbalagemEspecial extends ServicoAdicional {
    private static final BigDecimal CUSTO = new BigDecimal("180.00");

    public EmbalagemEspecial(ItemFatura componente) {
        super(componente);
    }

    @Override
    protected String getDescricao() {
        return "Embalagem especial";
    }

    @Override
    protected BigDecimal getCusto() {
        return CUSTO;
    }
}
