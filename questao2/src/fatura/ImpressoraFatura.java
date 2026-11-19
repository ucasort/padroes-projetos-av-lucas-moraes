package fatura;

import util.Moeda;

/**
 * RF09: imprime a fatura do comprador. Recebe apenas o Component (ItemFatura):
 * nao verifica quais servicos foram contratados nem se existe algum (RNF04).
 */
public class ImpressoraFatura {

    public void imprimir(ItemFatura fatura) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n============ FATURA DO COMPRADOR ============\n");
        sb.append(String.format("Lote:           %s%n", fatura.getNomeLote()));
        sb.append(String.format("Comprador:      %s%n", fatura.getComprador()));
        sb.append(String.format("Valor de venda: %s%n", Moeda.formatar(fatura.getValorVenda())));
        sb.append("Servicos adicionais:\n");
        for (LinhaServico servico : fatura.getServicos()) {
            sb.append(String.format("  + %-48s %s%n", servico.descricao(), Moeda.formatar(servico.custo())));
        }
        sb.append(String.format("TOTAL A PAGAR:  %s%n", Moeda.formatar(fatura.getTotal())));
        sb.append("=============================================");
        System.out.println(sb);
    }
}
