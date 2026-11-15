package controller;

import model.*;
import persistence.PersistenciaService;
import persistence.ArquivoPersistenciaService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PlanejamentoController {
    private Planejamento planejamento;
    private PersistenciaService persistencia;

    public PlanejamentoController() {
        this.planejamento = new Planejamento();
        this.persistencia = new ArquivoPersistenciaService();
    }

    public void novoPlanejamento() {
        this.planejamento = new Planejamento();
    }

    public void cadastrarParticipante(int codigo, String nome, double orcamento) {
        Participante p = new Participante(codigo, nome, orcamento);
        planejamento.adicionarParticipante(p);
    }

    public void editarParticipante(int codigo, String novoNome, double novoOrcamento) {
        planejamento.atualizarParticipante(codigo, novoNome, novoOrcamento);
    }

    public void excluirParticipante(Participante p) {
        planejamento.removerParticipante(p);
    }

    public void cadastrarDespesaFixa(String desc, double valor, List<Participante> envolvidos) {
        Despesa d = new DespesaFixa(desc, valor, envolvidos);
        planejamento.adicionarDespesa(d);
    }

    public void cadastrarDespesaPorPessoa(String desc, double valorUnitario, List<Participante> envolvidos) {
        Despesa d = new DespesaPorPessoa(desc, valorUnitario, envolvidos);
        planejamento.adicionarDespesa(d);
    }

    public void editarDespesa(int index, String desc, double valor, boolean fixa, List<Participante> envolvidos) {
        Despesa nova = fixa ? new DespesaFixa(desc, valor, envolvidos) : new DespesaPorPessoa(desc, valor, envolvidos);
        planejamento.atualizarDespesa(index, nova);
    }

    public void excluirDespesa(int index) {
        planejamento.removerDespesa(index);
    }

    public Planejamento getPlanejamento() {
        return planejamento;
    }

    public Map<Participante, Double> obterResumoCustos() {
        return planejamento.calcularCustosTotais();
    }

    public void salvarDados(String caminho) throws IOException {
        persistencia.salvar(caminho, planejamento);
    }

    public void carregarDados(String caminho) throws IOException, ClassNotFoundException {
        this.planejamento = persistencia.carregar(caminho);
    }
}