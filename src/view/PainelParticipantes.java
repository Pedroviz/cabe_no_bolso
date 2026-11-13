package view;

import controller.PlanejamentoController;
import model.Participante;

import javax.swing.*;
import java.awt.*;

public class PainelParticipantes extends JPanel {
    private PlanejamentoController controlador;
    private JTextField txtCod, txtNome, txtOrcamento;
    private JList<Participante> listaParticipantes;
    private DefaultListModel<Participante> modeloLista;

    public PainelParticipantes(PlanejamentoController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        txtCod = new JTextField();
        txtNome = new JTextField();
        txtOrcamento = new JTextField();

        form.add(new JLabel(" Código:"));
        form.add(txtCod);
        form.add(new JLabel(" Nome:"));
        form.add(txtNome);
        form.add(new JLabel(" Orçamento (R$):"));
        form.add(txtOrcamento);

        JButton btnSalvar = new JButton("Cadastrar Participante");
        form.add(btnSalvar);
        add(form, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaParticipantes = new JList<>(modeloLista);
        add(new JScrollPane(listaParticipantes), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCod.getText());
                String nome = txtNome.getText();
                double orc = Double.parseDouble(txtOrcamento.getText());

                controlador.cadastrarParticipante(cod, nome, orc);
                JOptionPane.showMessageDialog(this, "Participante cadastrado com sucesso!");
                txtCod.setText(""); txtNome.setText(""); txtOrcamento.setText("");
                atualizarLista();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void atualizarLista() {
        modeloLista.clear();
        for (Participante p : controlador.getPlanejamento().getParticipantes()) {
            modeloLista.addElement(p);
        }
    }
}