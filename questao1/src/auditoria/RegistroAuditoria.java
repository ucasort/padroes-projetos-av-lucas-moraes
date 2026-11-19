package auditoria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Registro imutavel de auditoria (RF07). */
public record RegistroAuditoria(int sequencia, LocalDateTime dataHora, String ambiente, String provedor,
        ResultadoProvisionamento resultado, String motivo) {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");

    @Override
    public String toString() {
        String texto = String.format("#%d | %s | ambiente=%s | provedor=%s | %s",
                sequencia, FORMATO.format(dataHora), ambiente, provedor, resultado);
        return motivo == null ? texto : texto + " (motivo: " + motivo + ")";
    }
}
