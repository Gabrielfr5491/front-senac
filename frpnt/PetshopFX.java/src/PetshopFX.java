import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

/**
 * Interface JavaFX integrada com as classes do seu Main (PetShop, Cliente, Animal, Servico, Racao, Agendamento, Compra).
 * CSS separado: petshop.css (coloque na mesma pasta do .class ou em resources).
 */
public class PetshopFX extends Application {

    private StackPane root;
    private BorderPane dashboard;
    private StackPane content;
    private StackPane loginOverlay;

    private PetShop shop = new PetShop();

    private final Map<String, String> users = new HashMap<>();

    @Override
    public void start(Stage stage) {

        // credenciais demo
        users.put("admin", "123");

        // carrega dados iniciais (mesmos do Main)
        carregarDadosPadrao();

        root = new StackPane();
        Scene scene = new Scene(root, 1200, 720);

        // tenta carregar css (se não encontrar, só usa estilo padrão)
        try {
            //link css
            scene.getStylesheets().add(
                    getClass().getResource("petshop.css").toExternalForm()
            );
        } catch (Exception ex) {
            System.out.println("Aviso: petshop.css não encontrado (use estilos padrão).");
        }

        criarDashboard();
        criarLoginOverlay();

        stage.setTitle("PetShop Premium");
        stage.setScene(scene);
        stage.show();
    }

    /* ================= DADOS PADRÃO (igual ao Main) ================= */
    private void carregarDadosPadrao() {
        Servico banho = new Servico("Banho", 50);
        banho.adicionarHorario("09:00"); banho.adicionarHorario("10:00"); banho.adicionarHorario("11:00");

        Servico tosa = new Servico("Tosa", 80);
        tosa.adicionarHorario("14:00"); tosa.adicionarHorario("16:00");

        shop.adicionarServico(banho);
        shop.adicionarServico(tosa);

        shop.adicionarRacao(new Racao("Ração Popular 10kg - Cães Adultos", 59.90, 20));
        shop.adicionarRacao(new Racao("Ração Econômica 5kg - Gatos", 42.50, 15));
        shop.adicionarRacao(new Racao("Ração Premium 10kg - Cães", 119.90, 10));
        shop.adicionarRacao(new Racao("Ração Super Premium 7kg - Gatos", 159.90, 8));
        shop.adicionarRacao(new Racao("Ração Natural & Grain Free 3kg - Cães Pequenos", 189.50, 5));
    }

    /* ================= DASHBOARD ================= */

    private void criarDashboard() {
        dashboard = new BorderPane();
        dashboard.getStyleClass().add("dashboard");

        dashboard.setLeft(criarSidebar());

        content = new StackPane(telaHome());
        dashboard.setCenter(content);

        // blur inicial (o overlay de login vai estar por cima)
        dashboard.setEffect(new GaussianBlur(25));

        root.getChildren().add(dashboard);
    }

