package nuvem.azure;

import java.util.concurrent.atomic.AtomicInteger;

import nuvem.Armazenamento;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import nuvem.ProvedorNuvemFactory;
import nuvem.TabelaPrecos;

/** Abstract Factory - ConcreteFactory2 (Azure). Unica classe que instancia os recursos Azure (RNF06). */
public class AzureFactory implements ProvedorNuvemFactory {
    // Numeracao sequencial por tipo de recurso (RF03), segura entre threads.
    private static final AtomicInteger SEQ_VM = new AtomicInteger();
    private static final AtomicInteger SEQ_ARMAZENAMENTO = new AtomicInteger();
    private static final AtomicInteger SEQ_FILA = new AtomicInteger();

    private static final TabelaPrecos PRECOS = new TabelaPrecos(
            AzureMaquinaVirtual.CUSTO_HORA, AzureArmazenamentoBlob.CUSTO_GB_MES, AzureFilaServiceBus.CUSTO_MENSAL);

    @Override
    public String getNomeProvedor() {
        return "Azure";
    }

    @Override
    public TabelaPrecos getTabelaPrecos() {
        return PRECOS;
    }

    @Override
    public MaquinaVirtual criarMaquinaVirtual() {
        return new AzureMaquinaVirtual(SEQ_VM.incrementAndGet());
    }

    @Override
    public Armazenamento criarArmazenamento(int capacidadeGb) {
        return new AzureArmazenamentoBlob(SEQ_ARMAZENAMENTO.incrementAndGet(), capacidadeGb);
    }

    @Override
    public FilaMensagens criarFilaMensagens() {
        return new AzureFilaServiceBus(SEQ_FILA.incrementAndGet());
    }
}
