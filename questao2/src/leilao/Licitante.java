package leilao;

/** Quem pode dar lances. O lote conhece so esta interface, nunca a classe concreta do participante. */
public interface Licitante {
    String getNome();
}
