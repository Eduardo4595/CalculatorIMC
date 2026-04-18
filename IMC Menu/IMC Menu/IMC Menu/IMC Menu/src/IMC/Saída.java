package IMC;

import javax.swing.*;

public class Saída {
    private JPanel PainelPrincipal;
    private JLabel labelMensagem; // label com binding "labelMensagem" no .form — veja nota abaixo
    private JButton OKButton;

    private JFrame frame;

    public Saída() {
        OKButton.addActionListener(e -> frame.dispose());
    }

    /**
     * Abre a tela de saída exibindo uma mensagem e bloqueia até o usuário clicar OK.
     * @param mensagem texto a exibir na label
     * @param titulo   título da janela
     */
    public void mostrar(String mensagem, String titulo) {
        // ATENÇÃO: para o texto aparecer dinamicamente, adicione binding="labelMensagem"
        // na JLabel do Saída.form. Se ainda não fez isso, veja a nota no final do arquivo.
        if (labelMensagem != null) {
            labelMensagem.setText(mensagem);
        }

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
                        synchronized (Saída.this) {
                            Saída.this.notifyAll();
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
 * NOTA IMPORTANTE — Saída.form:
 * A JLabel de texto no seu Saída.form não tem binding definido.
 * Para que o texto seja atualizado dinamicamente, abra o Saída.form no IntelliJ,
 * clique na JLabel (a que exibe "Label"), vá em Properties > field name
 * e defina como "labelMensagem". Salve o .form e o IntelliJ atualizará o código gerado.
 */