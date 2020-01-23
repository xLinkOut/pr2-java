package app;

import app.exception.*;

import java.util.Iterator;
import java.util.List;

public class Main {

    static final String username = "Luca";
    static final String password = "un1c0rn1!";
    static final String newPassword = "gh0stbust3rs?";
    static final String wrongPassword = "e@rthqu4k3";

    public static void main(String[] args)
            throws EmptyFieldException {

        System.out.println("#== PR2-Java ==#");

        System.out.println("\nTest prima implementazione di Board...");
        Board1<Data> board1 = new Board1<>(username, password);
        try {
            doTest(board1);
        } catch (TestFailException e) {
            System.out.println("Unrecoverable error! Check your tests.");
            System.exit(-2);
        }

        System.out.println("\nTest seconda implementazione di Board...");
        Board2<Data> board2 = new Board2<>(username, password);
        try {
            doTest(board2);
        } catch (TestFailException e) {
            System.out.println("Unrecoverable error! Check your tests.");
            System.exit(-2);
        }

        System.out.println("\n#== Test completato ==#");
    }

    private static void doTest(Board<Data> board) throws TestFailException {

        // Categories
        System.out.println("\n+ Categories:");

        // createCategory
        try {
            board.createCategory("Funny", password);
            board.createCategory("Pets", password);
            board.createCategory("Quotes", password);
            board.createCategory("Technology", password);
            board.createCategory("Stuffs", password);
            System.out.println("\t+ createCategory: ✅");
        } catch (UnauthorizedException | EmptyFieldException | DuplicateItemException e) {
            System.out.println("\t+ createCategory: ❌");
        }

        // createCategory->Unauthorized
        try {
            board.createCategory("Videogames", wrongPassword);
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | DuplicateItemException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // createCategory->EmptyField
        try {
            board.createCategory("", password);
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | DuplicateItemException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // createCategory->DuplicateItem
        try {
            board.createCategory("Funny", password);
            System.out.println("\t\t+ DuplicateItem: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ DuplicateItem: ❌");
        } catch (DuplicateItemException e) {
            System.out.println("\t\t+ DuplicateItem: ✅");
        }

        // removeCategory
        try {
            board.removeCategory("Stuffs", password);
            System.out.println("\t+ removeCategory: ✅");
        } catch (EmptyFieldException | ItemNotFoundException | UnauthorizedException e) {
            System.out.println("\t+ removeCategory: ❌");
        }

        // removeCategory->Unauthorized
        try {
            board.removeCategory("Quotes", wrongPassword);
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // removeCategory->EmptyField
        try {
            board.removeCategory("", password);
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // removeCategory->ItemNotFoundException
        try {
            board.removeCategory("Flowers", password);
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // Friends
        System.out.println("\n+ Friends:");

        // addFriend
        try {
            board.addFriend("Funny", password, "Sofia");
            board.addFriend("Funny", password, "Riccardo");
            board.addFriend("Funny", password, "Danila");
            board.addFriend("Funny", password, "Alessandro");
            board.addFriend("Pets", password, "Danila");
            board.addFriend("Technology", password, "Alessandro");
            board.addFriend("Technology", password, "Riccardo");
            System.out.println("\t+ addFriend: ✅");
        } catch (EmptyFieldException | UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t+ addFriend: ❌");
        }

        // addFriend->Unauthorized
        try {
            board.addFriend("Quotes", wrongPassword, "Erika");
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // addFriend->EmptyField
        try {
            board.addFriend("", password, "");
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // addFriend->ItemNotFoundException
        try {
            board.addFriend("Flowers", password, "Leonardo");
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // removeFriend
        try {
            board.removeFriend("Funny", password, "Riccardo");
            board.removeFriend("Pets", password, "Danila");
            board.removeFriend("Technology", password, "Alessandro");
            System.out.println("\t+ removeFriend: ✅");
        } catch (EmptyFieldException | UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t+ removeFriend: ❌");
        }

        // removeFriend->Unauthorized
        try {
            board.removeFriend("Funny", wrongPassword, "Sofia");
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // removeFriend->EmptyField
        try {
            board.removeFriend("", password, "");
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // removeFriend->ItemNotFoundException
        try {
            board.removeFriend("Flowers", password, "Leonardo");
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // Posts
        System.out.println("\n+ Posts:");

        Data[] posts = new Data[10];
        try {
            posts[0] = new Data(username, "<A>", "Funny");
            posts[1] = new Data(username, "<B>", "Funny");
            posts[2] = new Data(username, "<C>", "Pets");
            posts[3] = new Data(username, "<D>", "Pets");
            posts[4] = new Data(username, "<E>", "Quotes");
            posts[5] = new Data(username, "<F>", "Quotes");
            posts[6] = new Data(username, "<G>", "Technology");
            posts[7] = new Data(username, "<H>", "Technology");
            posts[8] = new Data(username, "<I>", "Technology");
            posts[9] = new Data(username, "<J>", "Technology");
            //System.out.println("\t Those are " + posts.length + " posts for test purpose:");
            //for (Data post : posts)
            //    System.out.println("\t\t " + post.toString().replace("\n", ""));
        } catch (EmptyFieldException e) {
            throw new TestFailException();
        }

        // put
        try {
            for (Data post : posts)
                board.put(password, post, post.getCategory());
            System.out.println("\t+ put: ✅");
        } catch (EmptyFieldException | ItemNotFoundException | UnauthorizedException e) {
            System.out.println("\t+ put: ❌");
        }

        // put->Unauthorized
        try {
            board.put(wrongPassword, posts[4], posts[4].getCategory());
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // put->EmptyField
        try {
            board.put(password, posts[5], "");
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // put->ItemNotFound
        try {
            board.put(password, posts[6], "Stuffs");
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // remove
        try {
            board.remove(password, posts[8]);
            board.remove(password, posts[9]);
            System.out.println("\t+ remove: ✅");
        } catch (EmptyFieldException | ItemNotFoundException | UnauthorizedException e) {
            System.out.println("\t+ remove: ❌");
        }

        // remove->Unauthorized
        try {
            board.remove(wrongPassword, posts[2]);
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // remove->EmptyField
        try {
            board.remove("", posts[3]);
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // remove->ItemNotFound
        try {
            board.remove(password, posts[9]);
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // get
        try {
            board.get(password, posts[0]);
            board.get(password, posts[1]);
            board.get(password, posts[2]);
            System.out.println("\t+ get: ✅");
        } catch (EmptyFieldException | ItemNotFoundException | UnauthorizedException e) {
            System.out.println("\t+ get: ❌");
        }

        // get->Unauthorized
        try {
            board.get(wrongPassword, posts[4]);
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // get->EmptyField
        try {
            board.get("", posts[5]);
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // get->ItemNotFound
        try {
            board.get(password, posts[9]);
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // insertLike
        try {
            board.insertLike("Sofia", posts[0]);
            board.insertLike("Alessandro", posts[0]);
            board.insertLike("Riccardo", posts[6]);
            board.insertLike("Danila", posts[1]);
            board.insertLike("Riccardo", posts[6]);
            board.insertLike("Riccardo", posts[6]);
            System.out.println("\t+ insertLike: ✅");
        } catch (EmptyFieldException | ItemNotFoundException | NotAllowedException e) {
            System.out.println("\t+ insertLike: ❌");
        }

        // insertLike->EmptyField
        try {
            board.insertLike("", posts[3]);
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (ItemNotFoundException | NotAllowedException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // insertLike->ItemNotFound
        try {
            board.insertLike("Martina", posts[9]);
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | NotAllowedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // insertLike->NotAllowed
        try {
            board.insertLike("Sofia", posts[2]);
            System.out.println("\t\t+ NotAllowed: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ NotAllowed: ❌");
        } catch (NotAllowedException e) {
            System.out.println("\t\t+ NotAllowed: ✅");
        }

        // getDataCategory
        try {
            List<Data> postTechnology = board.getDataCategory(password, "Technology");
            if (postTechnology.size() != 2) throw new TestFailException();
            System.out.println("\t+ getDataCategory: ✅");
        } catch (EmptyFieldException | ItemNotFoundException | UnauthorizedException | TestFailException e) {
            System.out.println("\t+ getDataCategory: ❌");
        }

        // getDataCategory->Unauthorized
        try {
            board.getDataCategory(wrongPassword, "Pets");
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException | ItemNotFoundException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // getDataCategory->EmptyField
        try {
            board.getDataCategory("", "");
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException | ItemNotFoundException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // getDataCategory->ItemNotFound
        try {
            board.getDataCategory(password, "Stuffs");
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ ItemNotFound: ❌");
        } catch (ItemNotFoundException e) {
            System.out.println("\t\t+ ItemNotFound: ✅");
        }

        // getIterator
        try {
            Iterator<Data> postsIterator = board.getIterator(password);
            if (postsIterator.next().getLikesCounter() != 2) throw new TestFailException();
            if (postsIterator.next().getLikesCounter() != 1) throw new TestFailException();
            if (postsIterator.next().getLikesCounter() != 1) throw new TestFailException();
            if (postsIterator.next().getLikesCounter() != 0) throw new TestFailException();
            System.out.println("\t+ getIterator: ✅");
        } catch (EmptyFieldException | UnauthorizedException | TestFailException e) {
            System.out.println("\t+ getIterator: ❌");
        }

        // getIterator->remove
        try {
            Iterator<Data> postsIterator = board.getIterator(password);
            postsIterator.next();
            postsIterator.remove();
            System.out.println("\t\t+ Remove: ❌");
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t\t+ Remove: ❌");
        } catch (UnsupportedOperationException e) {
            System.out.println("\t\t+ Remove: ✅");
        }

        // getIterator->Unauthorized
        try {
            board.getIterator(wrongPassword);
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

        // getIterator->EmptyField
        try {
            board.getIterator("");
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // getFriendIterator
        try {
            Iterator<Data> friendIterator = board.getFriendIterator("Danila");
            if (!(friendIterator.next().getContent().equals("<A>"))) throw new TestFailException();
            if (!(friendIterator.next().getContent().equals("<B>"))) throw new TestFailException();
            System.out.println("\t+ getFriendIterator: ✅");
        } catch (EmptyFieldException | TestFailException e) {
            System.out.println("\t+ getFriendIterator: ❌");
        }

        // getFriendIterator->EmptyField
        try {
            board.getFriendIterator("");
            System.out.println("\t\t+ EmptyField: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ EmptyField: ✅");
        }

        // Misc
        System.out.println("\n+ Misc:");

        // display
        try {
            Iterator<Data> iterator = board.getIterator(password);
            System.out.print("\t+ display: ");
            iterator.next().display();
        } catch (EmptyFieldException | UnauthorizedException e) {
            System.out.print("❌");
        }

        // resetPassword
        try {
            board.resetPassword(password, newPassword);
            board.authentication(newPassword);
            System.out.println("\t+ resetPassword: ✅");
        } catch (SamePasswordException | EmptyFieldException | UnauthorizedException e) {
            System.out.println("\t+ resetPassword: ❌");
        }

        // resetPassword->Unauthorized
        try {
            board.authentication(password); // Old password
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (EmptyFieldException e) {
            System.out.println("\t\t+ Unauthorized: ❌");
        } catch (UnauthorizedException e) {
            System.out.println("\t\t+ Unauthorized: ✅");
        }

    }
}
