package view;

import controller.PlanejamentoController;
import model.Despesa;
import model.DespesaFixa;
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
    private DefaultListModel<String> modeloDespesas;
    private JList<String> listaDespesasUI;

    public PainelDespesas(PlanejamentoController controlador) {
        this.controlador = controlador;
        this.listaChks = new ArrayList<>();
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
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

        add(form, BorderLayout.NORTH);

        modeloDespesas = new DefaultListModel<>();
        listaDespesasUI = new JList<>(modeloDespesas);
        JScrollPane scrollLista = new JScrollPane(listaDespesasUI);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Despesas Cadastradas (Selecione para Editar ou Excluir)"));
        add(scrollLista, BorderLayout.CENTER);

        JPanel sul = new JPanel(new FlowLayout());
        JButton btnAdicionar = new JButton("Adicionar Despesa");
        JButton btnEditar = new JButton("Editar Selecionada");
        JButton btnExcluir = new JButton("Excluir Selecionada");
        sul.add(btnAdicionar);
        sul.add(btnEditar);
        sul.add(btnExcluir);
        add(sul, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            try {
                String desc = txtDesc.getText();
                double val = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
                int tipo = cmbTipo.getSelectedIndex();
                List<Participante> envolvidos = obterSelecionados();

                if (tipo == 0) {
                    controlador.cadastrarDespesaFixa(desc, val, envolvidos);
                } else {
                    controlador.cadastrarDespesaPorPessoa(desc, val, envolvidos);
                }

                JOptionPane.showMessageDialog(this, "Despesa cadastrada com sucesso!");
                txtDesc.setText("");
                txtValor.setText("");
                atualizarListaDespesas();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Informe um valor numérico válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEditar.addActionListener(e -> {
            try {
                int idx = listaDespesasUI.getSelectedIndex();
                String desc = txtDesc.getText();
                double val = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
                boolean fixa = (cmbTipo.getSelectedIndex() == 0);
                List<Participante> envolvidos = obterSelecionados();

                controlador.editarDespesa(idx, desc, val, fixa, envolvidos);
                JOptionPane.showMessageDialog(this, "Despesa atualizada com sucesso!");
                atualizarListaDespesas();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Informe um valor numérico válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int idx = listaDespesasUI.getSelectedIndex();
                controlador.excluirDespesa(idx);
                JOptionPane.showMessageDialog(this, "Despesa excluída com sucesso!");
                atualizarListaDespesas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private List<Participante> obterSelecionados() {
        List<Participante> envolvidos = new ArrayList<>();
        List<Participante> todos = controlador.getPlanejamento().getParticipantes();
        for (int i = 0; i < listaChks.size(); i++) {
            if (listaChks.get(i).isSelected()) {
                envolvidos.add(todos.get(i));
            }
        }
        return envolvidos;
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
        atualizarListaDespesas();
    }

    public void atualizarListaDespesas() {
        modeloDespesas.clear();
        List<Despesa> despesas = controlador.getPlanejamento().getDespesas();
        for (int i = 0; i < despesas.size(); i++) {
            Despesa d = despesas.get(i);
            String tipo = (d instanceof DespesaFixa) ? "Fixa" : "Por Pessoa";
            modeloDespesas.addElement((i + 1) + ". " + d.getDescricao() + " | R$ " + String.format("%.2f", d.getValorInformado()) + " | Tipo: " + tipo);
        }
    }
}