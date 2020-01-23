package app;

import app.exception.*;

import java.util.*;

    /*
     *   Overview:
     *       La seconda della due implementazioni della classe astratta Board.
     *       Utilizza una classe esterna per rappresentare l'oggetto "categoria",
     *       le cui istanze sono contenute in una map. Ogni operazioni richiede
     *       quindi di passare attraverso questa struttura per accedere alle varie
     *       categorie. Per ulteriori dettagli di implementazione,
     *       si rimanda all'apposito file (Category.java).
     *
     *   Abstract Function:
     *      <owner, password, categories>, dove:
     *          owner (string)      è il nome del proprietario della board
     *          password (string)   è la chiave di accesso alla board per l'utente 'owner'
     *          categories (map)    è una map che contiene le istante di tutte le categorie
     *                                  create dall'utente
     *
     *   Representation Invariant:
     *      owner != null && owner != ""
     *      && password != null && password != ""
     *      && for each(category) in categories : name(category) != "" && unique
     *
    */

public class Board2<E extends Data> extends Board<E>{

    private Map<String, Category<E>> categories;

    /**
     * Costruttore di board (2)
     * @param username valido, diverso dalla stringa vuota
     * @param password valida, diverso dalla stringa vuota 
     * @throws EmptyFieldException se username o password sono vuoti
     */
    public Board2(String username, String password)
        throws EmptyFieldException {
        super(username,password);
        this.categories = new HashMap<>();
    }

    /**
     * Crea una nuova categoria
     * @param category  nome della categoria da creare
     * @param password password dell'utente per autenticarsi
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se uno dei due parametri è una stringa vuota
     * @throws DuplicateItemException se esiste già una categoria con lo stesso nome
     * @modifies this.categories
     * @effects post(this.categories) = pre(this.categories) U {category}
     */
    @Override
    public void createCategory(String category, String password)
            throws UnauthorizedException, EmptyFieldException, DuplicateItemException {

        if(category.isBlank() || password.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(category))
            throw new DuplicateItemException();
        else
            this.categories.put(category, new Category<>());
    }

