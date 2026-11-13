package model;

import java.util.List;
import java.util.Map;

public class DespesaFixa extends Despesa {
    private static final long serialVersionUID = 1L;

    public DespesaFixa(String descricao, double valorInformado, List<Participante> participantesEnvolvidos) {
        super(descricao, valorInformado, participantesEnvolvidos);
    }

    @Override
    public void calcularRateio(Map<Participante, Double> mapaCustos) {
        int qtd = participantesEnvolvidos.size();
        double valorBase = Math.floor((valorInformado / qtd) * 100.0) / 100.0;
        double resto = Math.round((valorInformado - (valorBase * qtd)) * 100.0);

        // Ordena participantes por código para distribuição justa dos centavos restantes
        participantesEnvolvidos.sort((p1, p2) -> Integer.compare(p1.getCodigo(), p2.getCodigo()));

        for (int i = 0; i < qtd; i++) {
            Participante p = participantesEnvolvidos.get(i);
            double acrescimoCentavo = (i < resto) ? 0.01 : 0.0;
            double custoIndividual = valorBase + acrescimoCentavo;
            
            mapaCustos.put(p, mapaCustos.getOrDefault(p, 0.0) + custoIndividual);
        }
    }
}