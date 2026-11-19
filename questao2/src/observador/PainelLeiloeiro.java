package observador;

import leilao.Lance;
import leilao.Lote;
import util.Moeda;

/** Observer - ConcreteObserver: painel que exibe o maior lance e o total de lances do lote (RF04). */
public class PainelLeiloeiro implements ObservadorLance {

    @Override
    public void atualizar(Lote lote, Lance lance) {
        System.out.println("   -> [Painel do leiloeiro] '" + lote.getNome() + "' | maior lance: "
                + Moeda.formatar(lote.getLanceAtual()) + " (" + lance.licitante().getNome() + ")"
                + " | total de lances: " + lote.getQuantidadeLances());
    }
}
