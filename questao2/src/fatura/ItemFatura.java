package fatura;

import java.math.BigDecimal;
import java.util.List;

/**
 * Decorator - Component. Uma venda simples e uma venda com N servicos tem a mesma
 * interface, entao a impressao da fatura as trata da mesma forma (RNF04).
 */
public interface ItemFatura {
    String getNomeLote();

    String getComprador();

    BigDecimal getValorVenda();

    /** Servicos contratados, na ordem em que foram acrescentados. */
    List<LinhaServico> getServicos();

    BigDecimal getTotal();
}
