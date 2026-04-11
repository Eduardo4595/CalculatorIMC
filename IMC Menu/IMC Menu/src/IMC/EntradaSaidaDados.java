package IMC;
import javax.swing.JOptionPane;

public class EntradaSaidaDados {
    public String entradaDados(String mensagemEntrada) {

        String mensagemOriginal = mensagemEntrada;
        String entrada = "";
        boolean valido = false;

        // Laço de repeticao para validação da entrada do usuário - JOptionPane
        do {
            // Variavel de controle para a validação, nela será atribuida oq o usuário digitar e mais tarde utilizada para verificar se é valida
            entrada = JOptionPane.showInputDialog(mensagemOriginal);

            if (entrada == null || entrada.trim().isEmpty()) { //Verifica se o usuário clicou em ok sem preencher o campo
                JOptionPane.showMessageDialog(null, "Voce precisa preencher o campo com o valor");
            } else {
                try {
                    Double.parseDouble(entrada);
                    valido = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Digite apenas numeros!");
                }
            }
        } while (!valido);
        return entrada;
    }

    public String entradaDadosString(String mensagemEntrada) {

        String mensagemOriginal = mensagemEntrada;
        String entrada = "";
        boolean valido = false;

        // Laço de repeticao para validação da entrada do usuário - JOptionPane
        do {
            // Variavel de controle para a validação, nela será atribuida oq o usuário digitar e mais tarde utilizada para verificar se é valida
            entrada = JOptionPane.showInputDialog(mensagemOriginal);

            if (entrada == null || entrada.trim().isEmpty()) { //Verifica se o usuário clicou em ok sem preencher o campo
                JOptionPane.showMessageDialog(null, "Voce precisa preencher o campo com o valor");
            } else {
                valido = true;
            }
        } while (!valido);
        return entrada;
    }

    public void saidaDados(String mensagemSaida) {

        JOptionPane.showMessageDialog(null, mensagemSaida);
    }
}