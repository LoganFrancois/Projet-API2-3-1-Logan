package Projet.Gestion.Vue;

import Projet.Metier.Bureau;

import java.util.List;

public interface VueInterface<T,U> {
    int menu(String[] options);

    void displayMsg(String msg);

    String getMsg(String invite);

    T create();

    void display(T obj);

    T update(T obj);

    U read();

    void affAll(List<T> lobj);
}
