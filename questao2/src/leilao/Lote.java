package leilao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import comissao.RegraComissao;
import observador.ObservadorLance;
import util.Moeda;

/**
 * Lote em leilao.
 * - Observer: ConcreteSubject. Conhece os interessados so pela interface ObservadorLance (RNF01).
 * - Strategy: Context. Delega o calculo da comissao a uma RegraComissao trocavel em tempo de execucao (RNF02).
 */
public class Lote implements SujeitoLance {
    private static final BigDecimal INCREMENTO_MINIMO = new BigDecimal("1.05"); // 5% acima do lance atual

    private final String nome;
    private final Categoria categoria;
    private final BigDecimal precoInicial;
    private final BigDecimal precoReserva;
    private final List<Lance> lances = new ArrayList<>();
    private final List<ObservadorLance> observadores = new CopyOnWriteArrayList<>();

    private RegraComissao regraComissao;
    private EstadoLote estado = EstadoLote.ABERTO;
    private Lance lanceVencedor;
    private BigDecimal comissaoCobrada;

    public Lote(String nome, Categoria categoria, BigDecimal precoInicial, BigDecimal precoReserva) {
        this.nome = nome;
        this.categoria = categoria;
        this.precoInicial = precoInicial;
        this.precoReserva = precoReserva;
        this.regraComissao = categoria.getRegraPadrao();
    }

    // ---------------- Observer (Subject) ----------------

    @Override
    public void acompanhar(ObservadorLance observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    @Override
    public void deixarDeAcompanhar(ObservadorLance observador) {
        observadores.remove(observador);
    }

    /** RF03: avisa todos os que acompanham o lote, exceto quem deu o lance. */
    @Override
    public void notificarObservadores(Lance lance) {
        for (ObservadorLance observador : observadores) {
            if (observador != lance.licitante()) {
                observador.atualizar(this, lance);
            }
        }
    }

    // ---------------- Lances (RF01, RF02, RF05) ----------------

    public synchronized ResultadoLance darLance(Licitante licitante, BigDecimal valor) {
        if (estado != EstadoLote.ABERTO) {
            return ResultadoLance.recusado(licitante.getNome() + " ofereceu " + Moeda.formatar(valor)
                    + " em '" + nome + "', mas o lote ja foi encerrado (" + estado + ").");
        }
        BigDecimal minimo = getLanceMinimo();
        if (valor.compareTo(minimo) < 0) {
            return ResultadoLance.recusado(licitante.getNome() + " ofereceu " + Moeda.formatar(valor)
                    + " em '" + nome + "': o lance precisa ser pelo menos 5% maior que o lance atual de "
                    + Moeda.formatar(getLanceAtual()) + " (minimo " + Moeda.formatar(minimo) + ").");
        }
        Lance lance = new Lance(licitante, valor, LocalDateTime.now());
        lances.add(lance);
        notificarObservadores(lance);
        return ResultadoLance.aceito(licitante.getNome() + " deu lance de "
                + Moeda.formatar(valor) + " em '" + nome + "'.");
    }

    /** RF01: o lance atual e o preco inicial ate o primeiro lance. */
    public synchronized BigDecimal getLanceAtual() {
        return lances.isEmpty() ? precoInicial : lances.get(lances.size() - 1).valor();
    }

    public BigDecimal getLanceMinimo() {
        return getLanceAtual().multiply(INCREMENTO_MINIMO).setScale(2, RoundingMode.CEILING);
    }

    /** RF05: vendido ao maior lance se >= preco de reserva; senao, nao vendido. */
    public synchronized EstadoLote encerrar() {
        if (estado != EstadoLote.ABERTO) {
            return estado;
        }
        Lance maior = lances.isEmpty() ? null : lances.get(lances.size() - 1);
        if (maior != null && maior.valor().compareTo(precoReserva) >= 0) {
            estado = EstadoLote.VENDIDO;
            lanceVencedor = maior;
            // RF07: usa a regra vigente no momento do encerramento.
            comissaoCobrada = regraComissao.calcular(maior.valor());
        } else {
            estado = EstadoLote.NAO_VENDIDO;
        }
        return estado;
    }

    // ---------------- Strategy (Context) ----------------

    /** RF07: troca a regra de comissao em tempo de execucao, antes do encerramento. */
    public synchronized void setRegraComissao(RegraComissao novaRegra) {
        if (estado != EstadoLote.ABERTO) {
            throw new IllegalStateException("Lote '" + nome + "' ja encerrado: a regra de comissao nao pode mudar.");
        }
        this.regraComissao = novaRegra;
    }

    public RegraComissao getRegraComissao() {
        return regraComissao;
    }

    // ---------------- Consultas ----------------

    public String getNome() {
        return nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public BigDecimal getPrecoInicial() {
        return precoInicial;
    }

    public BigDecimal getPrecoReserva() {
        return precoReserva;
    }

    public synchronized List<Lance> getLances() {
        return List.copyOf(lances);
    }

    public synchronized int getQuantidadeLances() {
        return lances.size();
    }

    public EstadoLote getEstado() {
        return estado;
    }

    public Licitante getComprador() {
        return estado == EstadoLote.VENDIDO ? lanceVencedor.licitante() : null;
    }

    public BigDecimal getValorVenda() {
        return estado == EstadoLote.VENDIDO ? lanceVencedor.valor() : BigDecimal.ZERO;
    }

    public BigDecimal getComissaoCobrada() {
        return estado == EstadoLote.VENDIDO ? comissaoCobrada : BigDecimal.ZERO;
    }
}
