import dominio.Jogo;
import dominio.PosicaoTabela;
import dominio.Resultado;
import impl.CampeonatoBrasileiroImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Aplicacao {

    public static void main(String[] args) throws IOException {

        Path file = Path.of("campeonato-brasileiro.csv");

        // obter a implementação: (ponto extra -> abstrair para interface)
        CampeonatoBrasileiroImpl resultados =
                new CampeonatoBrasileiroImpl(file, (jogo) -> jogo.data().data().getYear() == 2019);

        imprimirEstatisticas(resultados);

        imprimirTabela(resultados.getTabela());

    }

    private static void imprimirEstatisticas(CampeonatoBrasileiroImpl brasileirao) {
        IntSummaryStatistics statistics = brasileirao.getEstatisticasPorJogo();

        System.out.println("Estatisticas | Total de gols         | " + statistics.getSum());
        System.out.println("Estatisticas | Total de jogos        | " + statistics.getCount());
        System.out.printf ("Estatisticas | Media de gols         | %.2f\n", statistics.getAverage());

        Map.Entry<Resultado, Long> placarMaisRepetido = brasileirao.getPlacarMaisRepetido();

        System.out.println("Estatisticas | Placar mais repetido  | "
                + placarMaisRepetido.getKey() + " (" +placarMaisRepetido.getValue() + " jogo(s))");

        Map.Entry<Resultado, Long> placarMenosRepetido = brasileirao.getPlacarMenosRepetido();

        System.out.println("Estatisticas | Placar menos repetido | "
                + placarMenosRepetido.getKey() + " (" +placarMenosRepetido.getValue() + " jogo(s))");

        Long jogosCom3OuMaisGols = brasileirao.getTotalJogosCom3OuMaisGols();
        Long jogosComMenosDe3Gols = brasileirao.getTotalJogosComMenosDe3Gols();

        System.out.println("Estatisticas | 3 ou mais gols        | " + jogosCom3OuMaisGols);
        System.out.println("Estatisticas | menos de 3 gols       | " + jogosComMenosDe3Gols);

        Long totalVitoriasEmCasa = brasileirao.getTotalVitoriasEmCasa();
        Long vitoriasForaDeCasa = brasileirao.getTotalVitoriasForaDeCasa();
        Long empates = brasileirao.getTotalEmpates();

<<<<<<< Updated upstream
        System.out.println("Estatisticas (Vitorias Fora de casa) - " + vitoriasForaDeCasa);
        System.out.println("Estatisticas (Vitorias Em casa) - " + totalVitoriasEmCasa);
        System.out.println("Estatisticas (Empates) - " + empates);
=======
        System.out.println("Estatisticas | Vitorias Fora de casa | " + vitoriasForaDeCasa);
        System.out.println("Estatisticas | Vitorias Em casa      | " + totalVitoriasEmCasa);
        System.out.println("Estatisticas | Empates               | " + empates);

>>>>>>> Stashed changes
    }

    public static void imprimirTabela(Set<PosicaoTabela> posicoes) {
        System.out.println();
        System.out.println("####################################################################################################################");
        System.out.println("##########################                  TABELA CAMPEONADO BRASILEIRO                  ##########################");
        System.out.println("####################################################################################################################");
        System.out.println();
        System.out.println("Posição |      Time      | Pontos | Vitórias | Derrotas | Empates |  Gols positivos | Gols sofridos | Saldo de gols");
        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        int colocacao = 1;
        for (PosicaoTabela posicao : posicoes) {
            System.out.printf("%7s | %s\n", colocacao,  posicao);
            colocacao++;
        }

        System.out.println();
    }
}