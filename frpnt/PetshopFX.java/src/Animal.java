

public class Animal {
    String nome;
    String especie;
    int idade;
    Cliente dono;

    public Animal(String nome, String especie, int idade) {
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public String toString() {
        if (dono != null) {
            return nome + " (" + especie + ", " + idade + " anos, dono: " + dono.getNome() + ")";
        } else {
            return nome + " (" + especie + ", " + idade + " anos)";
        }
    }
}
