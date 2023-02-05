package impl;

import dominio.Jogo;
import dominio.PosicaoTabela;
import dominio.Resultado;
import dominio.Time;

import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CampeonatoBrasileiroImpl {

    private Map<Integer, List<Jogo>> brasileirao;
    private List<Jogo> jogos;
    private Predicate<Jogo> filtro;

    public CampeonatoBrasileiroImpl(Path arquivo, Predicate<Jogo> filtro) throws IOException {
        this.jogos = lerArquivo(arquivo);
        this.filtro = filtro;
        this.brasileirao = jogos.stream()
                .filter(filtro) //filtrar por ano
                .collect(Collectors.groupingBy(
                        Jogo::rodada,
                        Collectors.mapping(Function.identity(), Collectors.toList())));

    }

    public List<Jogo> lerArquivo(Path file) throws IOException {
        return null;
    }

    public IntSummaryStatistics getEstatisticasPorJogo() {
        return null;
    }

    public Map<Jogo, Integer> getMediaGolsPorJogo() {
        return null;
    }

    public IntSummaryStatistics GetEstatisticasPorJogo() {
        return null;
    }

    public List<Jogo> todosOsJogos() {
        return null;
    }

    public Long getTotalVitoriasEmCasa() {
        return null;
    }

    public Long getTotalVitoriasForaDeCasa() {
        return null;
    }

    public Long getTotalEmpates() {
        return null;
    }

    public Long getTotalJogosComMenosDe3Gols() {
        return null;
    }

    public Long getTotalJogosCom3OuMaisGols() {
        return null;
    }

    public Map<Resultado, Long> getTodosOsPlacares() {
        return null;
    }

    public Map.Entry<Resultado, Long> getPlacarMaisRepetido() {
        return null;
    }

    public Map.Entry<Resultado, Long> getPlacarMenosRepetido() {
        return null;
    }

    private List<Time> getTodosOsTimes() {
        return null;
    }

    private Map<Time, List<Jogo>> getTodosOsJogosPorTimeComoMandantes() {
        return null;
    }

    private Map<Time, List<Jogo>> getTodosOsJogosPorTimeComoVisitante() {
        return null;
    }

    public Map<Time, List<Jogo>> getTodosOsJogosPorTime() {
        return null;
    }

    public Map<Time, Map<Boolean, List<Jogo>>> getJogosParticionadosPorMandanteTrueVisitanteFalse() {
        return null;
    }

    public Set<PosicaoTabela> getTabela() {
        return null;
    }

    private DayOfWeek getDayOfWeek(String dia) {

        DayOfWeek dayOfWeek = null;

        switch (dia) {
            case "Segunda-feira":
                dayOfWeek = DayOfWeek.MONDAY;
                break;
            case "Terça-feira":
                dayOfWeek = DayOfWeek.TUESDAY;
                break;
            case "Quarta-feira":
                dayOfWeek = DayOfWeek.WEDNESDAY;
                break;
            case "Quinta-feira":
                dayOfWeek = DayOfWeek.THURSDAY;
                break;
            case "Sexta-feira":
                dayOfWeek = DayOfWeek.FRIDAY;
                break;
            case "Sábado":
                dayOfWeek = DayOfWeek.SATURDAY;
                break;
            case "Domingo":
                dayOfWeek = DayOfWeek.SUNDAY;
                break;
        }

        return dayOfWeek;
    }

    private Map<Integer, Integer> getTotalGolsPorRodada() {
        return null;
    }

    private Map<Time, Integer> getTotalDeGolsPorTime() {
        return null;
    }

    private Map<Integer, Double> getMediaDeGolsPorRodada() {
        return null;
    }


}