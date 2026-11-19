package provisionamento;

/** RF03: nome do ambiente, provedor, quantidade de maquinas virtuais e GB de armazenamento. */
public record SolicitacaoAmbiente(String nomeAmbiente, String provedor, int quantidadeVms, int gbArmazenamento) {
    public SolicitacaoAmbiente {
        if (quantidadeVms < 1 || gbArmazenamento < 1) {
            throw new IllegalArgumentException("Quantidade de VMs e de GB devem ser positivas");
        }
    }
}
