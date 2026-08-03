package IMC;

import javax.swing.*;

public class Entrada {
    private JPanel PainelPrincipal;
    private JTextField textField1;
    private JButton ENVIARButton;
    private JButton CANCELARButton;

    private JFrame frame;
    private String valorDigitado = null;
    private JLabel labelMensagem;

    public Entrada() {
        ENVIARButton.addActionListener(e -> {
            String entrada = textField1.getText().trim();
            if (entrada.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha o campo antes de enviar.");
                return;
            }
            // Valida se é número
            try {
                Double.parseDouble(entrada);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Digite apenas números!");
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
     * Abre a tela de entrada de dados e bloqueia até o usuário interagir.
     * @param titulo título da janela (ex: "Digite seu Peso")
     * @return o valor digitado como String, ou null se cancelou
     */
    public String mostrar(String titulo) {
        labelMensagem.setText(titulo); // ← atualiza o texto da label antes de abrir

        frame = new JFrame(titulo);
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
                        synchronized (Entrada.this) {
                            Entrada.this.notifyAll();
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

    /**
     * Versão que aceita qualquer texto (sem validação numérica), para entrada de nome.
     */
    public String mostrarParaTexto(String titulo) {
        // Redefine o listener do ENVIAR para não validar número
        for (java.awt.event.ActionListener al : ENVIARButton.getActionListeners()) {
            ENVIARButton.removeActionListener(al);
        }
        ENVIARButton.addActionListener(e -> {
            String entrada = textField1.getText().trim();
            if (entrada.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha o campo antes de enviar.");
                return;
            }
            valorDigitado = entrada;
            frame.dispose();
        });

        return mostrar(titulo);
    }

    public JPanel getPanelPrincipal() {
        return PainelPrincipal;
    }
}