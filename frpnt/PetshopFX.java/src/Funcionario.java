

public class Funcionario extends Pessoa {
    String cargo;

    public Funcionario(String nome, String telefone, String cargo) {
        super(nome, telefone);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String toString() {
        return nome + " - " + cargo;
    }
}
