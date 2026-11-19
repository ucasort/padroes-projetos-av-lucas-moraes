package observador;

import leilao.Lance;
import leilao.Lote;

/** Observer - Observer: todo interessado em um lote implementa esta interface (RNF01). */
public interface ObservadorLance {
    void atualizar(Lote lote, Lance lance);
}
