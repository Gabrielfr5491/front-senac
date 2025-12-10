

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        PetShop shop = new PetShop();

        // ---------- SERVIÇOS PADRÃO ----------
        Servico banho = new Servico("Banho", 50);
        banho.adicionarHorario("09:00");
        banho.adicionarHorario("10:00");
        banho.adicionarHorario("11:00");

        Servico tosa = new Servico("Tosa", 80);
        tosa.adicionarHorario("14:00");
        tosa.adicionarHorario("16:00");

        shop.adicionarServico(banho);
        shop.adicionarServico(tosa);

        // ---------- RAÇÕES PADRÃO ----------
        // Tipos variando entre baratas e premium
        Racao r1 = new Racao("Ração Popular 10kg - Cães Adultos", 59.90, 20);
        Racao r2 = new Racao("Ração Econômica 5kg - Gatos", 42.50, 15);
        Racao r3 = new Racao("Ração Premium 10kg - Cães", 119.90, 10);
        Racao r4 = new Racao("Ração Super Premium 7kg - Gatos", 159.90, 8);
        Racao r5 = new Racao("Ração Natural & Grain Free 3kg - Cães Pequenos", 189.50, 5);

        shop.adicionarRacao(r1);
        shop.adicionarRacao(r2);
        shop.adicionarRacao(r3);
        shop.adicionarRacao(r4);
        shop.adicionarRacao(r5);

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n===== MENU PETSHOP =====");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Listar Clientes");
            System.out.println("3 - Cadastrar Serviço");
            System.out.println("4 - Listar Serviços");
            System.out.println("5 - Ver horários disponíveis de um serviço");
            System.out.println("6 - Cadastrar Ração");
            System.out.println("7 - Listar Rações");
            System.out.println("8 - Agendar Serviço");
            System.out.println("9 - Comprar Ração");
            System.out.println("0 - Sair e gerar recibos");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente(shop, input);
                case 2 -> listarClientes(shop);
                case 3 -> cadastrarServico(shop, input);
                case 4 -> listarServicos(shop);
                case 5 -> listarHorariosDeServico(shop, input);
                case 6 -> cadastrarRacao(shop, input);
                case 7 -> listarRacoes(shop);
                case 8 -> agendarServico(shop, input);
                case 9 -> comprarRacao(shop, input);
                case 0 -> {
                    System.out.println("Encerrando o programa...");
                    shop.gerarRecibos();
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        input.close();
    }

    // As demais funções (cadastrarCliente, listarClientes, agendarServico etc.)
    // permanecem exatamente como na versão anterior
    // incluindo a função comprarRacao() e gerarRecibos()



    // --- Funções auxiliares já existentes (sem alteração até agendarServico) ---

    public static void cadastrarCliente(PetShop shop, Scanner input) {
        System.out.print("Nome do cliente: ");
        String nome = input.nextLine();
        System.out.print("Telefone: ");
        String telefone = input.nextLine();

        Cliente c = new Cliente(nome, telefone);

        System.out.print("Deseja cadastrar um animal para este cliente? (s/n): ");
        String resposta = input.nextLine();

        while (resposta.equalsIgnoreCase("s")) {
            System.out.print("Nome do animal: ");
            String nomeAnimal = input.nextLine();
            System.out.print("Espécie: ");
            String especie = input.nextLine();
            System.out.print("Idade: ");
            int idade = input.nextInt();
            input.nextLine();

            Animal a = new Animal(nomeAnimal, especie, idade);
            c.adicionarAnimal(a);

            System.out.print("Deseja adicionar outro animal? (s/n): ");
            resposta = input.nextLine();
        }

        shop.adicionarCliente(c);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void listarClientes(PetShop shop) {
        if (shop.getClientes().isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
        } else {
            System.out.println("\n=== CLIENTES CADASTRADOS ===");
            for (Cliente c : shop.getClientes()) {
                System.out.println("- " + c.getNome() + " (" + c.getTelefone() + ")");
                for (Animal a : c.getAnimais()) {
                    System.out.println("   * " + a);
                }
            }
        }
    }

    public static void cadastrarServico(PetShop shop, Scanner input) {
        System.out.print("Nome do serviço: ");
        String nome = input.nextLine();
        System.out.print("Preço: ");
        double preco = input.nextDouble();
        input.nextLine();

        Servico s = new Servico(nome, preco);

        System.out.print("Deseja adicionar horários disponíveis? (s/n): ");
        String resp = input.nextLine();

        while (resp.equalsIgnoreCase("s")) {
            System.out.print("Horário (ex: 09:00): ");
            String h = input.nextLine();
            s.adicionarHorario(h);
            System.out.print("Adicionar outro horário? (s/n): ");
            resp = input.nextLine();
        }

        shop.adicionarServico(s);
        System.out.println("Serviço cadastrado com sucesso!");
    }

    public static void listarServicos(PetShop shop) {
        if (shop.getServicos().isEmpty()) {
            System.out.println("Nenhum serviço cadastrado!");
        } else {
            System.out.println("\n=== SERVIÇOS ===");
            for (Servico s : shop.getServicos()) {
                System.out.println("- " + s.getNome() + " | R$ " + s.getPreco() + " | Horários: " + s.getHorariosDisponiveis());
            }
        }
    }

    public static void listarHorariosDeServico(PetShop shop, Scanner input) {
        System.out.print("Digite o nome do serviço: ");
        String nome = input.nextLine();
        ArrayList<String> horarios = shop.listarHorariosDisponiveis(nome);
        if (horarios.isEmpty()) {
            System.out.println("Serviço não encontrado ou sem horários!");
        } else {
            System.out.println("Horários disponíveis para " + nome + ":");
            for (String h : horarios) {
                System.out.println(" - " + h);
            }
        }
    }

    public static void cadastrarRacao(PetShop shop, Scanner input) {
        System.out.print("Nome da ração: ");
        String nome = input.nextLine();
        System.out.print("Preço: ");
        double preco = input.nextDouble();
        System.out.print("Quantidade em estoque: ");
        int qtd = input.nextInt();
        input.nextLine();

        Racao r = new Racao(nome, preco, qtd);
        shop.adicionarRacao(r);

        System.out.println("Ração cadastrada com sucesso!");
    }

    public static void listarRacoes(PetShop shop) {
        if (shop.getRacoes().isEmpty()) {
            System.out.println("Nenhuma ração cadastrada!");
        } else {
            System.out.println("\n=== RAÇÕES ===");
            for (int i = 0; i < shop.getRacoes().size(); i++) {
                Racao r = shop.getRacoes().get(i);
                System.out.println((i + 1) + " - " + r);
            }
        }
    }

    public static void agendarServico(PetShop shop, Scanner input) {
        if (shop.getClientes().isEmpty() || shop.getServicos().isEmpty()) {
            System.out.println("É preciso ter pelo menos 1 cliente e 1 serviço cadastrados!");
            return;
        }

        System.out.println("\n=== AGENDAR SERVIÇO ===");

        for (int i = 0; i < shop.getClientes().size(); i++) {
            System.out.println((i + 1) + " - " + shop.getClientes().get(i).getNome());
        }
        System.out.print("Escolha o número do cliente: ");
        int numCliente = input.nextInt();
        input.nextLine();
        Cliente cliente = shop.getClientes().get(numCliente - 1);

        if (cliente.getAnimais().isEmpty()) {
            System.out.println("Este cliente não tem animais cadastrados!");
            return;
        }

        for (int i = 0; i < cliente.getAnimais().size(); i++) {
            System.out.println((i + 1) + " - " + cliente.getAnimais().get(i).getNome());
        }
        System.out.print("Escolha o número do animal: ");
        int numAnimal = input.nextInt();
        input.nextLine();
        Animal animal = cliente.getAnimais().get(numAnimal - 1);

        for (int i = 0; i < shop.getServicos().size(); i++) {
            System.out.println((i + 1) + " - " + shop.getServicos().get(i).getNome());
        }
        System.out.print("Escolha o número do serviço: ");
        int numServico = input.nextInt();
        input.nextLine();
        Servico servico = shop.getServicos().get(numServico - 1);

        boolean agendado = false;
        while (!agendado) {
            System.out.println("Horários disponíveis: " + servico.getHorariosDisponiveis());
            System.out.print("Escolha um horário: ");
            String horario = input.nextLine();

            if (shop.horarioJaAgendado(servico, horario)) {
                System.out.println("⚠️ Esse horário já foi agendado! Escolha outro.");
            } else {
                Agendamento ag = new Agendamento(cliente, animal, servico, horario);
                shop.adicionarAgendamento(ag);
                System.out.println("✅ Agendamento realizado com sucesso!");
                agendado = true;
            }
        }
    }

    // -------- NOVA FUNÇÃO: COMPRAR RAÇÃO --------

    public static void comprarRacao(PetShop shop, Scanner input) {
        if (shop.getClientes().isEmpty() || shop.getRacoes().isEmpty()) {
            System.out.println("É preciso ter pelo menos 1 cliente e 1 ração cadastrados!");
            return;
        }

        System.out.println("\n=== COMPRAR RAÇÃO ===");

        for (int i = 0; i < shop.getClientes().size(); i++) {
            System.out.println((i + 1) + " - " + shop.getClientes().get(i).getNome());
        }
        System.out.print("Escolha o número do cliente: ");
        int numCliente = input.nextInt();
        input.nextLine();
        Cliente cliente = shop.getClientes().get(numCliente - 1);

        for (int i = 0; i < shop.getRacoes().size(); i++) {
            System.out.println((i + 1) + " - " + shop.getRacoes().get(i));
        }
        System.out.print("Escolha o número da ração: ");
        int numRacao = input.nextInt();
        System.out.print("Quantidade desejada: ");
        int qtd = input.nextInt();
        input.nextLine();

        Racao racao = shop.getRacoes().get(numRacao - 1);

        if (qtd > racao.getQuantidade()) {
            System.out.println("Quantidade insuficiente em estoque!");
            return;
        }

        racao.setQuantidade(racao.getQuantidade() - qtd);
        Compra compra = new Compra(cliente, racao, qtd);
        shop.adicionarCompra(compra);

        System.out.println("✅ Compra registrada com sucesso!");
    }
}
