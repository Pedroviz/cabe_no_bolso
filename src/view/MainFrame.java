package view;

import controller.PlanejamentoController;
import javax.swing.*;

public class MainFrame extends JFrame {
    private PlanejamentoController controlador;
    private PainelParticipantes painelParticipantes;
    private PainelDespesas painelDespesas;
    private PainelResumo painelResumo;

    public MainFrame() {
        controlador = new PlanejamentoController();

        setTitle("Cabe no Bolso - Planejador de Despesas Compartilhadas");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        painelParticipantes = new PainelParticipantes(controlador);
        painelDespesas = new PainelDespesas(controlador);
        painelResumo = new PainelResumo(controlador, () -> {
            painelParticipantes.atualizarLista();
            painelDespesas.atualizarCheckboxes();
        });

        abas.add("Participantes", painelParticipantes);
        abas.add("Despesas", painelDespesas);
        abas.add("Resumo", painelResumo);

        abas.addChangeListener(e -> {
            if (abas.getSelectedComponent() == painelDespesas) {
                painelDespesas.atualizarCheckboxes();
            } else if (abas.getSelectedComponent() == painelResumo) {
                painelResumo.gerarResumo();
            }
        });

        add(abas);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}