    private VBox criarSidebar() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20));
        box.getStyleClass().add("sidebar");

        Label logo = new Label("🐾 PETSHOP");
        logo.getStyleClass().add("logo");

        box.getChildren().addAll(
                logo,
                botao("🏠 Home", this::telaHome),
                botao("👤 Cadastrar Cliente", this::telaCadastrarCliente),
                botao("📋 Listar Clientes", this::telaListarClientes),
                botao("🐶 Cadastrar Pet", this::telaCadastrarPet),
                botao("🩺 Agendar Serviço", this::telaAgendarServico),
                botao("🛒 Comprar Ração", this::telaComprarRacao)
        );

        return box;
    }

    private Button botao(String texto, Tela tela) {
        Button b = new Button(texto);
        b.getStyleClass().add("menu-btn");
        b.setMaxWidth(Double.MAX_VALUE);
        b.setOnAction(e -> trocarTela(tela.get()));
        return b;
    }

    private void trocarTela(Node tela) {
        FadeTransition fade = new FadeTransition(Duration.millis(220), content);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> {
            content.getChildren().setAll(tela);
            FadeTransition in = new FadeTransition(Duration.millis(260), content);
            in.setFromValue(0);
            in.setToValue(1);
            in.play();
        });
        fade.play();
    }

    /* ================= TELAS ================= */

    private Node telaHome() {
        VBox box = baseTela("Dashboard PetShop", "Sistema completo de gerenciamento");
        Label stats = new Label(
                "Clientes: " + shop.getClientes().size() +
                "    |    Serviços: " + shop.getServicos().size() +
                "    |    Rações: " + shop.getRacoes().size()
        );
        box.getChildren().add(stats);
        return box;
    }

    private Node telaCadastrarCliente() {
        VBox box = baseTela("Cadastrar Cliente", null);

        TextField nome = campo("Nome");
        TextField tel = campo("Telefone");

        Label msg = new Label();
        Button salvar = botaoAcao("Salvar");

        salvar.setOnAction(e -> {
            String n = nome.getText().trim();
            String t = tel.getText().trim();
            if (n.isEmpty()) {
                msg.setText("⚠️ Nome obrigatório");
                return;
            }
            shop.adicionarCliente(new Cliente(n, t));
            msg.setText("✅ Cliente cadastrado");
            nome.clear();
            tel.clear();
        });

        box.getChildren().addAll(nome, tel, salvar, msg);
        return box;
    }

    private Node telaListarClientes() {
        VBox box = baseTela("Clientes Cadastrados", null);
        ListView<String> lista = new ListView<>();

        shop.getClientes().forEach(c ->
                lista.getItems().add(c.getNome() + " - " + c.getTelefone())
        );

        // botão refresh
        Button refresh = botaoAcao("Atualizar");
        refresh.setOnAction(e -> {
            lista.getItems().clear();
            shop.getClientes().forEach(c ->
                    lista.getItems().add(c.getNome() + " - " + c.getTelefone())
            );
        });

        box.getChildren().addAll(lista, refresh);
        return box;
    }

    private Node telaCadastrarPet() {
        VBox box = baseTela("Cadastrar Pet", null);

        ComboBox<Cliente> cbCliente = new ComboBox<>();
        cbCliente.getItems().addAll(shop.getClientes());
        cbCliente.setPromptText("Selecione cliente");

        TextField nomePet = campo("Nome do Pet");
        TextField especie = campo("Espécie (ex: Cachorro)");
        TextField idade = campo("Idade (anos)");

        Label msg = new Label();
        Button salvar = botaoAcao("Cadastrar");

        salvar.setOnAction(e -> {
            Cliente c = cbCliente.getValue();
            if (c == null) {
                msg.setText("⚠️ Selecione um cliente");
                return;
            }
            String n = nomePet.getText().trim();
            String esp = especie.getText().trim();
            int id = 0;
            try {
                id = Integer.parseInt(idade.getText().trim());
            } catch (Exception ex) {
                msg.setText("⚠️ Idade inválida (use número)");
                return;
            }
            if (n.isEmpty() || esp.isEmpty()) {
                msg.setText("⚠️ Preencha todos os campos");
                return;
            }
            // usa construtor do Main: Animal(nome, especie, idade)
            Animal a = new Animal(n, esp, id);
            c.adicionarAnimal(a);
            msg.setText("✅ Pet cadastrado para " + c.getNome());
            nomePet.clear(); especie.clear(); idade.clear();
        });

        box.getChildren().addAll(cbCliente, nomePet, especie, idade, salvar, msg);
        return box;
    }

    private Node telaAgendarServico() {
        VBox box = baseTela("Agendar Serviço", null);

        ComboBox<Cliente> cbCliente = new ComboBox<>();
        cbCliente.getItems().addAll(shop.getClientes());
        cbCliente.setPromptText("Selecione Cliente");

        ComboBox<Animal> cbAnimal = new ComboBox<>();
        cbAnimal.setPromptText("Selecione Animal");

        cbCliente.setOnAction(e -> {
            Cliente sel = cbCliente.getValue();
            cbAnimal.getItems().clear();
            if (sel != null) {
                cbAnimal.getItems().addAll(sel.getAnimais());
            }
        });

        ComboBox<Servico> cbServico = new ComboBox<>();
        cbServico.getItems().addAll(shop.getServicos());
        cbServico.setPromptText("Selecione Serviço");

        ComboBox<String> cbHora = new ComboBox<>();
        cbHora.setPromptText("Selecione Horário");

        cbServico.setOnAction(e -> {
            Servico s = cbServico.getValue();
            cbHora.getItems().clear();
            if (s != null) {
                cbHora.getItems().addAll(s.getHorariosDisponiveis());
            }
        });

        Label msg = new Label();
        Button agendar = botaoAcao("Agendar");

        agendar.setOnAction(e -> {
            Cliente c = cbCliente.getValue();
            Animal a = cbAnimal.getValue();
            Servico s = cbServico.getValue();
            String h = cbHora.getValue();

            if (c == null || a == null || s == null || h == null) {
                msg.setText("⚠️ Preencha todos os campos");
                return;
            }

            // verifica se horário já agendado (usa método do PetShop)
            if (shop.horarioJaAgendado(s, h)) {
                msg.setText("⚠️ Horário já agendado");
                return;
            }

            Agendamento ag = new Agendamento(c, a, s, h);
            shop.adicionarAgendamento(ag);
            msg.setText("✅ Agendamento realizado");
        });

        box.getChildren().addAll(cbCliente, cbAnimal, cbServico, cbHora, agendar, msg);
        return box;
    }

    private Node telaComprarRacao() {
        VBox box = baseTela("Comprar Ração", null);

        ComboBox<Cliente> cbCliente = new ComboBox<>();
        cbCliente.getItems().addAll(shop.getClientes());
        cbCliente.setPromptText("Selecione Cliente");

        ComboBox<Racao> cbRacao = new ComboBox<>();
        cbRacao.getItems().addAll(shop.getRacoes());
        cbRacao.setPromptText("Selecione Ração");

        TextField qtd = campo("Quantidade");

        Label msg = new Label();
        Button comprar = botaoAcao("Comprar");

        comprar.setOnAction(e -> {
            Cliente c = cbCliente.getValue();
            Racao r = cbRacao.getValue();

            if (c == null || r == null) {
                msg.setText("⚠️ Selecionar cliente e ração");
                return;
            }

            int q;
            try {
                q = Integer.parseInt(qtd.getText().trim());
            } catch (Exception ex) {
                msg.setText("⚠️ Quantidade inválida");
                return;
            }
            if (q <= 0) { msg.setText("⚠️ Quantidade > 0"); return; }

            if (q > r.getQuantidade()) {
                msg.setText("⚠️ Estoque insuficiente");
                return;
            }

            r.setQuantidade(r.getQuantidade() - q);
            Compra comp = new Compra(c, r, q);
            shop.adicionarCompra(comp);
            msg.setText("✅ Compra registrada");
        });

        box.getChildren().addAll(cbCliente, cbRacao, qtd, comprar, msg);
        return box;
    }

    /* ================= LOGIN (overlay) ================= */

    private void criarLoginOverlay() {
        VBox card = new VBox(12);
        card.setMaxWidth(380);
        card.setPadding(new Insets(18));
        card.getStyleClass().add("login-card");

        TextField user = new TextField();
        user.setPromptText("Usuário");

        PasswordField pass = new PasswordField();
        pass.setPromptText("Senha");

        Label msg = new Label();
        msg.getStyleClass().add("login-msg");

        Button entrar = botaoAcao("Entrar");

        card.getChildren().addAll(user, pass, entrar, msg);

        // overlay centralizado
        loginOverlay = new StackPane(card);
        loginOverlay.getStyleClass().add("login-overlay");
        StackPane.setAlignment(card, Pos.CENTER);

        // adiciona ao root (por cima do dashboard)
        root.getChildren().add(loginOverlay);

        // ação do botão entrar
        entrar.setOnAction(e -> {
            if (users.containsKey(user.getText()) &&
                    users.get(user.getText()).equals(pass.getText())) {

                // anima fade-out do overlay e remove blur do dashboard
                FadeTransition f = new FadeTransition(Duration.seconds(0.9), loginOverlay);
                f.setFromValue(1);
                f.setToValue(0);
                f.setOnFinished(ev -> {
                    root.getChildren().remove(loginOverlay);
                    dashboard.setEffect(null); // remove blur
                });
                f.play();
            } else {
                msg.setText("❌ Login inválido");
                // shake
                TranslateTransition tr = new TranslateTransition(Duration.millis(60), card);
                tr.setFromX(-10); tr.setToX(10); tr.setCycleCount(6); tr.setAutoReverse(true);
                tr.play();
            }
        });
    }

    /* ================= UTIL ================= */

    private VBox baseTela(String titulo, String sub) {
        VBox box = new VBox(12);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));

        Label t = new Label(titulo);
        t.getStyleClass().add("title");

        box.getChildren().add(t);
        if (sub != null) {
            Label s = new Label(sub);
            s.getStyleClass().add("subtitle");
            box.getChildren().add(s);
        }

        return box;
    }

    private TextField campo(String prompt) {
        TextField t = new TextField();
        t.setPromptText(prompt);
        t.getStyleClass().add("input");
        return t;
    }

    private Button botaoAcao(String texto) {
        Button b = new Button(texto);
        b.getStyleClass().add("action-btn");
        b.setMinWidth(140);
        return b;
    }

    interface Tela { Node get(); }

    public static void main(String[] args) {
        launch();
    }

}
