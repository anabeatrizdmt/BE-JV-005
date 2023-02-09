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

public class CampeonatoBrasileiroImpl {

    public Map<Integer, List<Jogo>> brasileirao;

    public List<Jogo> jogos;

    public CampeonatoBrasileiroImpl(Path arquivo, Predicate<Jogo> filtro) throws IOException {
        this.jogos = lerArquivo(arquivo);
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
            String line;

            while ((line = br.readLine()) != null) {

                String[] valores = line.split(";");
                Integer rodada = Integer.valueOf(valores[0]);
                DataDoJogo data;
                if (valores[2].contains("h")) {
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

    public IntSummaryStatistics getEstatisticasPorJogo() {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream()
                .mapToInt(Jogo::getTotalGols)
                .summaryStatistics();
    }

    public List<Jogo> todosOsJogos() {
        List<List<Jogo>> jogosPorRodadaBrasileirao = brasileirao.values().stream().toList();

        List<Jogo> jogosBrasileirao = new ArrayList<>();

        jogosPorRodadaBrasileirao.forEach(jogosBrasileirao::addAll);

        return jogosBrasileirao;
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
                .filter(jogo -> Objects.equals(jogo.mandantePlacar(), jogo.visitantePlacar()))
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
        Map<Resultado, Long> jogosBrasileiraoPorResultado = getTodosOsPlacares();

        return jogosBrasileiraoPorResultado.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .get();
    }

    private List<Time> getTodosOsTimes() {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        List<Time> times = new ArrayList<>();

        for (Jogo jogo : jogosBrasileirao) {
            times.add(jogo.mandante());
        }

        return times.stream().distinct().toList();
    }

    public List<PosicaoTabela> getTabela() {

        List<Time> times = getTodosOsTimes();

        Set<PosicaoTabela> posicoes = new HashSet<>();

        for (Time time : times) {
            posicoes.add(new PosicaoTabela(
                            new Time(time.nome()),
                            getPontos(time),
                            getVitorias(time),
                            getDerrotas(time),
                            getEmpates(time),
                            getGolsPositivos(time),
                            getGolsSofridos(time),
                            getGolsPositivos(time) - getGolsSofridos(time),
                            getNumeroJogos(time)
                    )
            );
        }

        Comparator<PosicaoTabela> comparator = Comparator
                .comparing(PosicaoTabela::pontos)
                .thenComparing(PosicaoTabela::vitorias)
                .thenComparing(PosicaoTabela::saldoDeGols).reversed();

        return posicoes.stream().sorted(comparator).toList();
    }

    private Long getVitorias(Time time) {

        Long vitoriasComoMandante = getTodosOsJogosPorTimeComoMandantes(time).stream().filter(jogo -> jogo.mandantePlacar() > jogo.visitantePlacar()).count();
        Long vitoriasComoVisitante = getTodosOsJogosPorTimeComoVisitante(time).stream().filter(jogo -> jogo.mandantePlacar() < jogo.visitantePlacar()).count();

        return vitoriasComoMandante + vitoriasComoVisitante;
    }

    private Long getDerrotas(Time time) {

        Long derrotasComoMandante = getTodosOsJogosPorTimeComoMandantes(time).stream().filter(jogo -> jogo.mandantePlacar() < jogo.visitantePlacar()).count();
        Long derrotasComoVisitante = getTodosOsJogosPorTimeComoVisitante(time).stream().filter(jogo -> jogo.mandantePlacar() > jogo.visitantePlacar()).count();

        return derrotasComoMandante + derrotasComoVisitante;
    }

    private Long getEmpates(Time time) {
        Long empateComoMandante = getTodosOsJogosPorTimeComoMandantes(time).stream().filter(jogo -> Objects.equals(jogo.mandantePlacar(), jogo.visitantePlacar())).count();
        Long empateComoVisitante = getTodosOsJogosPorTimeComoVisitante(time).stream().filter(jogo -> Objects.equals(jogo.mandantePlacar(), jogo.visitantePlacar())).count();

        return empateComoMandante + empateComoVisitante;
    }

    private Long getPontos(Time time) {
        return (3 * getVitorias(time)) + (getEmpates(time));
    }

    private Long getGolsPositivos(Time time) {
        int totalGolsPositivos = 0;

        totalGolsPositivos = getTodosOsJogosPorTimeComoMandantes(time).stream()
                .map(Jogo::mandantePlacar)
                .reduce(Math.toIntExact(totalGolsPositivos), Integer::sum);

        totalGolsPositivos = getTodosOsJogosPorTimeComoVisitante(time).stream()
                .map(Jogo::visitantePlacar)
                .reduce(Math.toIntExact(totalGolsPositivos), Integer::sum);

        return (long) totalGolsPositivos;
    }

    private Long getGolsSofridos(Time time) {

        int totalGolsSofridos = 0;
        totalGolsSofridos = getTodosOsJogosPorTimeComoMandantes(time).stream()
                .map(Jogo::visitantePlacar)
                .reduce(Math.toIntExact(totalGolsSofridos), Integer::sum);

        totalGolsSofridos = getTodosOsJogosPorTimeComoVisitante(time).stream()
                .map(Jogo::mandantePlacar)
                .reduce(Math.toIntExact(totalGolsSofridos), Integer::sum);

        return (long) totalGolsSofridos;
    }

    private Long getNumeroJogos(Time time) {
        Long jogosComoMandante = (long) getTodosOsJogosPorTimeComoMandantes(time).size();
        Long jogosComoVisitante = (long) getTodosOsJogosPorTimeComoVisitante(time).size();

        return jogosComoMandante + jogosComoVisitante;
    }

    private List<Jogo> getTodosOsJogosPorTimeComoMandantes(Time time) {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream().filter(jogo -> jogo.mandante().nome().equals(time.nome())).toList();
    }

    private List<Jogo> getTodosOsJogosPorTimeComoVisitante(Time time) {

        List<Jogo> jogosBrasileirao = todosOsJogos();

        return jogosBrasileirao.stream().filter(jogo -> jogo.visitante().nome().equals(time.nome())).toList();
    }
}
