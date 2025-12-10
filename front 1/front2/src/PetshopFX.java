import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

public class PetshopFX extends Application {

    private List<Funcionario> funcionarios = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Servico> servicos = new ArrayList<>();
    private List<Pet> pets = new ArrayList<>();
    private List<Agendamento> agendamentos = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mostrarTelaLogin(primaryStage);  // Mostra a tela de login inicialmente
    }

    private void mostrarTelaLogin(Stage primaryStage) {
        // Criando a tela de login
        VBox vboxLogin = new VBox(10);
        vboxLogin.setAlignment(Pos.CENTER);

        // Campos de login
        TextField usuarioField = new TextField();
        usuarioField.setPromptText("Nome de Usuário");

        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Senha");

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String usuario = usuarioField.getText();
            String senha = senhaField.getText();

            // Simulação de validação do login
            if (validarLogin(usuario, senha)) {
                mostrarMenuPrincipal(primaryStage);  // Se o login for válido, mostra o menu principal
            } else {
                showAlert(AlertType.WARNING, "Login Falhou", "Usuário ou senha incorretos.");
            }
        });

        // Layout para o login
        vboxLogin.getChildren().addAll(usuarioField, senhaField, btnLogin);

        Scene sceneLogin = new Scene(vboxLogin, 350, 400);
        primaryStage.setTitle("Tela de Login");
        primaryStage.setScene(sceneLogin);
        primaryStage.show();
    }

    // Função para validar o login (simples exemplo)
    private boolean validarLogin(String usuario, String senha) {
        // Simula uma validação simples de login, substitua com uma validação real
        return usuario.equals("admin") && senha.equals("1234");
    }

    private void mostrarMenuPrincipal(Stage primaryStage) {
        // Layout do menu principal
        BorderPane root = new BorderPane();

        Button btnClientes = new Button("Gerenciar Clientes");
        Button btnFuncionarios = new Button("Gerenciar Funcionários");
        Button btnServicos = new Button("Gerenciar Serviços");
        Button btnAtendimentos = new Button("Iniciar Atendimento");
        Button btnAgendamentos = new Button("Listar Agendamentos");
        Button btnSair = new Button("Sair");

        VBox menu = new VBox(10);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(btnClientes, btnFuncionarios, btnServicos, btnAtendimentos, btnAgendamentos, btnSair);

        root.setCenter(menu);

        btnClientes.setOnAction(e -> mostrarClientes(primaryStage));
        btnFuncionarios.setOnAction(e -> mostrarFuncionarios(primaryStage));
        btnServicos.setOnAction(e -> mostrarServicos(primaryStage));
        btnAtendimentos.setOnAction(e -> iniciarAtendimento(primaryStage));
        btnAgendamentos.setOnAction(e -> listarAgendamentos(primaryStage));
        btnSair.setOnAction(e -> System.exit(0));

        Scene scene = new Scene(root, 1040, 720);
        scene.getStylesheets().add(getClass().getResource("petshop.css").toExternalForm());

        primaryStage.setTitle("Petshop Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mostrarClientes(Stage stage) {
        VBox vboxClientes = new VBox(10);
        vboxClientes.setAlignment(Pos.CENTER);

        TextField nomeClienteField = new TextField();
        nomeClienteField.setPromptText("Nome do Cliente");

        Button btnCadastrarCliente = new Button("Cadastrar Cliente");
        btnCadastrarCliente.setOnAction(e -> {
            String nome = nomeClienteField.getText();
            if (nome.isEmpty()) {
                showAlert(AlertType.WARNING, "Cadastro de Cliente", "Nome não pode ser vazio.");
            } else {
                Cliente cliente = new Cliente();
                cliente.setNomeCliente(nome);
                clientes.add(cliente);

                showAlert(AlertType.INFORMATION, "Cadastro de Cliente", "Cliente cadastrado com sucesso!");
                nomeClienteField.clear();
                atualizarListaClientes(vboxClientes);
            }
        });

        ListView<String> listaClientes = new ListView<>();
        vboxClientes.getChildren().addAll(nomeClienteField, btnCadastrarCliente, listaClientes);

        atualizarListaClientes(vboxClientes);

        Scene sceneClientes = new Scene(vboxClientes, 400, 300);
        stage.setScene(sceneClientes);
        stage.show();
    }

    private void atualizarListaClientes(VBox vbox) {
        ListView<String> listaClientes = null;
        for (var node : vbox.getChildren()) {
            if (node instanceof ListView) {
                listaClientes = (ListView<String>) node;
                break;
            }
        }
        if (listaClientes != null) {
            listaClientes.getItems().clear();
            listaClientes.getItems().addAll(getClientesNomes());
        }
    }

    private void mostrarFuncionarios(Stage stage) {
        VBox vboxFuncionarios = new VBox(10);
        vboxFuncionarios.setAlignment(Pos.CENTER);

        TextField nomeFuncionarioField = new TextField();
        nomeFuncionarioField.setPromptText("Nome do Funcionário");
        TextField funcaoFuncionarioField = new TextField();
        funcaoFuncionarioField.setPromptText("Função do Funcionário");

        Button btnCadastrarFuncionario = new Button("Cadastrar Funcionário");
        btnCadastrarFuncionario.setOnAction(e -> {
            String nome = nomeFuncionarioField.getText();
            String funcao = funcaoFuncionarioField.getText();
            if (nome.isEmpty() || funcao.isEmpty()) {
                showAlert(AlertType.WARNING, "Cadastro de Funcionário", "Campos não podem ser vazios.");
            } else {
                Funcionario funcionario = new Funcionario(nome, funcao);
                funcionarios.add(funcionario);
                showAlert(AlertType.INFORMATION, "Cadastro de Funcionário", "Funcionário cadastrado com sucesso!");
                nomeFuncionarioField.clear();
                funcaoFuncionarioField.clear();
                atualizarListaFuncionarios(vboxFuncionarios);
            }
        });

        ListView<String> listaFuncionarios = new ListView<>();
        vboxFuncionarios.getChildren().addAll(nomeFuncionarioField, funcaoFuncionarioField, btnCadastrarFuncionario, listaFuncionarios);

        atualizarListaFuncionarios(vboxFuncionarios);

        Scene sceneFuncionarios = new Scene(vboxFuncionarios, 400, 300);
        stage.setScene(sceneFuncionarios);
        stage.show();
    }

    private void atualizarListaFuncionarios(VBox vbox) {
        ListView<String> listaFuncionarios = null;
        for (var node : vbox.getChildren()) {
            if (node instanceof ListView) {
                listaFuncionarios = (ListView<String>) node;
                break;
            }
        }
        if (listaFuncionarios != null) {
            listaFuncionarios.getItems().clear();
            listaFuncionarios.getItems().addAll(getFuncionariosNomes());
        }
    }

    private void mostrarServicos(Stage stage) {
        VBox vboxServicos = new VBox(10);
        vboxServicos.setAlignment(Pos.CENTER);

        TextField nomeServicoField = new TextField();
        nomeServicoField.setPromptText("Nome do Serviço");

        TextField precoServicoField = new TextField();
        precoServicoField.setPromptText("Preço do Serviço");

        Button btnCadastrarServico = new Button("Cadastrar Serviço");
        btnCadastrarServico.setOnAction(e -> {
            String nome = nomeServicoField.getText();
            double preco;

            try {
                preco = Double.parseDouble(precoServicoField.getText());
            } catch (NumberFormatException ex) {
                showAlert(AlertType.WARNING, "Cadastro de Serviço", "Preço inválido.");
                return;
            }

            if (nome.isEmpty() || preco <= 0) {
                showAlert(AlertType.WARNING, "Cadastro de Serviço", "Nome ou preço inválido.");
            } else {
                Servico servico = new Servico(nome, preco);
                servicos.add(servico);
                showAlert(AlertType.INFORMATION, "Cadastro de Serviço", "Serviço cadastrado com sucesso!");
                nomeServicoField.clear();
                precoServicoField.clear();
                atualizarListaServicos(vboxServicos);
            }
        });

        ListView<String> listaServicos = new ListView<>();
        vboxServicos.getChildren().addAll(nomeServicoField, precoServicoField, btnCadastrarServico, listaServicos);

        atualizarListaServicos(vboxServicos);

        Scene sceneServicos = new Scene(vboxServicos, 400, 300);
        stage.setScene(sceneServicos);
        stage.show();
    }

    private void atualizarListaServicos(VBox vbox) {
        ListView<String> listaServicos = null;
        for (var node : vbox.getChildren()) {
            if (node instanceof ListView) {
                listaServicos = (ListView<String>) node;
                break;
            }
        }
        if (listaServicos != null) {
            listaServicos.getItems().clear();
            listaServicos.getItems().addAll(getServicosNomes());
        }
    }

    private void iniciarAtendimento(Stage stage) {
        showAlert(AlertType.INFORMATION, "Iniciar Atendimento", "Atendimento iniciado.");
    }

    private void listarAgendamentos(Stage stage) {
        VBox vboxAgendamentos = new VBox(10);
        vboxAgendamentos.setAlignment(Pos.CENTER);

        ListView<String> listaAgendamentos = new ListView<>();
        listaAgendamentos.getItems().addAll(getAgendamentos());

        vboxAgendamentos.getChildren().addAll(new Label("Agendamentos"), listaAgendamentos);
        Scene sceneAgendamentos = new Scene(vboxAgendamentos, 400, 300);

        stage.setScene(sceneAgendamentos);
        stage.show();
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Ajustado para refletir seu atributo 'nomeCliente' na classe Cliente
    private List<String> getClientesNomes() {
        List<String> nomes = new ArrayList<>();
        for (Cliente c : clientes) {
            nomes.add(c.getNomeCliente());
        }
        return nomes;
    }

    private List<String> getFuncionariosNomes() {
        List<String> nomes = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            nomes.add(f.getNome());
        }
        return nomes;
    }

    private List<String> getServicosNomes() {
        List<String> nomes = new ArrayList<>();
        for (Servico s : servicos) {
            nomes.add(s.getNome());
        }
        return nomes;
    }

    private List<String> getAgendamentos() {
        List<String> agendamentosStr = new ArrayList<>();
        for (Agendamento a : agendamentos) {
            agendamentosStr.add(a.toString());
        }
        return agendamentosStr;
    }
}