    /**
     * Rimuove una categoria esistente
     * @param category  nome della categoria da rimuovere
     * @param password password dell'utente per autenticarsi
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se uno dei due parametri è una stringa vuota
     * @throws ItemNotFoundException se non esiste una categoria con quel nome
     * @modifies this.categories
     * @effects post(this.categories) = pre(this.categories) \ {category}
     */
    @Override
    public void removeCategory(String category, String password)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if(category.isBlank() || password.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(category))
            this.categories.remove(category);
        else
            throw new ItemNotFoundException();
    }
    /**
     * Associa un nuovo amico ad una categoria esistente
     * @param category  nome della categoria dove aggiungere l'amico
     * @param password password dell'utente per autenticarsi
     * @param friend nome dell'amico da aggiungere
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se uno dei tre parametri è una stringa vuota
     * @throws ItemNotFoundException se non esiste una categoria con quel nome
     * @modifies this.categories[category]
     * @effects see Category.java
     */
    @Override
    public void addFriend(String category, String password, String friend)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if(category.isBlank() || password.isBlank() || friend.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(category))
            this.categories.get(category).addFriend(friend);
        else
            throw new ItemNotFoundException();
    }
    /**
     * Rimuove un amico da una delle categorie
     * @param category  nome della categoria da dove rimuovere l'amico
     * @param password password dell'utente per autenticarsi
     * @param friend nome dell'amico da rimuocere
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se uno dei tre parametri è una stringa vuota
     * @throws ItemNotFoundException se non esiste un amico con quel nome
     * @modifies this.categories[category]
     * @effects see Category.java
     */
    @Override
    public void removeFriend(String category, String password, String friend)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if(category.isBlank() || password.isBlank() || friend.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(category))
            this.categories.get(category).removeFriend(friend);
        else
            throw new ItemNotFoundException();
    }
    /**
     * Inserisce un nuovo post nel feed dell'utente
     * @param password password dell'utente per autenticarsi
     * @param post il post da inserire nel feed
     * @param category  nome della categoria dove inserire il post
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se uno dei due parametri stringa è vuota
     * @throws ItemNotFoundException se non esiste una categoria con quel nome
     * @modifies this.categories[category]
     * @effects see Category.java
     * @return (true) se l'operazione va a buon fine
     */
    @Override
    public boolean put(String password, E post, String category)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if(password.isBlank() || category.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(category)) {
            this.categories.get(category).addPost(post);
            return true;
        } else
            throw new ItemNotFoundException();
    }

    /**
     * Visualizza un post dal feed dell'utente
     * @param password password dell'utente per autenticarsi
     * @param post il post da visualizzare dal feed
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se il campo password è vuoto
     * @throws ItemNotFoundException se il post richiesto non esiste
     * @return (post) ritorna una copia del dato richiesto
     */
    @Override
    public E get(String password, E post)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if(password.isBlank()) throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(post.getCategory()))
            return (E) this.categories.get(post.getCategory()).getPost(post).clone();
        else
            throw new ItemNotFoundException();
    }

    /**
     * Rimuove un post dal feed dell'utente
     * @param password password dell'utente per autenticarsi
     * @param post il post da rimuovere dal feed
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se il campo password è vuoto
     * @throws ItemNotFoundException se il post da rimuovere non esiste
     * @modifies this.categories[post.category]
     * @effects see Category.java
     * @return (post) ritorna una copia del dato rimosso
     */
    @Override
    public E remove(String password, E post)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if(password.isBlank()) throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(post.getCategory())) {
            this.categories.get(post.getCategory()).removePost(post);
            return (E) post.clone();
        } else
            throw new ItemNotFoundException();
    }

    /**
     * Inserisce un like ad un post specifico da parte di un amico,
     * se il like c'è già allora funziona da toggle, ovvero lo rimuove
     * @param friend nome dell'amico che vuole mettere like al post
     * @param post post a cui mettere like
     * @throws EmptyFieldException se il campo friend è vuoto
     * @throws ItemNotFoundException se il post non esiste
     * @modifies this.categories[post.category][likes]
     * @effects see Category.java
     */
    @Override
    public void insertLike(String friend, E post)
            throws EmptyFieldException, ItemNotFoundException, NotAllowedException {

        if(friend.isBlank())
            throw new EmptyFieldException();

        if(this.categories.containsKey(post.getCategory())
                && this.categories.get(post.getCategory()).getFeed().contains(post))
            if(this.categories.get(post.getCategory()).checkFriend(friend))
                this.categories.get(post.getCategory()).like(post, friend);
            else
                throw new NotAllowedException();
        else
            throw new ItemNotFoundException(); // categoria || post non esistenti
    }
   
    /**
     * Crea la lista dei post in bacheca di una determinata categoria
     * @param password password dell'utente per autenticarsi
     * @param category  nome della categoria di cui si vogliono vedere i post
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se uno dei due campi è vuoto
     * @throws ItemNotFoundException se la categoria non esiste
     * @return (list) ritorna una lista dei post appartenenti ad una categoria
     */
    @Override
    public List<E> getDataCategory(String password, String category)
            throws UnauthorizedException, EmptyFieldException, ItemNotFoundException {

        if(password.isBlank() || category.isBlank())
            throw new EmptyFieldException();
        this.authentication(password);
        if(this.categories.containsKey(category)){
            List<E> filteredFeed = new ArrayList<>();
            for(E post: this.categories.get(category).getFeed())
                if(post.getCategory().equals(category))
                    filteredFeed.add(post);
            return filteredFeed;
        }
        else
            throw new ItemNotFoundException();
    }

    /**
     * Iteratore che genera tutti i dati del feed ordinati per numero di like
     * @param password password dell'utente per autenticarsi
     * @throws UnauthorizedException se la password non è corretta
     * @throws EmptyFieldException se il campo password è vuoto
     * @return Iterator ritorna un iteratore immutabile (senza la remove) per generare i posts
     */
    @Override
    public Iterator<E> getIterator(String password)
            throws UnauthorizedException, EmptyFieldException {

        if (password.isBlank()) throw new EmptyFieldException();
        this.authentication(password);
        List<E> sortedFeed = new ArrayList<>();
        for (String category : this.categories.keySet())
            sortedFeed.addAll(this.categories.get(category).getFeed());
        Collections.sort(sortedFeed);
        return Collections.unmodifiableList(sortedFeed).iterator();
        }

    /**
     * Iteratore che genera tutti i dati condivisi con uno specifico amico
     * @param friend nome dell'amico
     * @throws EmptyFieldException se il campo friend è vuoto
     * @return Iterator ritorna un iteratore immutabile (senza la remove) per generare i posts
     */
    @Override
    public Iterator<E> getFriendIterator(String friend)
            throws EmptyFieldException {

        if(friend.isBlank()) throw new EmptyFieldException();
        List<E> filteredFeed = new ArrayList<>();
        for(String category : this.categories.keySet())
            if(this.categories.get(category).checkFriend(friend))
                filteredFeed.addAll(this.categories.get(category).getFeed());
        return Collections.unmodifiableList(filteredFeed).iterator();
    }
}