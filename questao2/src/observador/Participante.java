package observador;

import leilao.Lance;
import leilao.Licitante;
import leilao.Lote;
import util.Moeda;

/** Observer - ConcreteObserver: participante que da lances e recebe avisos (RF04). */
public class Participante implements ObservadorLance, Licitante {
    private final String nome;

    public Participante(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void atualizar(Lote lote, Lance lance) {
        System.out.println("   -> [" + nome + "] aviso: o lote '" + lote.getNome() + "' tem novo lance de "
                + Moeda.formatar(lance.valor()));
    }
}
