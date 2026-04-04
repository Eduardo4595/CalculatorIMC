package IMC;

import javax.swing.*;
import java.text.DecimalFormat;

public class MenuIMC {
    private IMC imc;
    private int opcao;
    private ConversorNumeros conversor;
    private EntradaSaidaDados io;
    private Situacao situacao;
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
                io.saidaDados(saida);
                break;

            case 0:
                System.exit(0);
                break;
        }
    }
}