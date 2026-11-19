package comissao;

import java.math.BigDecimal;

/** Strategy - Strategy: regra de comissao cobrada do vendedor sobre o valor de venda (RF06). */
public interface RegraComissao {
    BigDecimal calcular(BigDecimal valorVenda);

    String getDescricao();
}
