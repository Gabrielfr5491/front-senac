

public class Compra {
    Cliente cliente;
    Racao racao;
    int quantidade;

    public Compra(Cliente cliente, Racao racao, int quantidade) {
        this.cliente = cliente;
        this.racao = racao;
        this.quantidade = quantidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Racao getRacao() {
        return racao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return racao.getPreco() * quantidade;
    }

    public String toString() {
        return cliente.getNome() + " comprou " + quantidade + "x " + racao.getNome() +
               " por R$ " + String.format("%.2f", getValorTotal());
    }
}
