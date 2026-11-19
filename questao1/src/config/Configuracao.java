package config;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton (RNF04). O arquivo config.properties e lido uma unica vez, de forma
 * preguicosa e thread-safe (idiom "Initialization-on-demand holder"). Todas as
 * classes obtem a mesma instancia por getInstancia(), sem receber a configuracao por parametro.
 */
public final class Configuracao {
    private static final AtomicInteger LEITURAS = new AtomicInteger();

    private final String regiaoPadrao;
    private final BigDecimal orcamentoMaximo;

    private Configuracao() {
        Properties props = carregarArquivo();
        this.regiaoPadrao = props.getProperty("regiao.padrao").trim();
        this.orcamentoMaximo = new BigDecimal(props.getProperty("orcamento.maximo").trim());
        LEITURAS.incrementAndGet();
    }

    /** A JVM garante que a classe Holder e inicializada uma unica vez, mesmo com varias threads. */
    private static final class Holder {
        private static final Configuracao INSTANCIA = new Configuracao();
    }

    public static Configuracao getInstancia() {
        return Holder.INSTANCIA;
    }

    public String getRegiaoPadrao() {
        return regiaoPadrao;
    }

    public BigDecimal getOrcamentoMaximo() {
        return orcamentoMaximo;
    }

    /** Quantas vezes o arquivo foi lido (deve ser sempre 1). */
    public static int getQuantidadeLeituras() {
        return LEITURAS.get();
    }

    private static Properties carregarArquivo() {
        List<Path> candidatos = List.of(
                Path.of(System.getProperty("config", "config.properties")),
                Path.of("src", "config.properties"),
                Path.of("questao1", "src", "config.properties"));
        for (Path caminho : candidatos) {
            if (Files.isRegularFile(caminho)) {
                try (InputStream in = Files.newInputStream(caminho)) {
                    Properties props = new Properties();
                    props.load(in);
                    System.out.println("[Configuracao] arquivo lido: " + caminho);
                    return props;
                } catch (IOException e) {
                    throw new UncheckedIOException("Erro ao ler " + caminho, e);
                }
            }
        }
        throw new IllegalStateException("config.properties nao encontrado. Execute a partir da pasta questao1.");
    }
}
