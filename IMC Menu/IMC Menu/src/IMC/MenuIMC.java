package IMC;

import javax.swing.*;
import java.text.DecimalFormat;
import java.nio.file.*;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class MenuIMC {
    private IMC imc;
    private int opcao;
    private ConversorNumeros conversor;
    private EntradaSaidaDados io;
    private Situacao situacao;
    String Situacao;
    DecimalFormat df = new DecimalFormat("0.##");

    public MenuIMC() {
        this.imc = new IMC();
        this.opcao = -1;
        this.imc = new IMC();
        this.conversor = new ConversorNumeros();
        this.io = new EntradaSaidaDados();
        this.situacao = new Situacao();
    }

    public void executarIMC() {
        do {
            this.executarMenuPrincipal();
            this.avaliarOpcaoEscolhida();
        } while (this.opcao != 0);
    }

    private void executarMenuPrincipal() {
        String mensagemMenu = "Selecione uma opção "
                + "\n 1 - Cadastrar Peso e Altura"
                + "\n 2 - Consultar Dados Cadastrados"
                + "\n 3 - Calcular IMC"
                + "\n 4 - Verificar Situação"
                + "\n 0 - Sair";

        String entradaDados = io.entradaDados(mensagemMenu);
        try {

            this.opcao = conversor.StringToInt(entradaDados);
            if (opcao < -1 || opcao > 4) {
                io.saidaDados("Digite um número que seja uma das oções do Menu.");
                this.opcao = -1;
            }

        } catch (NumberFormatException e) {
            this.opcao = -1;
            io.saidaDados("Digite apenas números!");
        }
    }

    public void avaliarOpcaoEscolhida() {
        String saida;
        double peso = 0;
        double altura = 0;
        double resultado_imc = 0;

        if (this.opcao == 1) {

            String mensagemEntrada = "Digite seu Peso";
            peso = conversor.stringToDouble(io.entradaDados(mensagemEntrada));


            mensagemEntrada = "Digite sua Altura";
            altura = conversor.stringToDouble(io.entradaDados(mensagemEntrada));

            imc.cadastrarDados(peso,altura);

        }


        switch (this.opcao) {

            case 2:
                if (imc.getpeso() != 0) {
                    io.saidaDados("Peso: " + imc.getpeso() + "kg \nAltura: " + imc.getaltura() + "cm \nIMC: " +  df.format(imc.getimc()) +"kg/m²");
                    break;
                } else {
                    io.saidaDados("Nenhum dado Registrado");
                    break;
                }

            case 3:
                imc.calcularImc();
                saida = "IMC: " + df.format(imc.getimc()) +"kg/m²";
                io.saidaDados(saida);
                break;

            case 4:
                saida = situacao.verificarSituacao(imc);
                Situacao = saida;
                io.saidaDados(saida);
                break;

            case 0:
                if (imc.getimc() != 0 && imc.getpeso() != 0 && imc.getaltura() != 0) {
                    String mensagemEntrada = "Você deseja salvar as suas informações? \n" +
                            "Digite: \n" +
                            "1 - Sim \n" +
                            "2 - Não \n" ;
                    Double resposta = conversor.stringToDouble(io.entradaDados(mensagemEntrada));

                    if (resposta == 1) {
                        mensagemEntrada = "Digite o seu nome:";
                        String nome = io.entradaDadosString(mensagemEntrada);
                        String registro = "Nome: " + nome + "\n" +
                                "Peso: " +  imc.getpeso() + "kg\n" +
                                "Altura: " +  imc.getaltura() + "m\n" +
                                "IMC: " +  df.format(imc.getimc()) + "kg/m²\n" +
                                "Situação: " + Situacao + "\n" +
                                "--------------------------------------- \n\n";

                        try {
                            Files.writeString(
                                    Path.of("E:\\IMC Menu\\IMC Menu\\Registros\\imc.txt"),
                                    registro,
                                    StandardOpenOption.CREATE,
                                    StandardOpenOption.APPEND
                            );
                            io.saidaDados("Dados salvos com sucesso!\nAcesse o arquivo .txt chamdo \"imc\" no caminho \"E:\\IMC Menu\\IMC Menu\\Registros\\imc.txt\" para ter acesso aos seus dados. ");
                        } catch (IOException e) {
                            io.saidaDados("Erro ao salvar arquivo, Tente novamente.");
                            e.printStackTrace();
                        }
                    } else {
                        System.exit(0);
                    }

                } else {
                    System.exit(0);
                }
                break;
        }
    }
}