package observador;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import leilao.Lance;
import leilao.Lote;
import util.Moeda;

/** Observer - ConcreteObserver: registra todos os lances aceitos com data e hora (RF04). */
public class Auditor implements ObservadorLance {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");
    private final List<String> registros = new ArrayList<>();

    @Override
    public synchronized void atualizar(Lote lote, Lance lance) {
        String registro = FORMATO.format(lance.dataHora()) + " | " + lote.getNome() + " | "
                + lance.licitante().getNome() + " | " + Moeda.formatar(lance.valor());
        registros.add(registro);
        System.out.println("   -> [Auditor] registrado: " + registro);
    }

    public synchronized List<String> getRegistros() {
        return List.copyOf(registros);
    }

    public void imprimirRegistros() {
        System.out.println("\n========== REGISTROS DO AUDITOR ==========");
        getRegistros().forEach(r -> System.out.println("  " + r));
        System.out.println("==========================================");
    }
}
