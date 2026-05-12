import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true );

            String clientAddress = socket.getInetAddress().getHostAddress();
            System.out.println("[CLIENTE " + clientAddress + "] Conectado.");
            String input;

            while ((input = in.readLine()) != null) {

                input = input.trim();
                System.out.println("[CLIENTE " + clientAddress + "] Enviou: " + input);

                switch (input) {

                    case "1":
                        out.println("FILMES DISPONIVEIS:");
                        String lista = MovieServer.listMovies();
                        out.println(lista);
                        out.println("=== FIM ===");
                        break;

                    case "2":
                        out.println("Digite o ID do filme para alugar:");
                        out.println("=== FIM ===");

                        String idAlugar = in.readLine();
                        if (idAlugar != null) {
                            try {
                                int id = Integer.parseInt(idAlugar.trim());
                                out.println(MovieServer.rentMovie(id));
                            } catch (NumberFormatException e) {
                                out.println("ERRO: ID deve ser um número.");
                            }
                        }
                        out.println("=== FIM ===");
                        break;

                    case "3":
                        out.println("Digite o ID do filme para devolver:");
                        out.println("=== FIM ===");

                        String idDevolver = in.readLine();
                        if (idDevolver != null) {
                            try {
                                int id = Integer.parseInt(idDevolver.trim());
                                out.println(MovieServer.returnMovie(id));
                            } catch (NumberFormatException e) {
                                out.println("ERRO: ID deve ser um número.");
                            }
                        }
                        out.println("=== FIM ===");
                        break;

                    case "0":
                        out.println("Conexão encerrada.");
                        out.println("=== FIM ===");
                        System.out.println("[CLIENTE " + clientAddress + "] Desconectou.");
                        return;

                    default:
                        out.println("Comando inválido! Use 1, 2, 3 ou 0.");
                        out.println("=== FIM ===");
                        break;
                }
            }

        } catch (IOException e) {
            System.err.println("Erro com cliente " +  socket.getInetAddress().getHostAddress() + ": " + e.getMessage());
        } 
    }
}