package app;

import app.exception.*;

import java.util.Iterator;
import java.util.List;

public interface DataBoard<E extends Data> {

    // Tutte le funzioni vengono eseguite correttamente
    // se e solo se vengono rispettati i controlli di identità

    // Crea una categoria di dati
    public void createCategory(String category, String passw)
            throws UnauthorizedException, EmptyFieldException, DuplicateItemException;

    // Rimuove una categoria di dati
    public void removeCategory(String category, String passw)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException;

    // Aggiunge un amico ad una categoria di dati
    public void addFriend(String category, String passw, String friend)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException;

    // Rimuove un amico da una categoria di dati
    public void removeFriend(String category, String passw, String friend)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException;

    // Inserisce un dato in bacheca
    public boolean put(String passw, E dato, String categoria)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException;

    // Restituisce una copia del dato in bacheca
    public E get(String passw, E dato)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException;

    // Rimuove il dato dalla bacheca
    public E remove(String passw, E dato)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException;

    // Aggiunge un like ad un dato
    void insertLike(String friend, E data)
            throws EmptyFieldException, ItemNotFoundException, NotAllowedException;

    // Crea la lista dei dati in bacheca di una determinata categoria
    public List<E> getDataCategory(String passw, String category)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException;

    // Restituisce un iteratore (senza remove) che genera tutti i dati
    // in bacheca ordinati rispetto al numero di like
    public Iterator<E> getIterator(String passw)
            throws UnauthorizedException, EmptyFieldException;

    // Restituisce un iteratore (senza remove) che genera tutti i dati
    // in bacheca condivisi con l'amico <friend>
    public Iterator<E> getFriendIterator(String friend)
            throws EmptyFieldException;

    // Altre operazioni qui...
}
