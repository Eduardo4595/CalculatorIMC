package IMC;

import javax.swing.*;

public class Info {
    private JPanel PainelPrincipal;
    private JButton OKButton;

    // Bindings das 3 labels — adicione-os no Info.form conforme a nota abaixo
    private JLabel labelPeso;
    private JLabel labelAltura;
    private JLabel labelIMC;

    private JFrame frame;

    public Info() {
        OKButton.addActionListener(e -> frame.dispose());
    }

    /**
     * Abre a tela Info exibindo peso, altura e IMC.
     * @param peso   valor do peso (ex: 68.0)
     * @param altura valor da altura (ex: 1.77)
     * @param imc    valor do IMC formatado (ex: "23,26")
     */
    public void mostrar(double peso, double altura, String imc) {
        // ATENÇÃO: as labels precisam ter binding definido no Info.form (veja nota abaixo)
        if (labelPeso != null)   labelPeso.setText("Peso: " + peso + "kg");
        if (labelAltura != null) labelAltura.setText("Altura: " + altura + "m");
        if (labelIMC != null)    labelIMC.setText("IMC: " + imc + "kg/m²");

        frame = new JFrame("Dados Cadastrados");
        frame.setContentPane(PainelPrincipal);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            synchronized (this) {
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        synchronized (Info.this) {
                            Info.this.notifyAll();
                        }
                    }
                });
                this.wait();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public JPanel getPanelPrincipal() {
        return PainelPrincipal;
    }
}

/*
 * NOTA IMPORTANTE — Info.form:
 * As 3 JLabels (Peso, Altura, IMC) no Info.form não têm binding definido.
 * Para que os valores sejam atualizados dinamicamente, abra o Info.form no IntelliJ,
 * clique em cada label e defina os field names assim:
 *   - Label "Peso: 68kg"       → field name: labelPeso
 *   - Label "Altura: 1.77m"    → field name: labelAltura
 *   - Label "IMC: 23,26kg/m²"  → field name: labelIMC
 * Salve o .form e o IntelliJ atualizará o código gerado automaticamente.
 */