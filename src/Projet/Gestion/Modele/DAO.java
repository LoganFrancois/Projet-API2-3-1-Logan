package Projet.Gestion.Modele;

import java.util.List;

public interface DAO <T> {

    T create(T newObj);

    boolean delete(T objRech);

    T read(T objRech);

    T update(T objRech);

    List<T> readAll();
}
