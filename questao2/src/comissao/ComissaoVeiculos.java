package comissao;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Strategy - ConcreteStrategy: Veiculos, 8% do valor limitada a R$ 5.000,00. */
public class ComissaoVeiculos implements RegraComissao {
    private static final BigDecimal PERCENTUAL = new BigDecimal("0.08");
    private static final BigDecimal TETO = new BigDecimal("5000.00");

    @Override
    public BigDecimal calcular(BigDecimal valorVenda) {
        return valorVenda.multiply(PERCENTUAL).min(TETO).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Veiculos: 8% do valor, limitada a R$ 5.000,00";
    }
}
