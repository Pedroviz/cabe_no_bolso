package model;

import java.io.Serializable;
import java.util.List;

public abstract class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String descricao;
    protected double valorInformado;
    protected List<Participante> participantesEnvolvidos;

    public Despesa(String descricao, double valorInformado, List<Participante> participantesEnvolvidos) {
        setDescricao(descricao);
        setValorInformado(valorInformado);
        setParticipantesEnvolvidos(participantesEnvolvidos);
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) throw new IllegalArgumentException("Descrição inválida.");
        this.descricao = descricao;
    }

    public double getValorInformado() { return valorInformado; }
    public void setValorInformado(double valorInformado) {
        if (valorInformado <= 0) throw new IllegalArgumentException("O valor da despesa deve ser maior que zero.");
        this.valorInformado = valorInformado;
    }

    public List<Participante> getParticipantesEnvolvidos() { return participantesEnvolvidos; }
    public void setParticipantesEnvolvidos(List<Participante> participantesEnvolvidos) {
        if (participantesEnvolvidos == null || participantesEnvolvidos.isEmpty()) {
            throw new IllegalArgumentException("A despesa deve ter pelo menos um participante.");
        }
        this.participantesEnvolvidos = participantesEnvolvidos;
    }

    // Método polimórfico a ser implementado pelas subclasses
    public abstract void calcularRateio(java.util.Map<Participante, Double> mapaCustos);
}