package persistence;

import model.Planejamento;
import java.io.IOException;

public interface PersistenciaService {
    void salvar(String caminho, Planejamento planejamento) throws IOException;
    Planejamento carregar(String caminho) throws IOException, ClassNotFoundException;
}