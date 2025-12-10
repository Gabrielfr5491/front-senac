
import java.util.ArrayList;

public class Cliente extends Pessoa {
    ArrayList<Animal> animais = new ArrayList<Animal>();

    public Cliente(String nome, String telefone) {
        super(nome, telefone);
    }

    public void adicionarAnimal(Animal a) {
        if (a != null) {
            animais.add(a);
            a.setDono(this);
        }
    }

    public ArrayList<Animal> getAnimais() {
        return animais;
    }

    public String toString() {
        return nome + " (" + animais.size() + " animais)";
    }
}
