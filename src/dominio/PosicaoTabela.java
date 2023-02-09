package dominio;

public record PosicaoTabela(Time time,
                            Long pontos,
                            Long vitorias,
                            Long derrotas,
                            Long empates,
                            Long golsPositivos,
                            Long golsSofridos,
                            Long saldoDeGols,
                            Long jogos) {

    @Override
    public String toString() {

        return  String.format("%14s | %6s | %8s | %8s | %7s | %15s | %13s | %13s",
                time, pontos, vitorias, derrotas, empates,
                golsPositivos, golsSofridos, saldoDeGols);
    }
}
