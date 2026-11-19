package fatura;

import java.math.BigDecimal;
import java.util.List;

/** Decorator - ConcreteComponent: a venda do lote, sem servicos adicionais. */
public class VendaLote implements ItemFatura {
    private final String nomeLote;
    private final String comprador;
    private final BigDecimal valorVenda;

    public VendaLote(String nomeLote, String comprador, BigDecimal valorVenda) {
        this.nomeLote = nomeLote;
        this.comprador = comprador;
        this.valorVenda = valorVenda;
    }

    @Override
    public String getNomeLote() {
        return nomeLote;
    }

    @Override
    public String getComprador() {
        return comprador;
    }

    @Override
    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    @Override
    public List<LinhaServico> getServicos() {
        return List.of();
    }

    @Override
    public BigDecimal getTotal() {
        return valorVenda;
    }
}
