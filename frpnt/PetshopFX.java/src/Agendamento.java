

public class Agendamento {
    Cliente cliente;
    Animal animal;
    Servico servico;
    String horario;

    public Agendamento(Cliente cliente, Animal animal, Servico servico, String horario) {
        this.cliente = cliente;
        this.animal = animal;
        this.servico = servico;
        this.horario = horario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Animal getAnimal() {
        return animal;
    }

    public Servico getServico() {
        return servico;
    }

    public String getHorario() {
        return horario;
    }

    public String toString() {
        return cliente.getNome() + " - " + animal.getNome() + " - " + servico.getNome() + " às " + horario;
    }
}
