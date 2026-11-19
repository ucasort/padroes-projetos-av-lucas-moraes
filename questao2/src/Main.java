import java.math.BigDecimal;

import comissao.ComissaoArte;
import comissao.ComissaoEletronicos;
import comissao.ComissaoImoveis;
import comissao.ComissaoVeiculos;
import fatura.CertificadoAutenticidade;
import fatura.EmbalagemEspecial;
import fatura.EntregaExpressa;
import fatura.ImpressoraFatura;
import fatura.ItemFatura;
import fatura.SeguroTransporte;
import fatura.VendaLote;
import leilao.AcertoVendedor;
import leilao.Categoria;
import leilao.Lote;
import observador.Auditor;
import observador.PainelLeiloeiro;
import observador.Participante;
import util.Moeda;

/**
 * RF11: dois lotes de categorias distintas, tres participantes, um painel e um auditor;
 * lances aceitos e recusados, saida de um participante, troca de regra de comissao,
 * um lote vendido, um nao vendido e uma fatura com servicos (um deles contratado duas vezes).
 */
public class Main {

    private static BigDecimal reais(String valor) {
        return new BigDecimal(valor);
    }

    /** Mostra a tentativa de lance, os avisos gerados (Observer) e o resultado. */
    private static void lance(Lote lote, Participante participante, String valor) {
        System.out.println(">> " + participante.getNome() + " oferece " + Moeda.formatar(reais(valor))
                + " em '" + lote.getNome() + "'");
        System.out.println("   " + lote.darLance(participante, reais(valor)));
    }

    private static void secao(String titulo) {
        System.out.println("\n##### " + titulo + " #####");
    }

    public static void main(String[] args) {
        // Categorias (cada uma com sua Strategy de comissao padrao)
        Categoria arte = new Categoria("Arte", new ComissaoArte());
        Categoria veiculos = new Categoria("Veiculos", new ComissaoVeiculos());
        Categoria imoveis = new Categoria("Imoveis", new ComissaoImoveis());
        Categoria eletronicos = new Categoria("Eletronicos", new ComissaoEletronicos());

        Lote quadro = new Lote("Quadro Por do Sol no Cerrado", arte, reais("10000.00"), reais("15000.00"));
        Lote carro = new Lote("Sedan 2020 (60 mil km)", veiculos, reais("80000.00"), reais("120000.00"));

        // Interessados (Observers)
        Participante ana = new Participante("Ana");
        Participante bruno = new Participante("Bruno");
        Participante carla = new Participante("Carla");
        PainelLeiloeiro painel = new PainelLeiloeiro();
        Auditor auditor = new Auditor();

        quadro.acompanhar(ana);
        quadro.acompanhar(bruno);
        quadro.acompanhar(carla);
        quadro.acompanhar(painel);
        quadro.acompanhar(auditor);

        carro.acompanhar(ana);
        carro.acompanhar(bruno);
        carro.acompanhar(painel);
        carro.acompanhar(auditor);

        secao("Lances no lote de Arte");
        lance(quadro, ana, "10500.00");                  // aceito (exatamente 5%)
        lance(quadro, bruno, "10800.00"); // recusado (< 5%)
        lance(quadro, bruno, "12000.00");                // aceito

        secao("Carla deixa de acompanhar o quadro");
        quadro.deixarDeAcompanhar(carla);
        lance(quadro, ana, "13000.00");                  // Carla nao e mais avisada
        lance(quadro, carla, "17000.00");                // Carla ainda pode dar lance

        secao("Lances no lote de Veiculos");
        lance(carro, bruno, "85000.00");
        lance(carro, ana, "88000.00"); // recusado (minimo 89.250)
        lance(carro, ana, "95000.00");
        lance(carro, bruno, "100000.00");

        secao("Campanha: lote de Arte passa a usar a regra de Eletronicos");
        System.out.println("Regra antes:  " + quadro.getRegraComissao().getDescricao());
        quadro.setRegraComissao(eletronicos.getRegraPadrao());
        System.out.println("Regra depois: " + quadro.getRegraComissao().getDescricao());

        secao("Encerramento dos lotes");
        System.out.println("'" + quadro.getNome() + "' -> " + quadro.encerrar()
                + " (maior lance " + Moeda.formatar(quadro.getLanceAtual())
                + ", reserva " + Moeda.formatar(quadro.getPrecoReserva()) + ")");
        System.out.println("'" + carro.getNome() + "' -> " + carro.encerrar()
                + " (maior lance " + Moeda.formatar(carro.getLanceAtual())
                + ", reserva " + Moeda.formatar(carro.getPrecoReserva()) + ")");
        lance(carro, ana, "130000.00"); // recusado: lote encerrado

        secao("Fatura do comprador (Decorator)");
        ItemFatura fatura = new VendaLote(quadro.getNome(), quadro.getComprador().getNome(), quadro.getValorVenda());
        fatura = new SeguroTransporte(fatura);
        fatura = new CertificadoAutenticidade(fatura);
        fatura = new EmbalagemEspecial(fatura);
        fatura = new EmbalagemEspecial(fatura);   // contratado duas vezes
        fatura = new EntregaExpressa(fatura);
        new ImpressoraFatura().imprimir(fatura);

        secao("Acerto do vendedor (Strategy vigente no encerramento)");
        new AcertoVendedor(quadro).imprimir();
        System.out.println("(Com a regra original de Arte a comissao seria "
                + Moeda.formatar(arte.getRegraPadrao().calcular(quadro.getValorVenda())) + ")");
        System.out.println("'" + carro.getNome() + "' nao foi vendido: sem fatura e sem acerto.");

        auditor.imprimirRegistros();

        // Categoria criada so para mostrar que existe; nenhuma alteracao em Lote foi necessaria.
        System.out.println("\nCategorias disponiveis: " + arte.getNome() + ", " + veiculos.getNome() + ", "
                + imoveis.getNome() + ", " + eletronicos.getNome());
    }
}
