package view;

import controller.PlanejamentoController;
import model.Despesa;
import model.DespesaFixa;
import model.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PainelResumo extends JPanel {
    private PlanejamentoController controlador;
    private JTextArea txtResumo;

    public PainelResumo(PlanejamentoController controlador, Runnable aoCarregarCallback) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));

        txtResumo = new JTextArea();
        txtResumo.setEditable(false);
        txtResumo.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(txtResumo), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout());
        JButton btnAtualizar = new JButton("Atualizar Relatório");
        JButton btnSalvar = new JButton("Salvar Arquivo (.dat)");
        JButton btnCarregar = new JButton("Carregar Arquivo (.dat)");
        JButton btnNovo = new JButton("Novo Planejamento Vazio");

        botoes.add(btnAtualizar);
        botoes.add(btnSalvar);
        botoes.add(btnCarregar);
        botoes.add(btnNovo);
        add(botoes, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> gerarResumo());

        btnSalvar.addActionListener(e -> {
            String arq = JOptionPane.showInputDialog(this, "Nome do arquivo para salvar:", "planejamento.dat");
            if (arq != null && !arq.trim().isEmpty()) {
                try {
                    controlador.salvarDados(arq.trim());
                    JOptionPane.showMessageDialog(this, "Planejamento salvo com sucesso em: " + arq);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo: " + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCarregar.addActionListener(e -> {
            String arq = JOptionPane.showInputDialog(this, "Nome do arquivo para carregar:", "planejamento.dat");
            if (arq != null && !arq.trim().isEmpty()) {
                try {
                    controlador.carregarDados(arq.trim());
                    if (aoCarregarCallback != null) {
                        aoCarregarCallback.run();
                    }
                    gerarResumo();
                    JOptionPane.showMessageDialog(this, "Planejamento recuperado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Falha ao carregar arquivo (planejamento atual preservado):\n" + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNovo.addActionListener(e -> {
            controlador.novoPlanejamento();
            if (aoCarregarCallback != null) {
                aoCarregarCallback.run();
            }
            gerarResumo();
            JOptionPane.showMessageDialog(this, "Iniciado um novo planejamento vazio.");
        });
    }

    public void gerarResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE PLANEJAMENTO (CABE NO BOLSO) ===\n\n");

        sb.append("--- DESPESAS REGISTRADAS ---\n");
        for (Despesa d : controlador.getPlanejamento().getDespesas()) {
            String tipo = (d instanceof DespesaFixa) ? "Fixa" : "Por Pessoa";
            sb.append("• ").append(d.getDescricao())
              .append(" (").append(tipo).append(") - Valor Informado: R$ ")
              .append(String.format("%.2f", d.getValorInformado())).append("\n");
        }

        sb.append("\n--- SITUAÇÃO POR PARTICIPANTE ---\n");
        Map<Participante, Double> custos = controlador.obterResumoCustos();
        for (Map.Entry<Participante, Double> entry : custos.entrySet()) {
            Participante p = entry.getKey();
            double gasto = entry.getValue();
            sb.append("Cód ").append(p.getCodigo()).append(" - ").append(p.getNome())
              .append(" | Orçamento: R$ ").append(String.format("%.2f", p.getOrcamento()))
              .append(" | Gasto Individual: R$ ").append(String.format("%.2f", gasto));

            if (gasto > p.getOrcamento()) {
                sb.append(" -> EXCEDEU O ORÇAMENTO EM R$ ")
                  .append(String.format("%.2f", gasto - p.getOrcamento())).append("\n");
            } else {
                sb.append(" -> Dentro do orçamento.\n");
            }
        }

        sb.append("\nCUSTO TOTAL GERAL DO PASSEIO: R$ ")
          .append(String.format("%.2f", controlador.getPlanejamento().getCustoTotalGeral())).append("\n");

        txtResumo.setText(sb.toString());
    }
}