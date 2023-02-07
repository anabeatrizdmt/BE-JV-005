package impl;

import dominio.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CampeonatoBrasileiroImpl {

    public Map<Integer, List<Jogo>> brasileirao;

    public List<Jogo> jogos;
    private Predicate<Jogo> filtro;

    public CampeonatoBrasileiroImpl(Path arquivo, Predicate<Jogo> filtro) throws IOException {
        this.jogos = lerArquivo(arquivo);
        this.filtro = filtro;
        this.brasileirao = jogos.stream()
                .filter(filtro)
                .collect(Collectors.groupingBy(
                        Jogo::rodada,
                        Collectors.mapping(Function.identity(), Collectors.toList())));

    }

    public List<Jogo> lerArquivo(Path arquivo) throws IOException {

        List<Jogo> jogos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo.toFile()))) {

            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyy");
            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH'h'mm");
            DateTimeFormatter formatterHora2 = DateTimeFormatter.ofPattern("HH':'mm");

            br.readLine();
            String line = null;

            while ((line = br.readLine()) != null) {

                String[] valores = line.split(";");
                Integer rodada = Integer.valueOf(valores[0]);
                DataDoJogo data;
                if(valores[2].contains("h")) {
                    data = new DataDoJogo(
                            LocalDate.parse(valores[1], formatterData),
                            LocalTime.parse(valores[2], formatterHora),
                            getDayOfWeek(valores[3])
                    );
                } else if (valores[2].contains(":")) {
                    data = new DataDoJogo(
                            LocalDate.parse(valores[1], formatterData),
                            LocalTime.parse(valores[2], formatterHora2),
                            getDayOfWeek(valores[3])
                    );
                } else {
                    data = new DataDoJogo(
                            LocalDate.parse(valores[1], formatterData),
                            null,
                            getDayOfWeek(valores[3])
                    );
                }
                Time mandante = new Time(valores[4]);
                Time visitante = new Time(valores[5]);
                Time vencedor = new Time(valores[6]);
                String arena = valores[7];
                Integer mandantePlacar = Integer.valueOf(valores[8]);
                Integer visitantePlacar = Integer.valueOf(valores[9]);
                String estadoMandante = valores[10];
                String estadoVisitante = valores[11];
                String estadoVencedor = valores[12];
                Jogo jogo = new Jogo(rodada, data, mandante, visitante, vencedor, arena,
                        mandantePlacar, visitantePlacar, estadoMandante, estadoVisitante,
                        estadoVencedor);
                jogos.add(jogo);
            }
        }
        return jogos;
    }

    public IntSummaryStatistics getEstatisticasPorJogo() {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream()
                .mapToInt(Jogo::getTotalGols)
                .summaryStatistics();
    }

    public Map<Jogo, Integer> getMediaGolsPorJogo() {
        return null;
    }

    public IntSummaryStatistics GetEstatisticasPorJogo() {
        return null;
    }

    public List<Jogo> todosOsJogos() {
        List<List<Jogo>> jogosPorRodadaBrasileirao = brasileirao.values().stream().toList();

        List<Jogo> jogosBrasileirao = new ArrayList<>();

        jogosPorRodadaBrasileirao.forEach(jogosBrasileirao::addAll);

        return  jogosBrasileirao;
    }

    public Long getTotalVitoriasEmCasa() {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream()
                .filter(jogo -> jogo.mandantePlacar() > jogo.visitantePlacar())
                .count();
    }

    public Long getTotalVitoriasForaDeCasa() {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream()
                .filter(jogo -> jogo.visitantePlacar() > jogo.mandantePlacar())
                .count();
    }

    public Long getTotalEmpates() {
        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream()
                .filter(jogo -> jogo.mandantePlacar() == jogo.visitantePlacar())
                .count();
    }

    public Long getTotalJogosComMenosDe3Gols() {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream()
                .filter(jogo -> jogo.getTotalGols() < 3)
                .count();
    }

    public Long getTotalJogosCom3OuMaisGols() {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream()
                .filter(jogo -> jogo.getTotalGols() >= 3)
                .count();
    }

    public Map<Resultado, Long> getTodosOsPlacares() {

        List<Jogo> jogosBrasileirao = todosOsJogos();
        return jogosBrasileirao.stream()
                .collect(Collectors.groupingBy(
                        Jogo::getResutado,
                        Collectors.counting()
                ));
    }

    public Map.Entry<Resultado, Long> getPlacarMaisRepetido() {

        Map<Resultado, Long> jogosBrasileiraoPorResultado = getTodosOsPlacares();

        return jogosBrasileiraoPorResultado.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
    }

    public Map.Entry<Resultado, Long> getPlacarMenosRepetido() {
        List<Jogo> jogosBrasileirao = todosOsJogos();

        Map<Resultado, Long> jogosBrasileiraoPorResultado = getTodosOsPlacares();

        return jogosBrasileiraoPorResultado.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .get();
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
//        Flamengo,
//        pontos=71,
//        vitorias=21,
//        derrotas=9,
//        empates=8,
//        golsPositivos=68,
//        golsSofridos=48,
//        saldoDeGols=20

        return null;
    }

    private DayOfWeek getDayOfWeek(String dia) {

        return switch (dia) {
            case "Segunda-feira" -> DayOfWeek.MONDAY;
            case "Terça-feira" -> DayOfWeek.TUESDAY;
            case "Quarta-feira" -> DayOfWeek.WEDNESDAY;
            case "Quinta-feira" -> DayOfWeek.THURSDAY;
            case "Sexta-feira" -> DayOfWeek.FRIDAY;
            case "Sábado" -> DayOfWeek.SATURDAY;
            case "Domingo" -> DayOfWeek.SUNDAY;
            default -> null;
        };
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