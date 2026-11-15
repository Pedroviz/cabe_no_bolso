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
        setLayout(new BorderLayout(10, 10));

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

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnSalvar = new JButton("Cadastrar");
        JButton btnEditar = new JButton("Atualizar Nome/Orçamento");
        JButton btnExcluir = new JButton("Excluir Selecionado");
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        form.add(new JLabel(" Ações:"));
        form.add(painelBotoes);
        add(form, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaParticipantes = new JList<>(modeloLista);
        add(new JScrollPane(listaParticipantes), BorderLayout.CENTER);

        listaParticipantes.addListSelectionListener(e -> {
            Participante sel = listaParticipantes.getSelectedValue();
            if (sel != null) {
                txtCod.setText(String.valueOf(sel.getCodigo()));
                txtNome.setText(sel.getNome());
                txtOrcamento.setText(String.valueOf(sel.getOrcamento()));
            }
        });

        btnSalvar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCod.getText().trim());
                String nome = txtNome.getText();
                double orc = Double.parseDouble(txtOrcamento.getText().trim().replace(",", "."));

                controlador.cadastrarParticipante(cod, nome, orc);
                JOptionPane.showMessageDialog(this, "Participante cadastrado com sucesso!");
                limparCampos();
                atualizarLista();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Informe valores numéricos válidos para código e orçamento.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEditar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCod.getText().trim());
                String nome = txtNome.getText();
                double orc = Double.parseDouble(txtOrcamento.getText().trim().replace(",", "."));

                controlador.editarParticipante(cod, nome, orc);
                JOptionPane.showMessageDialog(this, "Dados do participante atualizados com sucesso!");
                limparCampos();
                atualizarLista();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Selecione um participante e informe um orçamento válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                Participante sel = listaParticipantes.getSelectedValue();
                controlador.excluirParticipante(sel);
                JOptionPane.showMessageDialog(this, "Participante excluído com sucesso!");
                limparCampos();
                atualizarLista();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void limparCampos() {
        txtCod.setText("");
        txtNome.setText("");
        txtOrcamento.setText("");
    }

    public void atualizarLista() {
        modeloLista.clear();
        for (Participante p : controlador.getPlanejamento().getParticipantes()) {
            modeloLista.addElement(p);
        }
    }
}