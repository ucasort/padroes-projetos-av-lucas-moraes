package provisionamento;

import java.math.BigDecimal;
import java.util.Optional;

import auditoria.Auditoria;
import auditoria.ResultadoProvisionamento;
import config.Configuracao;
import nuvem.ProvedorNuvemFactory;
import util.Moeda;

/**
 * Abstract Factory - Client. Monta ambientes conhecendo apenas a interface
 * ProvedorNuvemFactory: nao conhece Aws/Azure nem usa if/switch para escolher
 * o provedor (RNF02). A fabrica e recebida de fora, pelo construtor.
 * Configuracao e Auditoria sao obtidas pelos Singletons, sem parametros (RNF04/RNF05).
 */
public class Provisionador {
    private final ProvedorNuvemFactory fabrica;

    public Provisionador(ProvedorNuvemFactory fabrica) {
        this.fabrica = fabrica;
    }

    public Optional<Ambiente> provisionar(SolicitacaoAmbiente solicitacao) {
        Configuracao config = Configuracao.getInstancia();
        Auditoria auditoria = Auditoria.getInstancia();
        String provedor = fabrica.getNomeProvedor();

        // RF04/RF05: o custo e estimado pela tabela de precos, antes de criar qualquer recurso.
        BigDecimal custoEstimado = fabrica.getTabelaPrecos()
                .estimarCustoMensal(solicitacao.quantidadeVms(), solicitacao.gbArmazenamento());

        if (custoEstimado.compareTo(config.getOrcamentoMaximo()) > 0) {
            String motivo = "custo estimado " + Moeda.formatar(custoEstimado)
                    + " ultrapassa o orcamento de " + Moeda.formatar(config.getOrcamentoMaximo());
            auditoria.registrar(solicitacao.nomeAmbiente(), provedor, ResultadoProvisionamento.RECUSADO, motivo);
            System.out.println("\n--- Ambiente RECUSADO: " + solicitacao.nomeAmbiente()
                    + " (" + provedor + ") -> " + motivo + " ---");
            return Optional.empty();
        }

        Ambiente ambiente = Ambiente.montar(fabrica, solicitacao.nomeAmbiente(), config.getRegiaoPadrao(),
                solicitacao.quantidadeVms(), solicitacao.gbArmazenamento());
        System.out.print(ambiente.resumo());
        auditoria.registrar(ambiente.getNome(), provedor, ResultadoProvisionamento.ACEITO, null);
        return Optional.of(ambiente);
    }
}
