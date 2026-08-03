package IMC;

public class IMC {

    //atributos privados
    private double peso;
    private double altura;
    private double imc;


    //metodo construtor

    public IMC() {
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
    }

    //get e set para os encapsulamentos dos atributos privados

    public double getpeso() {
        return peso;
    }

    public double getaltura() {return altura;}

    public double getimc() {return imc;}
    public void setimc(double imc) {
        this.imc = imc;
    }


    //métodos

    public double calcularImc() {
        this.imc = this.peso / (this.altura * this.altura);
        return this.imc;
    }

    public void cadastrarDados(double peso, double altura) {
        this.peso = peso;
        this.altura = altura;
    }
}