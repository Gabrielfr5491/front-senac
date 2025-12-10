

import java.util.ArrayList;

public class Servico {
    String nome;
    double preco;
    ArrayList<String> horarios = new ArrayList<String>();

    public Servico(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void adicionarHorario(String horario) {
        horarios.add(horario);
    }

    public ArrayList<String> getHorariosDisponiveis() {
        return horarios;
    }

    public String toString() {
        return nome + " - R$ " + preco;
    }
}
