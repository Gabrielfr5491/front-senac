

import java.util.ArrayList;

public class PetShop {
    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
    ArrayList<Servico> servicos = new ArrayList<Servico>();
    ArrayList<Racao> racoes = new ArrayList<Racao>();
    ArrayList<Agendamento> agendamentos = new ArrayList<Agendamento>();
    ArrayList<Compra> compras = new ArrayList<Compra>();

    public void adicionarCliente(Cliente c) {
        clientes.add(c);
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void adicionarFuncionario(Funcionario f) {
        funcionarios.add(f);
    }

    public ArrayList<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void adicionarServico(Servico s) {
        servicos.add(s);
    }

    public ArrayList<Servico> getServicos() {
        return servicos;
    }

    public void adicionarRacao(Racao r) {
        racoes.add(r);
    }

    public ArrayList<Racao> getRacoes() {
        return racoes;
    }

    public ArrayList<String> listarHorariosDisponiveis(String nomeServico) {
        ArrayList<String> horarios = new ArrayList<String>();

        for (Servico s : servicos) {
            if (s.getNome().equalsIgnoreCase(nomeServico)) {
                horarios.addAll(s.getHorariosDisponiveis());
            }
        }
        return horarios;
    }

    public void adicionarAgendamento(Agendamento ag) {
        agendamentos.add(ag);
    }

    public ArrayList<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public boolean horarioJaAgendado(Servico servico, String horario) {
        for (Agendamento a : agendamentos) {
            if (a.getServico().getNome().equalsIgnoreCase(servico.getNome()) &&
                a.getHorario().equalsIgnoreCase(horario)) {
                return true;
            }
        }
        return false;
    }

    public void adicionarCompra(Compra compra) {
        compras.add(compra);
    }

    public ArrayList<Compra> getCompras() {
        return compras;
    }

    // ----------- Gerar recibos simples -----------
    public void gerarRecibos() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado!");
            return;
        }

        System.out.println("\n===== RECIBOS =====");
        for (Cliente c : clientes) {
            double totalServicos = 0;
            double totalRacoes = 0;

            System.out.println("\nCliente: " + c.getNome() + " (" + c.getTelefone() + ")");

            // Serviços
            System.out.println("Serviços realizados:");
            for (Agendamento a : agendamentos) {
                if (a.getCliente().equals(c)) {
                    System.out.println(" - " + a.getServico().getNome() + " (" + a.getHorario() + ")");
                    totalServicos += a.getServico().getPreco();
                }
            }
            if (totalServicos == 0) System.out.println(" - Nenhum serviço");

            // Compras
            System.out.println("Rações compradas:");
            for (Compra comp : compras) {
                if (comp.getCliente().equals(c)) {
                    System.out.println(" - " + comp.getRacao().getNome() + " x" + comp.getQuantidade() + 
                                       " = R$ " + String.format("%.2f", comp.getValorTotal()));
                    totalRacoes += comp.getValorTotal();
                }
            }
            if (totalRacoes == 0) System.out.println(" - Nenhuma compra");

            System.out.println("Total gasto em serviços: R$ " + String.format("%.2f", totalServicos));
            System.out.println("Total gasto em rações: R$ " + String.format("%.2f", totalRacoes));
            System.out.println("TOTAL GERAL: R$ " + String.format("%.2f", totalServicos + totalRacoes));
        }
    }
}
