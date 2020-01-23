package app;

import app.exception.*;

import java.util.*;

public class Board1<E extends Data>
        extends Board<E> {

    /*
     *   Overview:
     *      Una delle due implementazione della classe astratta Board.
     *      Questa prima implementazione prevede l'utilizzo di tre strutture dati differenti
     *      senza far uso di classi esterne. I posts creati dall'utente vengono salvati
     *      all'interno di una lista <feed>, in modo da poter inserire
     *      anche più di una volta uno stesso post, ammettendo quindi duplicati.
     *      La lista degli amici invece è contenuta in un Set, in quanto questi
     *      non possono essere duplicati. Infine, le categorie sono contenute in una Map,
     *      per permetterne l'associazione con gli amici che possono visualizzare i contenuti
     *      appartenenti ad una specifica categoria.
     *
     *   Abstraction Function:
     *      <owner, password, feed, categories, friends>, dove:
     *          owner (string)      è il nome del proprietario della board
     *          password (string)   è la chiave di accesso alla board per l'utente 'owner'
     *          feed (list)         è la lista dei posts condivisi dal proprietario della board
     *          categories (map)    è una map che associa alle categorie create dell'utente, gli amici
     *                                  che sono autorizzati a visualizzare i contenuti appartenenti alla stessa
     *          friend (set)        è un insieme di amici (quindi tutti distinti) che pssono visualizzare
     *                                  i contenuti associati alla categoria a cui appartengono.
     *
     *   Representation Invariant:
     *      owner != null && owner != ""
     *      && password != null && password != ""
     *      && feed != null && categories != null && friends != null
     *      && for each(category) in categories : name(category) != "" && unique
     *      && for each(friend) in (for each(category) in categories : name(category.friend) != "" && unique)
     *
     */

    private List<E> feed;
    private Set<String> friends;
    private Map<String, Set<String>> categories;

    /**
     * Costruttore di board (1)
     *
     * @param username valido, diverso dalla stringa vuota
     * @param password valida, diverso dalla stringa vuota
     * @throws EmptyFieldException se almeno uno dei parametri è vuoto
     */
    public Board1(String username, String password)
            throws EmptyFieldException {
        super(username, password);
        this.feed = new ArrayList<>();
        this.friends = new HashSet<>();
        this.categories = new HashMap<>();
    }

    /**
     * Crea una nuova categoria
     *
     * @param category nome della categoria da creare
     * @param password password dell'utente per autenticarsi
     * @throws EmptyFieldException    se almeno uno dei due parametri è una stringa vuota
     * @throws UnauthorizedException  se la password non è corretta
     * @throws DuplicateItemException se esiste già una categoria con lo stesso nome
     * @modifies this.categories
     * @effects post(this.categories) = pre(this.categories) U {category}
     */
    @Override
    public void createCategory(String category, String password)
            throws EmptyFieldException, UnauthorizedException, DuplicateItemException {

        if (password.isBlank() || category.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.categories.containsKey(category))
            throw new DuplicateItemException();
        else
            this.categories.put(category, new TreeSet<>());
    }

    /**
     * Rimuove una categoria esistente
     *
     * @param category nome della categoria da rimuovere
     * @param password password dell'utente per autenticarsi
     * @throws EmptyFieldException   se uno dei due parametri è una stringa vuota
     * @throws UnauthorizedException se la password non è corretta
     * @throws ItemNotFoundException se la categoria da rimuovere non esiste
     * @modifies this.categories
     * @effects post(this.categories) = pre(this.categories) \ {category}
     */
    @Override
    public void removeCategory(String category, String password)
            throws EmptyFieldException, UnauthorizedException, ItemNotFoundException {

        if (password.isEmpty() || category.isEmpty())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.categories.containsKey(category))
            this.categories.remove(category);
        else
            throw new ItemNotFoundException();
    }

    /**
     * Associa un nuovo amico ad una categoria esistente
     *
     * @param category nome della categoria dove aggiungere l'amico
     * @param password password dell'utente per autenticarsi
     * @param friend   nome dell'amico da aggiungere
     * @throws EmptyFieldException   se almeno uno dei tre parametri è una stringa vuota
     * @throws UnauthorizedException se la password non è corretta
     * @throws ItemNotFoundException se non esiste una categoria con quel nome
     * @modifies this.categories, this.friends
     * @effects post(this.categories[category]) = pre(this.categories[category]) U {friend}
     * @effects post(this.friends) = pre(this.friends) U {friend}
     */
    @Override
    public void addFriend(String category, String password, String friend)
            throws EmptyFieldException, UnauthorizedException, ItemNotFoundException {

        if (password.isEmpty() || friend.isEmpty() || category.isEmpty())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.categories.containsKey(category)) {
            this.categories.get(category).add(friend);
            this.friends.add(friend);
        } else
            throw new ItemNotFoundException();
    }

    /**
     * Rimuove l'associazione tra un certo amico ed una specifica categoria
     *
     * @param category nome della categoria da dove rimuovere l'amico
     * @param password password dell'utente per autenticarsi
     * @param friend   nome dell'amico da rimuovere
     * @throws EmptyFieldException   se almeno uno dei tre parametri è una stringa vuota
     * @throws UnauthorizedException se la password non è corretta
     * @throws ItemNotFoundException se non esiste un amico con quel nome
     * @modifies this.categories, this.friends
     * @effects post(this.categories[category]) = pre(this.categories[category]) \ {friend}
     * @effects post(this.friends) = pre(this.friends) \ {friend}
     */
    @Override
    public void removeFriend(String category, String password, String friend)
            throws EmptyFieldException, UnauthorizedException, ItemNotFoundException {

        if (password.isBlank() || friend.isBlank() || category.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.friends.contains(friend)) {
            this.friends.remove(friend);
            this.categories.get(category).remove(friend);
        } else
            throw new ItemNotFoundException();
    }

    /**
     * Inserisce un nuovo post nel feed dell'utente
     *
     * @param password password dell'utente per autenticarsi
     * @param post     il post da inserire nel feed
     * @param category nome della categoria dove inserire il post
     * @return (true) se l'operazione va a buon fine
     * @throws EmptyFieldException   se uno dei due parametri stringa è vuota
     * @throws UnauthorizedException se la password non è corretta
     * @throws ItemNotFoundException se non esiste una categoria con quel nome
     * @modifies this.feed
     * @effects post(this.feed) = pre(this.feed) U {post}
     */
    @Override
    public boolean put(String password, E post, String category)
            throws EmptyFieldException, UnauthorizedException, ItemNotFoundException {

        if (password.isBlank() || category.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.categories.containsKey(category))
            // Non controllo i duplicati, in quanto posso voler
            // pubblicare più di una volta uno stesso contenuto
            return this.feed.add(post);
        else
            throw new ItemNotFoundException();
    }

    /**
     * Visualizza un post dal feed dell'utente
     *
     * @param password password dell'utente per autenticarsi
     * @param post     il post da visualizzare dal feed
     * @return (post) ritorna una copia del dato richiesto
     * @throws EmptyFieldException   se il campo password è vuoto
     * @throws UnauthorizedException se la password non è corretta
     * @throws ItemNotFoundException se il post richiesto non esiste
     */
    @Override
    public E get(String password, E post)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if (password.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.feed.contains(post))
            return (E) this.feed.get(this.feed.indexOf(post)).clone();
        else
            throw new ItemNotFoundException();
    }

    /**
     * Rimuove un post dal feed dell'utente
     *
     * @param password password dell'utente per autenticarsi
     * @param post     il post da rimuovere dal feed
     * @return (post) ritorna una copia del dato rimosso
     * @throws EmptyFieldException   se il campo password è vuoto
     * @throws UnauthorizedException se la password non è corretta
     * @throws ItemNotFoundException se il post da rimuovere non esiste
     * @modifies this.feed
     * @effects post(this.feed) = pre(this.feed) \ {post}
     */
    @Override
    public E remove(String password, E post)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if (password.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.feed.contains(post))
            this.feed.remove(feed.indexOf(post));
        else
            throw new ItemNotFoundException();
        return (E) post.clone();
    }

    /**
     * Inserisce un like ad un post specifico da parte di un amico,
     * se il like c'è già allora funziona da toggle, ovvero lo rimuove
     *
     * @param friend nome dell'amico che vuole mettere like al post
     * @param post   post a cui mettere like
     * @throws EmptyFieldException   se il campo friend è vuoto
     * @throws ItemNotFoundException se il post non esiste
     * @modifies this.feed[post]
     * @effects post(this.feed[post][likes]) = pre(this.feed[post][likes]) U {friend}
     */
    @Override
    public void insertLike(String friend, E post)
            throws EmptyFieldException, ItemNotFoundException, NotAllowedException {

        if (friend.isBlank())
            throw new EmptyFieldException();
        if (this.feed.contains(post) && this.categories.containsKey(post.getCategory()))
            if (this.categories.get(post.getCategory()).contains(friend))
                this.feed.get(this.feed.indexOf(post)).addLike(friend);
            else
                throw new NotAllowedException();
        else
            throw new ItemNotFoundException();
    }

    /**
     * Crea la lista dei post in bacheca di una determinata categoria
     *
     * @param password password dell'utente per autenticarsi
     * @param category nome della categoria di cui si vogliono vedere i post
     * @return (list) ritorna una lista dei post appartenenti ad una categoria
     * @throws EmptyFieldException   se uno dei due campi è vuoto
     * @throws UnauthorizedException se la password non è corretta
     * @throws ItemNotFoundException se la categoria non esiste
     */
    @Override
    public List<E> getDataCategory(String password, String category)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if (password.isBlank() || category.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if (this.categories.containsKey(category)) {
            List<E> filteredFeed = new ArrayList<>();
            for (E post : this.feed)
                if (post.getCategory().equals(category))
                    filteredFeed.add(post);
            return filteredFeed;
        } else
            throw new ItemNotFoundException();
    }

    /**
     * Iteratore che genera tutti i dati del feed ordinati per numero di like
     *
     * @param password password dell'utente per autenticarsi
     * @return (iterator) ritorna un iteratore immutabile (senza la remove) per generare i posts
     * @throws EmptyFieldException   se il campo password è vuoto
     * @throws UnauthorizedException se la password non è corretta
     */
    @Override
    public Iterator<E> getIterator(String password)
            throws UnauthorizedException, EmptyFieldException {

        if (password.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        List<E> sortedFeed = new ArrayList<>(feed);
        Collections.sort(sortedFeed);
        return Collections.unmodifiableList(sortedFeed).iterator();
    }

    /**
     * Iteratore che genera tutti i dati condivisi con uno specifico amico
     *
     * @param friend nome dell'amico
     * @return (iterator) ritorna un iteratore immutabile (senza la remove) per generare i posts
     * @throws EmptyFieldException se il campo friend è vuoto
     */
    @Override
    public Iterator<E> getFriendIterator(String friend)
            throws EmptyFieldException {

        if (friend.isBlank())
            throw new EmptyFieldException();
        List<E> filteredFeed = new ArrayList<>();
        for (E post : this.feed)
            if (this.categories.get(post.getCategory()).contains(friend))
                filteredFeed.add(post);
        return Collections.unmodifiableList(filteredFeed).iterator();
    }
}
