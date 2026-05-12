import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MovieClient {

    private static final String HOST = "172.17.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) {

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        Scanner scanner = null;

        try {
            socket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            scanner = new Scanner(System.in, "UTF-8");

            System.out.println("=== SISTEMA DE ALUGUEL DE FILMES ===");

            while (true) {

                System.out.println("\n=== MENU ===");
                System.out.println("1 - LISTAR_FILMES");
                System.out.println("2 - ALUGAR_FILME");
                System.out.println("3 - DEVOLVER_FILME");
                System.out.println("0 - SAIR");
                System.out.print("Escolha uma opção: ");

                String opcao = scanner.nextLine();

                out.println(opcao);

                switch (opcao) {
                    case "0":
                        String respostaSaida;
                        while ((respostaSaida = in.readLine()) != null) {
                            if (respostaSaida.equals("=== FIM ===")) {
                                break;
                            }
                            System.out.println(respostaSaida);
                        }
                        System.out.println("Desconectando...");
                        return;   

                    case "2":
                    case "3":

                        String respostaInicial;
                        while ((respostaInicial = in.readLine()) != null) {
                            if (respostaInicial.equals("=== FIM ===")) {
                                break;
                            }
                            System.out.println(respostaInicial);
                        }

                        System.out.print("Digite o ID: ");
                        String id = scanner.nextLine();

                        out.println(id);

                        String respostaFinal;
                        while ((respostaFinal = in.readLine()) != null) {
                            if (respostaFinal.equals("=== FIM ===")) {
                                break;
                            }
                            System.out.println(respostaFinal);
                        }
                        break;

                    case "1":
                    default:

                        String resposta;
                        while ((resposta = in.readLine()) != null) {
                            if (resposta.equals("=== FIM ===")) {
                                break;
                            }
                            System.out.println(resposta);
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }
    }
}