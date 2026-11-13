package view;

import controller.PlanejamentoController;
import model.Participante;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PainelResumo extends JPanel {
    private PlanejamentoController controlador;
    private JTextArea txtResumo;

    public PainelResumo(PlanejamentoController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());

        txtResumo = new JTextArea();
        txtResumo.setEditable(false);
        add(new JScrollPane(txtResumo), BorderLayout.CENTER);

        JButton btnAtualizar = new JButton("Atualizar Relatório");
        add(btnAtualizar, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> gerarResumo());
    }

    public void gerarResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE PLANEJAMENTO (CABE NO BOLSO) ===\n\n");
        Map<Participante, Double> custos = controlador.obterResumoCustos();
        
        for (Map.Entry<Participante, Double> entry : custos.entrySet()) {
            Participante p = entry.getKey();
            double gasto = entry.getValue();
            sb.append("Participante: ").append(p.getNome())
              .append(" | Orçamento: R$ ").append(String.format("%.2f", p.getOrcamento()))
              .append(" | Gasto Total: R$ ").append(String.format("%.2f", gasto));
            
            if (gasto > p.getOrcamento()) {
                sb.append(" -> ESTOUROU O ORÇAMENTO! (Excedeu R$ ")
                  .append(String.format("%.2f", gasto - p.getOrcamento())).append(")\n");
            } else {
                sb.append(" -> Dentro do orçamento.\n");
            }
        }
        sb.append("\nCusto Total Geral: R$ ")
          .append(String.format("%.2f", controlador.getPlanejamento().getCustoTotalGeral()));
        
        txtResumo.setText(sb.toString());
    }
}