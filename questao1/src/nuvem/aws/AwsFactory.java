package nuvem.aws;

import java.util.concurrent.atomic.AtomicInteger;

import nuvem.Armazenamento;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import nuvem.ProvedorNuvemFactory;
import nuvem.TabelaPrecos;

/** Abstract Factory - ConcreteFactory1 (Aws). Unica classe que instancia os recursos Aws (RNF06). */
public class AwsFactory implements ProvedorNuvemFactory {
    // Numeracao sequencial por tipo de recurso (RF03), segura entre threads.
    private static final AtomicInteger SEQ_VM = new AtomicInteger();
    private static final AtomicInteger SEQ_ARMAZENAMENTO = new AtomicInteger();
    private static final AtomicInteger SEQ_FILA = new AtomicInteger();

    private static final TabelaPrecos PRECOS = new TabelaPrecos(
            AwsMaquinaVirtual.CUSTO_HORA, AwsArmazenamentoS3.CUSTO_GB_MES, AwsFilaSqs.CUSTO_MENSAL);

    @Override
    public String getNomeProvedor() {
        return "Aws";
    }

    @Override
    public TabelaPrecos getTabelaPrecos() {
        return PRECOS;
    }

    @Override
    public MaquinaVirtual criarMaquinaVirtual() {
        return new AwsMaquinaVirtual(SEQ_VM.incrementAndGet());
    }

    @Override
    public Armazenamento criarArmazenamento(int capacidadeGb) {
        return new AwsArmazenamentoS3(SEQ_ARMAZENAMENTO.incrementAndGet(), capacidadeGb);
    }

    @Override
    public FilaMensagens criarFilaMensagens() {
        return new AwsFilaSqs(SEQ_FILA.incrementAndGet());
    }
}
