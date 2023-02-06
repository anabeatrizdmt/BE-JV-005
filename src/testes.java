import dominio.Jogo;
import impl.CampeonatoBrasileiroImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class testes {

    public static void main(String[] args) throws IOException {


        Path file = Path.of("campeonato-brasileiro.csv");

        CampeonatoBrasileiroImpl resultados =
                new CampeonatoBrasileiroImpl(file, (jogo) -> jogo.data().data().getYear() == 2019);
//        List<Jogo> jogos = resultados.getJogos();
//        for (Jogo jogo : jogos
//             ) {
//            System.out.println(jogo.data().data().getYear());
//        }
    }
}
