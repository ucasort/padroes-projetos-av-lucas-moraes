package comissao;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Strategy - ConcreteStrategy: Eletronicos, 12% do valor com minimo de R$ 50,00. */
public class ComissaoEletronicos implements RegraComissao {
    private static final BigDecimal PERCENTUAL = new BigDecimal("0.12");
    private static final BigDecimal MINIMO = new BigDecimal("50.00");

    @Override
    public BigDecimal calcular(BigDecimal valorVenda) {
        return valorVenda.multiply(PERCENTUAL).max(MINIMO).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getDescricao() {
        return "Eletronicos: 12% do valor, minimo R$ 50,00";
    }
}
