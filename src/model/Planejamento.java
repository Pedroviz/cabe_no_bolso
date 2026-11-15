package model;

import java.io.Serializable;
import java.util.*;

public class Planejamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Participante> participantes;
    private List<Despesa> despesas;

    public Planejamento() {
        this.participantes = new ArrayList<>();
        this.despesas = new ArrayList<>();
    }

    public void adicionarParticipante(Participante p) {
        for (Participante existente : participantes) {
            if (existente.getCodigo() == p.getCodigo()) {
                throw new IllegalArgumentException("Já existe um participante com este código.");
            }
        }
        participantes.add(p);
        participantes.sort(Comparator.comparingInt(Participante::getCodigo));
    }

    public void atualizarParticipante(int codigo, String novoNome, double novoOrcamento) {
        for (Participante p : participantes) {
            if (p.getCodigo() == codigo) {
                p.setNome(novoNome);
                p.setOrcamento(novoOrcamento);
                return;
            }
        }
        throw new IllegalArgumentException("Participante não encontrado.");
    }

    public void adicionarDespesa(Despesa d) {
        despesas.add(d);
    }

    public void atualizarDespesa(int index, Despesa novaDespesa) {
        if (index < 0 || index >= despesas.size()) {
            throw new IllegalArgumentException("Selecione uma despesa válida para editar.");
        }
        despesas.set(index, novaDespesa);
    }

    public void removerDespesa(int index) {
        if (index < 0 || index >= despesas.size()) {
            throw new IllegalArgumentException("Selecione uma despesa na lista para excluir.");
        }
        despesas.remove(index);
    }

    public boolean participantePossuiDespesa(Participante p) {
        for (Despesa d : despesas) {
            for (Participante env : d.getParticipantesEnvolvidos()) {
                if (env.getCodigo() == p.getCodigo()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removerParticipante(Participante p) {
        if (p == null) {
            throw new IllegalArgumentException("Selecione um participante para excluir.");
        }
        if (participantePossuiDespesa(p)) {
            throw new IllegalStateException("Não é possível excluir participante vinculado a despesas.");
        }
        participantes.remove(p);
    }

    public List<Participante> getParticipantes() { return participantes; }
    public List<Despesa> getDespesas() { return despesas; }

    public Map<Participante, Double> calcularCustosTotais() {
        Map<Participante, Double> mapaCustos = new LinkedHashMap<>();
        for (Participante p : participantes) {
            mapaCustos.put(p, 0.0);
        }
        for (Despesa d : despesas) {
            d.calcularRateio(mapaCustos);
        }
        return mapaCustos;
    }

    public double getCustoTotalGeral() {
        Map<Participante, Double> custos = calcularCustosTotais();
        double total = 0;
        for (double val : custos.values()) {
            total += val;
        }
        return Math.round(total * 100.0) / 100.0;
    }
}