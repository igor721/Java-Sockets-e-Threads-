import socket

HOST = 'localhost'
PORT = 12345

def main():
    client = None
    try:
        client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        client.connect((HOST, PORT))
        
        print("✅ Conectado ao Servidor de Filmes!")
        print(client.recv(1024).decode('utf-8'))
        
        while True:
            print("\n=== MENU ===")
            print("1 - LISTAR_FILMES")
            print("2 - ALUGAR_FILME")
            print("3 - DEVOLVER_FILME")
            print("0 - SAIR")
            
            comando = input("\nEscolha uma opção: ").strip()
            
            client.sendall((comando + '\n').encode('utf-8'))
            
            if comando == '0':
                print("👋 Desconectando...")
                break
            
            # Recebe resposta
            resposta = client.recv(4096).decode('utf-8').strip()
            print("\n" + resposta)
            
            # Se for lista, continua recebendo
            if "FILMES DISPONIVEIS" in resposta:
                while True:
                    mais = client.recv(1024).decode('utf-8').strip()
                    if not mais or "=== FIM DA LISTA ===" in mais:
                        break
                    print(mais)
            
    except ConnectionRefusedError:
        print("❌ Servidor não está rodando!")
    except Exception as e:
        print(f"❌ Erro: {e}")
    finally:
        if client:
            client.close()

if __name__ == "__main__":
    main()