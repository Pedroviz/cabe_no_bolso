package model;

import java.util.List;
import java.util.Map;

public class DespesaPorPessoa extends Despesa {
    private static final long serialVersionUID = 1L;

    public DespesaPorPessoa(String descricao, double valorUnitario, List<Participante> participantesEnvolvidos) {
        super(descricao, valorUnitario, participantesEnvolvidos);
    }

    @Override
    public void calcularRateio(Map<Participante, Double> mapaCustos) {
        for (Participante p : participantesEnvolvidos) {
            double custoAtual = mapaCustos.getOrDefault(p, 0.0);
            mapaCustos.put(p, custoAtual + valorInformado);
        }
    }
}
