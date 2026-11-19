package leilao;

import observador.ObservadorLance;

/** Observer - Subject: permite acompanhar e deixar de acompanhar um lote (RF03). */
public interface SujeitoLance {
    void acompanhar(ObservadorLance observador);

    void deixarDeAcompanhar(ObservadorLance observador);

    void notificarObservadores(Lance lance);
}
