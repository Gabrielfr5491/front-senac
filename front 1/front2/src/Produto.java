

//Classe que representa um produto vendido na loja/pet shop
//Foi feita para se parecer com a tabela "produtos" do banco.
public class Produto {

 // Atributos correspondentes às colunas da tabela "produtos"
 private int idProduto;        // id_produto
 private String nomeProduto;   // nome_produto
 private String descricao;     // descricao
 private double preco;         // preco (DECIMAL no banco, double aqui)
 private int estoque;          // estoque
 private int categoriaId;      // categoria_id (apenas o número da categoria)

 // Construtor vazio
 // Serve para criar um produto sem dados ainda.
 public Produto() {
 }

 // Construtor com parâmetros
 // Serve para criar um produto já com todos os dados preenchidos.
 public Produto(int idProduto, String nomeProduto, String descricao,
                double preco, int estoque, int categoriaId) {
     this.idProduto = idProduto;
     this.nomeProduto = nomeProduto;
     this.descricao = descricao;
     this.preco = preco;
     this.estoque = estoque;
     this.categoriaId = categoriaId;
 }

 // Retorna o ID do produto
 public int getIdProduto() {
     return idProduto;
 }

 // Define (altera) o ID do produto
 public void setIdProduto(int idProduto) {
     this.idProduto = idProduto;
 }

 // Retorna o nome do produto
 public String getNomeProduto() {
     return nomeProduto;
 }

 // Define (altera) o nome do produto
 public void setNomeProduto(String nomeProduto) {
     this.nomeProduto = nomeProduto;
 }

 // Retorna a descrição do produto
 public String getDescricao() {
     return descricao;
 }

 // Define (altera) a descrição do produto
 public void setDescricao(String descricao) {
     this.descricao = descricao;
 }

 // Retorna o preço do produto
 public double getPreco() {
     return preco;
 }

 // Define (altera) o preço do produto
 public void setPreco(double preco) {
     this.preco = preco;
 }

 // Retorna o estoque atual do produto
 public int getEstoque() {
     return estoque;
 }

 // Define (altera) o estoque do produto
 public void setEstoque(int estoque) {
     this.estoque = estoque;
 }

 // Retorna o código da categoria do produto
 public int getCategoriaId() {
     return categoriaId;
 }

 // Define (altera) o código da categoria do produto
 public void setCategoriaId(int categoriaId) {
     this.categoriaId = categoriaId;
 }

 // Mostra os dados do produto na tela
 public void exibirDados() {
     System.out.println("=== Dados do Produto ===");
     System.out.println("ID: " + idProduto);
     System.out.println("Nome: " + nomeProduto);
     System.out.println("Descrição: " + descricao);
     System.out.println("Preço: R$ " + preco);
     System.out.println("Estoque: " + estoque);
     System.out.println("Categoria ID: " + categoriaId);
     System.out.println("------------------------");
 }
}
