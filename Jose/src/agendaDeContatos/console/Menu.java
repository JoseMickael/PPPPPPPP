package agendaDeContatos.console;

import java.sql.Connection;
import java.util.Scanner;

public class Menu {

    private static Connection con;

    public static void main(String[] args) {
        con = ConexaoDb.conectar();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu da Agenda");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Listar Contatos");
            System.out.println("3. Deletar Contato");
            System.out.println("4. Atualizar Contato");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    adicionarContato(scanner);
                    break;
                case 2:
                    listarContatos();
                    break;
                case 3:
                    deletarContato(scanner);
                    break;
                case 4:
                    atualizarContato(scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    ConexaoDb.desconectar(con);
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void adicionarContato(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Sexo (Masculino/Feminino): ");
        String sexo = scanner.nextLine();
        System.out.print("Estado Civil (Solteiro/Casado): ");
        String estadoCivil = scanner.nextLine();

        Contatos contato = new Contatos(nome, email, telefone, endereco, estadoCivil, sexo);
        DaoContato contatoDao = new DaoContato (con);
        int id = 0;
		try {
			id = contatoDao.inserir(contato);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (id != -1) {
            System.out.println("Contato adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar contato.");
        }
    }

    private static void listarContatos() {
        DaoContato daoContato  = new DaoContato (con);
        daoContato.listar();
    }

    private static void deletarContato(Scanner scanner) {
        System.out.print("Informe o ID do contato a ser deletado: ");
        int id = scanner.nextInt();
        DaoContato DaoContato  = new DaoContato(con);
        if (DaoContato .deletar(id)) {
            System.out.println("Contato deletado com sucesso!");
        } else {
            System.out.println("Erro ao deletar contato.");
        }
    }

    private static void atualizarContato(Scanner scanner) {
        System.out.print("Informe o ID do contato a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Sexo (Masculino/Feminino): ");
        String sexo = scanner.nextLine();
        System.out.print("Estado Civil (Solteiro/Casado): ");
        String estadoCivil = scanner.nextLine();

        Contatos contato = new Contatos(id, nome, email, telefone, endereco, estadoCivil, sexo);
        DaoContato  contatoDao = new DaoContato (con);
        if (contatoDao.atualizar(contato)) {
            System.out.println("Contato atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar contato.");
        }
    }
}