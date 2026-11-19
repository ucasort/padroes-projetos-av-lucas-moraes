package nuvem;

/**
 * Abstract Factory - AbstractFactory.
 * Um unico objeto cria os tres recursos de UM provedor, garantindo que um
 * ambiente nunca misture recursos de provedores diferentes (RNF01).
 */
public interface ProvedorNuvemFactory {
    String getNomeProvedor();

    TabelaPrecos getTabelaPrecos();

    MaquinaVirtual criarMaquinaVirtual();

    Armazenamento criarArmazenamento(int capacidadeGb);

    FilaMensagens criarFilaMensagens();
}
