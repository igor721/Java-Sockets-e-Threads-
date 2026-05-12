import socket

HOST = '172.17.0.1'
PORT = 12345

def receber_resposta(sock):
    dados = ""
    while True:
        parte = sock.recv(1024).decode('utf-8')
        dados += parte
        if "=== FIM ===" in dados:
            break
    return dados.replace("=== FIM ===", "").strip()

def main():

    client = None

    try:
        client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client.connect((HOST, PORT))
        print("Conectado ao Servidor de Filmes!")

        while True:
            print("\n=== MENU ===")
            print("1 - LISTAR_FILMES")
            print("2 - ALUGAR_FILME")
            print("3 - DEVOLVER_FILME")
            print("0 - SAIR")

            comando = input("\nEscolha uma opção: ").strip()
            client.sendall((comando + '\n').encode('utf-8'))

            if comando == '0':

                resposta = receber_resposta(client)
                print("\n" + resposta)
                print("Desconectando...")
                break

            resposta = receber_resposta(client)
            print("\n" + resposta)

            if comando in ['2', '3']:
                movie_id = input("\nDigite o ID: ").strip()
                client.sendall((movie_id + '\n').encode('utf-8'))
                resposta = receber_resposta(client)
                print("\n" + resposta)

    except ConnectionRefusedError:
        print("Servidor não está rodando!")
    except Exception as e:
        print(f"Erro: {e}")
    finally:
        if client:
            client.close()

if __name__ == "__main__":
    main()