package IMC;

import java.text.DecimalFormat;
import java.nio.file.*;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class MenuIMC {
    private IMC imc;
    private int opcao;
    private ConversorNumeros conversor;
    private Situacao situacao;
    private String situacaoTexto;

    DecimalFormat df = new DecimalFormat("0.##");

    public MenuIMC() {
        this.imc = new IMC();
        this.opcao = -1;
        this.conversor = new ConversorNumeros();
        this.situacao = new Situacao();
    }

    public void executarIMC() {
        do {
            this.executarMenuPrincipal();
            this.avaliarOpcaoEscolhida();
        } while (this.opcao != 0);
    }

    // -------------------------------------------------------------------------
    // Utilitários para abrir cada tela
    // -------------------------------------------------------------------------

    /** Abre o menu principal e retorna a opção digitada, ou -1 se cancelou. */
    private void executarMenuPrincipal() {
        TelaIMC tela = new TelaIMC();
        String entrada = tela.mostrar();

        if (entrada == null) {
            this.opcao = 0; // usuário cancelou — encerra o programa
            return;
        }

        try {
            this.opcao = conversor.StringToInt(entrada);
            if (this.opcao < 0 || this.opcao > 4) {
                mostrarSaida("Digite um número que seja uma das opções do Menu.", "Opção inválida");
                this.opcao = -1;
            }
        } catch (NumberFormatException e) {
            mostrarSaida("Digite apenas números!", "Erro");
            this.opcao = -1;
        }
    }

    /** Abre a tela de entrada numérica e retorna o double, ou -1 se cancelou. */
    private double pedirNumero(String titulo) {
        while (true) {
            Entrada tela = new Entrada();
            String entrada = tela.mostrar(titulo);
            if (entrada == null) return -1; // cancelou
            try {
                return conversor.stringToDouble(entrada);
            } catch (NumberFormatException e) {
                mostrarSaida("Digite apenas números!", "Erro");
            }
        }
    }

    /** Abre a tela de entrada de texto livre e retorna a string, ou null se cancelou. */
    private String pedirTexto(String titulo) {
        Entrada tela = new Entrada();
        return tela.mostrarParaTexto(titulo);
    }

    /** Abre a tela Saída com uma mensagem e aguarda o usuário clicar OK. */
    private void mostrarSaida(String mensagem, String titulo) {
        Saída tela = new Saída();
        tela.mostrar(mensagem, titulo);
    }

    // -------------------------------------------------------------------------
    // Lógica principal
    // -------------------------------------------------------------------------

    public void avaliarOpcaoEscolhida() {
        switch (this.opcao) {

            case 1:
                double peso = pedirNumero("Digite seu Peso (kg)");
                if (peso == -1) break; // cancelou

                double altura = pedirNumero("Digite sua Altura (m)");
                if (altura == -1) break; // cancelou

                imc.cadastrarDados(peso, altura);
                mostrarSaida("Dados cadastrados com sucesso!", "Sucesso");
                break;

            case 2:
                if (imc.getpeso() != 0) {
                    Info telaInfo = new Info();
                    telaInfo.mostrar(imc.getpeso(), imc.getaltura(), df.format(imc.getimc()));
                } else {
                    mostrarSaida("Nenhum dado registrado.", "Aviso");
                }
                break;

            case 3:
                imc.calcularImc();
                mostrarSaida("IMC: " + df.format(imc.getimc()) + "kg/m²", "Resultado do IMC");
                break;

            case 4:
                String saida = situacao.verificarSituacao(imc);
                situacaoTexto = saida;
                mostrarSaida(saida, "Situação");
                break;

            case 0:
                if (imc.getimc() != 0 && imc.getpeso() != 0 && imc.getaltura() != 0) {

                    // Pergunta se deseja salvar — usa a própria tela de entrada
                    Entrada telaResposta = new Entrada();
                    String respostaStr = telaResposta.mostrar("Salvar suas informações? Digite 1 para salvar, 2 para não salvar.");
                    if (respostaStr == null) break;

                    double resposta = conversor.stringToDouble(respostaStr);

                    if (resposta == 1) {
                        String nome = pedirTexto("Digite o seu nome");
                        if (nome == null) break;

                        String registro = "Nome: " + nome + "\n" +
                                "Peso: " + imc.getpeso() + "kg\n" +
                                "Altura: " + imc.getaltura() + "m\n" +
                                "IMC: " + df.format(imc.getimc()) + "kg/m²\n" +
                                "Situação: " + situacaoTexto + "\n" +
                                "--------------------------------------- \n\n";

                        try {
                            Files.writeString(
                                    Path.of("C:\\Users\\Claudio Henrique\\Documents\\ProjetosJava\\CalculatorIMC-main\\IMC Menu\\IMC Menu\\Registros"),
                                    registro,
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.APPEND
                            );
                            mostrarSaida(
                                    "Dados salvos com sucesso!\nAcesse o arquivo em:\nE:\\IMC Menu\\IMC Menu\\Registros\\imc.txt",
                                    "Salvo"
                            );
                        } catch (IOException e) {
                            mostrarSaida("Erro ao salvar arquivo. Tente novamente.", "Erro");
                            e.printStackTrace();
                        }
                    }
                }
                System.exit(0);
                break;
        }
    }
}