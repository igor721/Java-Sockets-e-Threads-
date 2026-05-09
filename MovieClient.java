import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MovieClient {

    private static final String HOST = "localhost";

    private static final int PORT = 12345;

    public static void main(String[] args) {

        try (

                Socket socket = new Socket(HOST, PORT);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                StandardCharsets.UTF_8
                        )
                );

                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),
                                StandardCharsets.UTF_8
                        ),
                        true
                );

                Scanner scanner = new Scanner(System.in, "UTF-8")

        ) {

            System.out.println("=== SISTEMA DE ALUGUEL DE FILMES ===");

            while (true) {

                System.out.println("\n=== MENU ===");
                System.out.println("1 - LISTAR_FILMES");
                System.out.println("2 - ALUGAR_FILME");
                System.out.println("3 - DEVOLVER_FILME");
                System.out.println("0 - SAIR");

                System.out.print("Escolha uma opção: ");

                String opcao = scanner.nextLine().trim();

                out.println(opcao);

                if (opcao.equals("0")) {

                    String resposta;

                    while (!(resposta = in.readLine()).equals("=== FIM ===")) {
                        System.out.println(resposta);
                    }

                    break;
                }

                String resposta;

                while ((resposta = in.readLine()) != null) {

                    if (resposta.equals("=== FIM ===")) {
                        break;
                    }

                    System.out.println(resposta);
                }

                if (opcao.equals("2") || opcao.equals("3")) {

                    System.out.print("Digite o ID: ");

                    String id = scanner.nextLine();

                    out.println(id);

                    while ((resposta = in.readLine()) != null) {

                        if (resposta.equals("=== FIM ===")) {
                            break;
                        }

                        System.out.println(resposta);
                    }
                }
            }

        } catch (IOException e) {

            System.err.println("Erro de conexão: " + e.getMessage());
        }
    }
}