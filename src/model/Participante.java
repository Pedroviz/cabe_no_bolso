package model;

import java.io.Serializable;

public class Participante implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int codigo;
    private String nome;
    private double orcamento;

    public Participante(int codigo, String nome, double orcamento) {
        setCodigo(codigo);
        setNome(nome);
        setOrcamento(orcamento);
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) {
        if (codigo <= 0) throw new IllegalArgumentException("Código deve ser maior que zero.");
        this.codigo = codigo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio.");
        this.nome = nome;
    }

    public double getOrcamento() { return orcamento; }
    public void setOrcamento(double orcamento) {
        if (orcamento < 0) throw new IllegalArgumentException("Orçamento não pode ser negativo.");
        this.orcamento = orcamento;
    }

    @Override
    public String toString() {
        return "ID: " + codigo + " | Nome: " + nome + " | Orçamento: R$ " + String.format("%.2f", orcamento);
    }
}