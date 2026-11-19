package auditoria;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton (RNF05). Historico unico de auditoria, acessivel de qualquer classe por
 * getInstancia(). O registro e sincronizado: a sequencia, a data/hora e a insercao
 * acontecem dentro da mesma secao critica, entao execucoes simultaneas nao se
 * sobrescrevem e a ordem do historico e a ordem real em que os registros foram feitos.
 */
public final class Auditoria {
    private final List<RegistroAuditoria> historico = new ArrayList<>();

    private Auditoria() {
    }

    private static final class Holder {
        private static final Auditoria INSTANCIA = new Auditoria();
    }

    public static Auditoria getInstancia() {
        return Holder.INSTANCIA;
    }

    public synchronized RegistroAuditoria registrar(String ambiente, String provedor,
            ResultadoProvisionamento resultado, String motivo) {
        RegistroAuditoria registro = new RegistroAuditoria(historico.size() + 1, LocalDateTime.now(),
                ambiente, provedor, resultado, motivo);
        historico.add(registro);
        return registro;
    }

    /** Copia imutavel do historico, na ordem em que os registros foram feitos. */
    public synchronized List<RegistroAuditoria> getHistorico() {
        return List.copyOf(historico);
    }

    public void imprimirHistorico() {
        List<RegistroAuditoria> copia = getHistorico();
        StringBuilder sb = new StringBuilder("\n===== HISTORICO DE AUDITORIA (" + copia.size() + " registros) =====\n");
        copia.forEach(r -> sb.append(r).append('\n'));
        System.out.print(sb);
    }
}
