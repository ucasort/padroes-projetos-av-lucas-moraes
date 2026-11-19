package comissao;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Strategy - ConcreteStrategy: Arte, 20% do valor. */
public class ComissaoArte implements RegraComissao {
    private static final BigDecimal PERCENTUAL = new BigDecimal("0.20");

    @Override
    public BigDecimal calcular(BigDecimal valorVenda) {
        return valorVenda.multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Arte: 20% do valor";
    }
}
