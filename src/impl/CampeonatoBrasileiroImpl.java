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
                .filter(filtro) //filtrar por ano
//                .forEach(System.out::println);
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
        IntStream brasileiraoIS = todosOsJogos().stream()
                .mapToInt(Jogo::getTotalGols);
        IntSummaryStatistics statistics = brasileiraoIS.summaryStatistics();
        return statistics;
    }

    public Map<Jogo, Integer> getMediaGolsPorJogo() {
        return null;
    }

    public IntSummaryStatistics GetEstatisticasPorJogo() {
        return null;
    }

    public List<Jogo> todosOsJogos() {
        List<Jogo> jogosBrasileirao = new ArrayList<>();
//        for (Integer key: brasileirao.keySet()) {
//            jogosBrasileirao.add(brasileirao.get(key));
//        }
        return jogosBrasileirao;
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