package IMC;

import javax.swing.*;

public class TelaIMC {
    private JPanel PainelPrincipal;
    private JTextField textField1;
    private JButton ENVIARButton;
    private JButton CANCELARButton;

    private JFrame frame;
    private String valorDigitado = null; // null = cancelado, string = valor enviado

    public TelaIMC() {
        ENVIARButton.addActionListener(e -> {
            String entrada = textField1.getText().trim();
            if (entrada.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Digite uma opção antes de enviar.");
                return;
            }
            valorDigitado = entrada;
            frame.dispose();
        });

        CANCELARButton.addActionListener(e -> {
            valorDigitado = null;
            frame.dispose();
        });
    }

    /**
     * Abre a tela do menu e bloqueia até o usuário interagir.
     * @return a string digitada, ou null se cancelou
     */
    public String mostrar() {
        frame = new JFrame("Calculadora de IMC");
        frame.setContentPane(PainelPrincipal);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Espera a janela fechar
        try {
            synchronized (this) {
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        synchronized (TelaIMC.this) {
                            TelaIMC.this.notifyAll();
                        }
                    }
                });
                this.wait();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        return valorDigitado;
    }

    public JPanel getPanelPrincipal() {
        return PainelPrincipal;
    }
}