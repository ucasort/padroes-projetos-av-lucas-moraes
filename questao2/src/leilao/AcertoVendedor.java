package leilao;

import java.math.BigDecimal;

import util.Moeda;

/** RF10: acerto do vendedor (valor de venda, comissao cobrada e valor liquido). */
public class AcertoVendedor {
    private final Lote lote;

    public AcertoVendedor(Lote lote) {
        if (lote.getEstado() != EstadoLote.VENDIDO) {
            throw new IllegalStateException("So ha acerto para lotes vendidos: '" + lote.getNome() + "'");
        }
        this.lote = lote;
    }

    public BigDecimal getValorLiquido() {
        return lote.getValorVenda().subtract(lote.getComissaoCobrada());
    }

    public void imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n========== ACERTO DO VENDEDOR ==========\n");
        sb.append(String.format("Lote:              %s (%s)%n", lote.getNome(), lote.getCategoria().getNome()));
        sb.append(String.format("Valor de venda:    %s%n", Moeda.formatar(lote.getValorVenda())));
        sb.append(String.format("Comissao cobrada:  %s  [regra: %s]%n",
                Moeda.formatar(lote.getComissaoCobrada()), lote.getRegraComissao().getDescricao()));
        sb.append(String.format("Liquido a repassar: %s%n", Moeda.formatar(getValorLiquido())));
        sb.append("========================================");
        System.out.println(sb);
    }
}
