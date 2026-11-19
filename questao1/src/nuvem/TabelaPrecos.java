package nuvem;

import java.math.BigDecimal;

/**
 * Precos de um provedor. Permite estimar o custo (RF04) ANTES de criar qualquer
 * recurso, para que uma solicitacao acima do orcamento seja recusada sem criar nada (RF05).
 */
public record TabelaPrecos(BigDecimal custoHoraVm, BigDecimal custoGbMes, BigDecimal custoFilaMes) {

    /** RF04: maquinas x custo/hora x 730 + GB x custo/GB + custo fixo da fila. */
    public BigDecimal estimarCustoMensal(int quantidadeVms, int gbArmazenamento) {
        BigDecimal vms = custoHoraVm.multiply(BigDecimal.valueOf(quantidadeVms)).multiply(MaquinaVirtual.HORAS_POR_MES);
        BigDecimal armazenamento = custoGbMes.multiply(BigDecimal.valueOf(gbArmazenamento));
        return vms.add(armazenamento).add(custoFilaMes);
    }
}
