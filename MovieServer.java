import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class MovieServer {

    private static final int PORT = 12345;
    private static final int MAX_CONNECTIONS = 5;

    private static final List<Movie> movies =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        movies.add(new Movie(1, "Interestelar", "Ficção Científica"));
        movies.add(new Movie(2, "Batman Begins", "Ação"));
        movies.add(new Movie(3, "Soul", "Animação"));
        movies.add(new Movie(4, "A Origem", "Suspense"));

        ExecutorService executor =
                Executors.newFixedThreadPool(MAX_CONNECTIONS);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Servidor iniciado na porta " + PORT);
            System.out.println("Máximo de conexões simultâneas: " + MAX_CONNECTIONS);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                System.out.println(
                        "Cliente conectado: "
                                + clientSocket.getInetAddress()
                );

                executor.execute(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {

            System.err.println("Erro no servidor: " + e.getMessage());

        } finally {

            executor.shutdown();
        }
    }

    public static synchronized String listMovies() {

        StringBuilder resultado = new StringBuilder();

        for (Movie m : movies) {

            resultado.append(m.toString()).append("\n");
        }

        return resultado.toString();
    }

    public static synchronized String rentMovie(int id) {

        for (Movie m : movies) {

            if (m.getId() == id) {

                if (m.isAvailable()) {

                    m.setAvailable(false);

                    return "SUCESSO: Filme '" +
                            m.getName() +
                            "' alugado com sucesso!";
                }

                return "ERRO: Filme já está alugado.";
            }
        }

        return "ERRO: Filme com ID " + id + " não encontrado.";
    }

    public static synchronized String returnMovie(int id) {

        for (Movie m : movies) {

            if (m.getId() == id) {

                if (!m.isAvailable()) {

                    m.setAvailable(true);

                    return "SUCESSO: Filme '" +
                            m.getName() +
                            "' devolvido com sucesso!";
                }

                return "ERRO: Este filme já está disponível.";
            }
        }

        return "ERRO: Filme com ID " + id + " não encontrado.";
    }
}