package fatura;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Decorator - Decorator (abstrato). Envolve um ItemFatura e acrescenta a descricao
 * e o custo do seu servico. Servicos podem ser empilhados em qualquer ordem e
 * quantidade, sem classes de combinacao prontas (RNF03).
 */
public abstract class ServicoAdicional implements ItemFatura {
    protected final ItemFatura componente;

    protected ServicoAdicional(ItemFatura componente) {
        this.componente = componente;
    }

    protected abstract String getDescricao();

    protected abstract BigDecimal getCusto();

    @Override
    public String getNomeLote() {
        return componente.getNomeLote();
    }

    @Override
    public String getComprador() {
        return componente.getComprador();
    }

    @Override
    public BigDecimal getValorVenda() {
        return componente.getValorVenda();
    }

    @Override
    public List<LinhaServico> getServicos() {
        List<LinhaServico> servicos = new ArrayList<>(componente.getServicos());
        servicos.add(new LinhaServico(getDescricao(), getCusto()));
        return servicos;
    }

    @Override
    public BigDecimal getTotal() {
        return componente.getTotal().add(getCusto());
    }
}
