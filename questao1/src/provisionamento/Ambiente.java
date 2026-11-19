package provisionamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import nuvem.Armazenamento;
import nuvem.FilaMensagens;
import nuvem.MaquinaVirtual;
import nuvem.ProvedorNuvemFactory;
import nuvem.RecursoNuvem;
import util.Moeda;

/**
 * Ambiente montado. O construtor e privado e o unico jeito de cria-lo e a partir de
 * UMA unica fabrica: assim e impossivel juntar recursos de provedores diferentes (RNF01).
 */
public final class Ambiente {
    private final String nome;
    private final String provedor;
    private final String regiao;
    private final List<MaquinaVirtual> maquinas;
    private final Armazenamento armazenamento;
    private final FilaMensagens fila;

    private Ambiente(String nome, String provedor, String regiao, List<MaquinaVirtual> maquinas,
            Armazenamento armazenamento, FilaMensagens fila) {
        this.nome = nome;
        this.provedor = provedor;
        this.regiao = regiao;
        this.maquinas = List.copyOf(maquinas);
        this.armazenamento = armazenamento;
        this.fila = fila;
    }

    /** Cria os tres tipos de recurso usando somente a fabrica recebida. */
    static Ambiente montar(ProvedorNuvemFactory fabrica, String nome, String regiao, int quantidadeVms, int gb) {
        List<MaquinaVirtual> maquinas = new ArrayList<>();
        for (int i = 0; i < quantidadeVms; i++) {
            maquinas.add(fabrica.criarMaquinaVirtual());
        }
        return new Ambiente(nome, fabrica.getNomeProvedor(), regiao, maquinas,
                fabrica.criarArmazenamento(gb), fabrica.criarFilaMensagens());
    }

    public List<RecursoNuvem> getRecursos() {
        List<RecursoNuvem> recursos = new ArrayList<>(maquinas);
        recursos.add(armazenamento);
        recursos.add(fila);
        return recursos;
    }

    public BigDecimal getCustoMensal() {
        return getRecursos().stream().map(RecursoNuvem::getCustoMensal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getNome() {
        return nome;
    }

    public String getProvedor() {
        return provedor;
    }

    public String getRegiao() {
        return regiao;
    }

    /** RF06: nome, provedor, regiao, identificador de cada recurso e custo mensal estimado. */
    public String resumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- Ambiente provisionado: ").append(nome).append(" ---\n");
        sb.append("Provedor: ").append(provedor).append('\n');
        sb.append("Regiao:   ").append(regiao).append('\n');
        sb.append("Recursos:\n");
        for (RecursoNuvem r : getRecursos()) {
            sb.append(String.format("  - %-12s %s%n", r.getId(), r.getTipo()));
        }
        sb.append("Custo mensal estimado: ").append(Moeda.formatar(getCustoMensal())).append('\n');
        return sb.toString();
    }
}
