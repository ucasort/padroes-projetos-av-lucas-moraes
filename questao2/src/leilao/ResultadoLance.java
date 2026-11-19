package leilao;

/** Resposta a uma tentativa de lance: aceito ou recusado, com mensagem explicativa (RF02). */
public record ResultadoLance(boolean aceito, String mensagem) {

    static ResultadoLance aceito(String mensagem) {
        return new ResultadoLance(true, mensagem);
    }

    static ResultadoLance recusado(String mensagem) {
        return new ResultadoLance(false, mensagem);
    }

    @Override
    public String toString() {
        return (aceito ? "[ACEITO] " : "[RECUSADO] ") + mensagem;
    }
}
