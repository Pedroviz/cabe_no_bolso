package view;

import controller.PlanejamentoController;
import model.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PainelDespesas extends JPanel {
    private PlanejamentoController controlador;
    private JTextField txtDesc, txtValor;
    private JComboBox<String> cmbTipo;
    private JPanel panelCheckboxes;
    private List<JCheckBox> listaChks;

    public PainelDespesas(PlanejamentoController controlador) {
        this.controlador = controlador;
        this.listaChks = new ArrayList<>();
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        txtDesc = new JTextField();
        txtValor = new JTextField();
        cmbTipo = new JComboBox<>(new String[]{"Fixa (Dividida)", "Por Pessoa (Unitária)"});

        form.add(new JLabel(" Descrição:"));
        form.add(txtDesc);
        form.add(new JLabel(" Valor (R$):"));
        form.add(txtValor);
        form.add(new JLabel(" Tipo de Despesa:"));
        form.add(cmbTipo);
        form.add(new JLabel(" Participantes Envolvidos:"));

        panelCheckboxes = new JPanel();
        panelCheckboxes.setLayout(new BoxLayout(panelCheckboxes, BoxLayout.Y_AXIS));
        form.add(new JScrollPane(panelCheckboxes));

        JButton btnAdicionar = new JButton("Adicionar Despesa");
        JPanel sul = new JPanel();
        sul.add(btnAdicionar);

        add(form, BorderLayout.CENTER);
        add(sul, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            try {
                String desc = txtDesc.getText();
                double val = Double.parseDouble(txtValor.getText());
                int tipo = cmbTipo.getSelectedIndex();

                List<Participante> envolvidos = new ArrayList<>();
                for (int i = 0; i < listaChks.size(); i++) {
                    if (listaChks.get(i).isSelected()) {
                        envolvidos.add(controlador.getPlanejamento().getParticipantes().get(i));
                    }
                }

                if (tipo == 0) {
                    controlador.cadastrarDespesaFixa(desc, val, envolvidos);
                } else {
                    controlador.cadastrarDespesaPorPessoa(desc, val, envolvidos);
                }

                JOptionPane.showMessageDialog(this, "Despesa cadastrada com sucesso!");
                txtDesc.setText(""); txtValor.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void atualizarCheckboxes() {
        panelCheckboxes.removeAll();
        listaChks.clear();
        for (Participante p : controlador.getPlanejamento().getParticipantes()) {
            JCheckBox chk = new JCheckBox(p.getNome() + " (Cód: " + p.getCodigo() + ")");
            listaChks.add(chk);
            panelCheckboxes.add(chk);
        }
        panelCheckboxes.revalidate();
        panelCheckboxes.repaint();
    }
}