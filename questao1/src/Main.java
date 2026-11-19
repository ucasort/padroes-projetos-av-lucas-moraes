import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import auditoria.Auditoria;
import config.Configuracao;
import nuvem.ProvedorNuvemFactory;
import nuvem.aws.AwsFactory;
import nuvem.azure.AzureFactory;
import provisionamento.Provisionador;
import provisionamento.SolicitacaoAmbiente;
import util.Moeda;

/**
 * RF08: quatro provisionamentos (dois Aws, um Azure e um recusado por orcamento),
 * todos disparados ao mesmo tempo, cada um em sua propria thread.
 */
public class Main {

    /**
     * Unico ponto que escolhe o provedor. Para incluir um novo provedor (ex.: Gcp)
     * basta criar as classes novas e acrescentar uma linha aqui (RNF03).
     */
    static ProvedorNuvemFactory escolherProvedor(String provedor) {
        return switch (provedor.toUpperCase()) {
            case "AWS" -> new AwsFactory();
            case "AZURE" -> new AzureFactory();
            default -> throw new IllegalArgumentException("Provedor desconhecido: " + provedor);
        };
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Provisionamento Multinuvem (simulado em memoria) ===");
        Configuracao config = Configuracao.getInstancia();
        System.out.println("Regiao padrao: " + config.getRegiaoPadrao()
                + " | Orcamento maximo por ambiente: " + Moeda.formatar(config.getOrcamentoMaximo()));

        List<SolicitacaoAmbiente> solicitacoes = List.of(
                new SolicitacaoAmbiente("loja-producao", "Aws", 4, 500),
                new SolicitacaoAmbiente("loja-homologacao", "Aws", 2, 100),
                new SolicitacaoAmbiente("erp-producao", "Azure", 3, 200),
                new SolicitacaoAmbiente("data-lake", "Azure", 10, 5000)); // ultrapassa o orcamento

        CountDownLatch largada = new CountDownLatch(1);
        List<Thread> threads = new ArrayList<>();
        for (SolicitacaoAmbiente s : solicitacoes) {
            Provisionador provisionador = new Provisionador(escolherProvedor(s.provedor()));
            Thread t = new Thread(() -> {
                try {
                    largada.await(); // todas as threads comecam juntas
                    provisionador.provisionar(s);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "provisionamento-" + s.nomeAmbiente());
            threads.add(t);
            t.start();
        }
        largada.countDown();
        for (Thread t : threads) {
            t.join();
        }

        Auditoria.getInstancia().imprimirHistorico();
        System.out.println("Registros no historico: " + Auditoria.getInstancia().getHistorico().size()
                + " | Leituras do config.properties: " + Configuracao.getQuantidadeLeituras());
    }
}
