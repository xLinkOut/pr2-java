package app;

import app.exception.EmptyFieldException;
import app.exception.SamePasswordException;
import app.exception.UnauthorizedException;

public abstract class Board<E extends Data>
        implements DataBoard<E> {

    /*
     *   Overview:
     *       Classe astratta di Board per condividere la struttura e le funzionalità
     *       di base tra le due diverse implementazioni della classe.
     *       Permette la creazione di una board e di settare una coppia <username, password> per
     *       restringerne l'utilizzo al solo proprietario. Inoltre fornisce un metodo
     *       di autenticazione per sbloccare le funzioni protette
     *       ed uno per cambiare la password, se lo si desidera.
     *
     *   Typical Element:
     *       <owner, password, data>, dove:
     *           owner (string)      è il nome del proprietario della board
     *           password (string)   è la chiave di accesso alla board per l'utente 'owner'
     *           data (misc)         è l'insieme delle strutture dati utilizzati per far
     *                                   funzionare la board, che dipende dall'implementazione scelta
     *                                   (Fare riferimento alla doc di Board1 e Board2 per i dettagli).
     *
     *   Representation Invariant:
     *       owner != null && owner != ""
     *       && password != null && password != ""
     *
     */

    private final String owner;
    private String password;

    /**
     * Costruttore di Board
     *
     * @param owner    username valido, diverso dalla stringa vuota
     * @param password password valida, diverso dalla stringa vuota
     * @throws EmptyFieldException se username o password sono vuoti
     */
    public Board(String owner, String password)
            throws EmptyFieldException {

        if (owner.isBlank() || password.isBlank())
            throw new EmptyFieldException();

        this.owner = owner;
        this.password = password; // Hash?
    }

    /**
     * Getter dell'username
     *
     * @return (owner) l'username del proprietario della board
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Verifica di autenticazione
     *
     * @param password password corrente dell'utente
     * @return (true) se l'autenticazione è riuscita, ovvero la password è corretta
     * @throws EmptyFieldException   se password è una stringa vuota
     * @throws UnauthorizedException se la password è diversa da quella impostata
     */
    public boolean authentication(String password)
            throws EmptyFieldException, UnauthorizedException {

        if (password.isBlank())
            throw new EmptyFieldException();
        if (!(password.equals(this.password)))
            throw new UnauthorizedException();
        return true;
    }

    /**
     * Resetta la password dell'utente
     *
     * @param currentPassword è la password corrente dell'utente
     * @param newPassword     è la nuova password che si vuole impostare
     * @throws EmptyFieldException   se almeno uno dei due parametri è vuoto
     * @throws SamePasswordException se newPassword è uguale a currentPassowrd
     * @throws UnauthorizedException se la password corrente non è giusta
     * @modifies this.password
     * @effects post(this.password) = newPassword
     */
    public void resetPassword(String currentPassword, String newPassword)
            throws EmptyFieldException, SamePasswordException, UnauthorizedException {

        if (currentPassword.isBlank() || newPassword.isBlank())
            throw new EmptyFieldException();
        if (currentPassword.equals(newPassword))
            throw new SamePasswordException();

        this.authentication(currentPassword);
        // La password corrente è giusta
        this.password = newPassword;
        // La password corrente è sbagliata

    }
}
