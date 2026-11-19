package fatura;

import java.math.BigDecimal;

/** Uma linha da fatura: descricao e custo de um servico contratado. */
public record LinhaServico(String descricao, BigDecimal custo) {
}
