package IMC;

public class Situacao {
    private String situacao;

    public String verificarSituacao(IMC p){
        double n = p.getimc();

        if (n < 18.5){
            situacao = "Abaixo do peso.";
        } else if(n >= 18.5 && n < 24.9){
            situacao = "Peso normal (eutrofia).";
        } else if (n >= 24.9 && n < 29.9){
            situacao = "Sobrepeso.";
        } else if (n >= 29.9 && n < 34.9){
            situacao = "Obesidade Grau I";
        } else if (n >= 34.9 && n < 39.9){
            situacao = "Obesidade Grau II (severa)";
        } else if (n >= 40){
            situacao = "Obesidade Grau III (mórbida)";
        } else {
            situacao = "Inválido.";
        }

        return situacao;
    }
}