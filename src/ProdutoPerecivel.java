import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ProdutoPerecivel extends Produto {

    // constantes
    private static final double DESCONTO = 0.25;
    private static final int PRAZO_DESCONTO = 7;

    private LocalDate dataDeValidade;

    public ProdutoPerecivel(String desc, double precoCusto, double margemLucro, LocalDate dataDeValidade) {
        super(desc, precoCusto, margemLucro);
        if (dataDeValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de validade não pode ser anterior ao dia atual.");
        }

        this.dataDeValidade = dataDeValidade;
    }

    public double valorVenda() {
        double valorBase = super.valorDeVenda();
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), this.dataDeValidade);

        if (diasRestantes <= 0) {
            throw new IllegalArgumentException("Produto vencido.");
        }
        if (diasRestantes <= PRAZO_DESCONTO) {
            valorBase = valorBase - (valorBase * DESCONTO);
        }

        return valorBase;

    }

    @Override
    public String gerarDadosTexto() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataTexto = this.dataDeValidade.format(formatador);
        return String.format(java.util.Locale.US, "2;%s;%.2f;%.2f;%s", this.descricao, this.precoCusto,
                this.margemLucro, dataTexto);
    }
}
