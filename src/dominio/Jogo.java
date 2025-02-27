package dominio;

public record Jogo(
                    Integer rodada,
                    DataDoJogo data,
                    Time mandante,
                    Time visitante,
                    Time vencedor,
                    String arena,
                    Integer mandantePlacar,
                    Integer visitantePlacar,
                    String estadoMandante,
                    String estadoVisitante,
                    String estadoVencedor){

    public Integer getTotalGols() {
        return this.mandantePlacar + this.visitantePlacar;
    }

    public Resultado getResutado() {
        return new Resultado(this.mandantePlacar, this.visitantePlacar);
    }

}
