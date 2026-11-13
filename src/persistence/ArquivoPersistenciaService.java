package persistence;

import model.Planejamento;
import java.io.*;

public class ArquivoPersistenciaService implements PersistenciaService {
    @Override
    public void salvar(String caminho, Planejamento planejamento) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(planejamento);
        }
    }

    @Override
    public Planejamento carregar(String caminho) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (Planejamento) ois.readObject();
        }
    }
}