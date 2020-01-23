package app;

import app.exception.EmptyFieldException;
import app.exception.ItemNotFoundException;

import java.util.*;

public class Category<E extends Data> {

    /*
     *   Overview:
     *      Classe di appoggio per la seconda implementazione della classe astratta Board.
     *      Permette la creazione di un oggetto (categoria) che contiene al suo interno
     *      la lista degli amici che possono visualizzare i contenuti della stessa
     *      ed un ulteriore lista che contiene proprio i post condivisi dall'utente.
     *      Questa scelta permette di separare i post in base alla categoria a cui sono associati.
     *
     *   Abstraction function:
     *      <friends, feed>, dove:
     *          friends (set)   è un insieme che contiene al suo interno gli amici che sono autorizzati
     *                              a visualizzare i contenuti associati alla categoria. Essendo un insieme,
     *                              gli amici sono tutti distinti tra loro
     *          feed (list)     è una lista che tiene in memoria tutti i post condivisi dall'utente che sono
     *                              associati alla specifica categoria. Essendo una lista, sono ammessi post
     *                              duplicati.
     *
     *   Representation invariant:
     *      friends != null && feed != null
     *      && for each(friend) in friends: name(friend) != "" && unique
     *      && for each(post) in feed: post.category == name(this)
     *
     */

    private Set<String> friends;
    private List<E> feed;

    /**
     * Costrutture della classe Category
     */
    public Category() {
        this.friends = new TreeSet<>();
        this.feed = new ArrayList<>();
    }

    /**
     * Getter per l'insieme degli amici
     *
     * @return (friends) una copia del treeset che contiene gli amici
     */
    public Set<String> getFriends() {
        return new TreeSet<>(friends);
    }

    /**
     * Getter per la lista di post condivisi
     *
     * @return (feed) una copia della lista che contiene i post condivisi dall'utente
     */
    public List<E> getFeed() {
        return new ArrayList<>(feed);
    }

    /**
     * Aggiunge un amico alla categoria corrente
     *
     * @param friend nome dell'amico da aggiungere
     * @modifies this.friends
     * @effects post(this.friends) = pre(this.friends) U {friend}
     */
    public void addFriend(String friend) {
        this.friends.add(friend);
    }

    /**
     * Rimuove un amico dalla categoria corrente
     *
     * @param friend nome dell'amico da rimuovere
     * @modifies this.friends
     * @effects post(this.friends) = pre(this.friends) \ {friend}
     */
    public void removeFriend(String friend) {
        this.friends.remove(friend);
    }

    /**
     * Aggiunge un post al feed dell'utente
     *
     * @param post post da aggiungere
     * @modifies this.feed
     * @effects post(this.feed) = pre(this.feed) U {post}
     */
    public void addPost(E post) {
        this.feed.add(post);
    }

    /**
     * Richiede una copia di uno specifico post dal feed dell'utente
     *
     * @param post il post richiesto
     * @return (post) una copia del post richiesto, se questo esiste
     * @throws ItemNotFoundException se il post richiesto non è presente nel feed
     */
    public E getPost(E post)
            throws ItemNotFoundException {

        if (this.feed.contains(post))
            return (E) this.feed.get(this.feed.indexOf(post)).clone();
        else
            throw new ItemNotFoundException();
    }

    /**
     * Rimuove uno specifico post dal feed dell'utente
     *
     * @param post il post da rimuovere
     * @return (post) una copia del post rimosso, altrimenti (null) se il post non esiste
     * @throws ItemNotFoundException se il post da rimuovere non esiste nel feed
     * @modifies this.feed
     * @effects post(this.feed) = pre(this.feed) \ {post}
     */
    public E removePost(E post)
            throws ItemNotFoundException {

        if (!this.feed.contains(post)) throw new ItemNotFoundException();
        return this.feed.remove(post) ? (E) post.clone() : null;
    }

    /**
     * Inserisce (o rimuove) un like al post indicato, funziona come un toggle
     *
     * @param post   il post a cui mettere like
     * @param friend nome dell'amico che vuole mettere il like
     * @throws ItemNotFoundException se il post richiesto non esiste nel feed
     * @throws EmptyFieldException   se il nome dell'amico è una stringa vuota
     * @modifies this.feed[post][likes]
     * @effects post(this.feed[post][likes]) = pre(this.feed[post][likes]) U {friend}
     * || post(this.feed[post][likes]) = pre(this.feed[post][likes]) \ {friend}
     */
    public void like(E post, String friend)
            throws ItemNotFoundException, EmptyFieldException {

        if (!this.feed.contains(post))
            throw new ItemNotFoundException();
        this.feed.get(this.feed.indexOf(post)).addLike(friend);
    }

    /**
     * Controlla se un certo amico è presente all'interno della categoria
     *
     * @param friend il nome dell'amico
     * @return (bool) ritorna true o false in base a se l'amico è contenuto nella lista oppure no
     * @throws EmptyFieldException se friend è una stringa vuota
     */
    public boolean checkFriend(String friend)
            throws EmptyFieldException {

        if (friend.isBlank())
            throw new EmptyFieldException();
        return this.friends.contains(friend);
    }
}
