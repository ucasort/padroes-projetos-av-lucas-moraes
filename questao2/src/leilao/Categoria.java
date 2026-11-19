package leilao;

import comissao.RegraComissao;

/**
 * Categoria de um lote, com a sua regra de comissao padrao (Strategy).
 * Nova categoria = nova classe de regra + new Categoria("Nome", new RegraNova()),
 * sem alterar nenhuma classe existente (RNF02).
 */
public final class Categoria {
    private final String nome;
    private final RegraComissao regraPadrao;

    public Categoria(String nome, RegraComissao regraPadrao) {
        this.nome = nome;
        this.regraPadrao = regraPadrao;
    }

    public String getNome() {
        return nome;
    }

    public RegraComissao getRegraPadrao() {
        return regraPadrao;
    }
}
