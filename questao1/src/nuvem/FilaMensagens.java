package nuvem;

/** Abstract Factory - AbstractProductC: fila de mensagens (custo fixo mensal). */
public interface FilaMensagens extends RecursoNuvem {
    @Override
    default String getTipo() {
        return "Fila de mensagens";
    }
}
