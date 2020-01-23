package app;

import app.exception.EmptyFieldException;
import app.exception.ItemNotFoundException;

import java.util.List;
import java.util.ArrayList;

public class Data implements Comparable<Data> {

    /**
     *   Overview:
     *      Rappresentazione di un generico post che può essere creato e condiviso
     *      dal proprietario di una board. La classe mette a disposizione dell'utente
     *      alcuni campi che permettono la personalizzazione del post, nonché una funzione
     *      (display) per visualizzare il contenuto in un formato pseudo-JSON ed una funziona
     *      che permette di aggiungere o rimouvere un like al post stesso.
     *
     *   Abstraction function:
     *      <author, content, category, timestamp, likes>, dove:
     *          author (string)     è il nome del proprietario della board, che pubblica il post
     *          content (string)    è il contenuto del post
     *          category (string)   è la categoria a cui il post è associato
     *          timestamp (long)    è una rappresentazione in formato UNIX-like del momento in cui è stato creato il post
     *          likes (list)        è una lista delle persone che hanno messo like al post
     *
     *   Representation invariant:
     *      author != "" && content != "" && category != ""
     *      && int(timestamp) && timestamp > 0 && timestmap < 2^31
     *      && for each(friend) in likes : name(friend) != "" && unique
     *
     */

    private final String author;
    private final String content;
    private final String category;
    private final long timestamp;
    private List<String> likes;

    /**
     * Costruttore della classe Data
     * @param author è il nome del proprietario della board, che pubblica il post
     * @param content è il contenuto del post
     * @param category è il nome della categoria che si vuole associare al post
     * @throws EmptyFieldException se almeno uno dei tre parametri è una stringa vuota
     */
    public Data(String author, String content, String category)
            throws EmptyFieldException {

        if(author.isBlank() || content.isBlank() || category.isBlank())
            throw new EmptyFieldException();

        this.author = author;
        this.content = content;
        this.category = category;
        this.timestamp = System.currentTimeMillis() / 1000L;
        this.likes = new ArrayList<>();
    }

    /**
     * Costruttore di copia per il tipo di dato Data
     * @param data è il dato che si vuole copiare
     */
    public Data(Data data) {
        if (data == null)
            throw new NullPointerException();
        this.author = data.author;
        this.content = data.content;
        this.category = data.category;
        this.timestamp = data.timestamp;
        this.likes = data.likes;
    }

    /**
     * Getter per il campo author del post
     * @return (author) il nome del proprietario del post
     */
    public String getAuthor(){ return author; }

    /**
     * Getter per il campo content del post
     * @return (content) il contenuto del post
     */
    public String getContent(){ return content; }

    /**
     * Getter per il campo category del post
     * @return (category) la categoria a cui il post è associato
     */
    public String getCategory(){ return category; }

    /**
     * Getter per il tempo di creazione del post in formato UNIX
     * @return (timestamp)
     */
    public long getTimestamp(){ return timestamp; }

    /**
     * Getter per il numero di like del post
     * @return (likesCounter) usa la funzione .size() della lista di likes
     */
    public int getLikesCounter(){ return likes.size(); }

    /**
     * Getter per la lista dei like del post
     * @return (likes) ritorna una copia della lista di likes
     */
    public List<String> getLikes(){ return new ArrayList<>(likes); }

    /**
     * Visualizza il post
     * @effects stampa a video il post usando il metodo toString
     */
    public void display(){
        System.out.println(toString());
    }

    /**
     * Aggiunge un like al post da parte dell'amico indicato, se non è già presente
     * @param friend nome dell'amico che vuole mettere like
     * @throws EmptyFieldException se friend è una stringa vuota
     * @modifies this.likes
     * @effects post(this.likes) = pre(this.likes) U {friend}
     *          || removeLike(friend)
     */
    public void addLike(String friend)
            throws EmptyFieldException {

        if(friend.isBlank())
            throw new EmptyFieldException();
        if(this.likes.contains(friend))
            this.removeLike(friend);
        else
            this.likes.add(friend);
    }

    /**
     * Rimuove il like di friend se questo è presente
     * E' un metodo privato chiamato da addLike
     * @param friend il nome dell'amico
     * @modifies this.likes
     * @effects post(this.likes) = pre(this.likes) \ {friend}
     */
    private void removeLike(String friend){
        this.likes.remove(friend);
    }

    /**
     * Ritorna una stringa per visualizzare il post in formato JSON-like
     * @return (post)
     */
    public String toString(){
        return "{'author': "       + getAuthor() +
                ",'content:' "   + getContent() +
                ",'timestamp': " + getTimestamp() +
                ",'category': "  + getCategory() +
                ",'likes': "     + getLikesCounter() +
                ",'likesList: [" + String.join(", ", getLikes()) + "]" +
                "}";
    }

    /**
     * Confronta il numero di like tra due post per effettuare un ordinamento.
     * A parità di like, si usa l'ordine lessicografico del campo content
     * @param post il post con cui confrontare
     * @return (result) un intero che determina l'ordinamento (discendente, in questo caso)
     */
    @Override
    public int compareTo(Data post) {
        int result = post.getLikesCounter() - this.getLikesCounter();
        if(result == 0)
            return (post.getContent().compareTo(this.getContent()) < 0) ? 1 : -1;
        return result;
    }

    /**
     * Permette di restituire una copia del dato quando richiesto
     * @return (data) copia del post richiesto
     */
    public Data clone(){
        return new Data(this);
    }
}
