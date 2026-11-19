package leilao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** RF02: lance aceito, registrado com o participante e o valor. */
public record Lance(Licitante licitante, BigDecimal valor, LocalDateTime dataHora) {
}
