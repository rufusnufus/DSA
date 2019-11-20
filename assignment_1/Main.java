import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Rufina Talalaeva
 *
 * <h1>Class-connection between all classes</h1>
 * This program implements the sorting algorithm to produce
 * a ranked (i.e. sorted) list of teams in a series of football
 * tournaments by using implemented ADT list. The program reads
 * the tournament name, team names, and games played,
 * and it should output the tournament standings.
 */
public class Main {
    public static void main(String[] args) {
        //our input stream from console
        Scanner console = new Scanner(System.in);

        //reads the number of tournaments
        int numberOfTournaments = console.nextInt();

        //creates the list of tournaments
        DynamicArray<Tournament> tournaments = new DynamicArray<>();

        //moves the cursor to the next line of input
        console.nextLine();

        //reads the tournaments including the all teams and played matches
        readingTournaments(numberOfTournaments, console, tournaments);

        //produces the ranked list of teams in each tournament
        for (int i = 0; i < tournaments.size(); ++i){
            sort(( tournaments.get(i).teams));
        }

        //prints all tournaments with their ranked list of teams
        printingTournaments(tournaments);

        console.close();

        /**
         * Tests for implemented Dynamic Array & for Doubly Linked List
         */
//        //DoublyLinkedList<String> a = new DoublyLinkedList<>();
//        DynamicArray<String> a = new DynamicArray<>();
//        System.out.println(a.isEmpty()); //should print True
//        a.addFirst("ad");
//        a.addFirst("fed");
//        print(a); //should print [fed, ad]
//        a.addLast("gig");
//        print(a); //should print [fed, ad, gig]
//        a.delete(2);
//        a.deleteLast();
//        print(a); //should print [fed]
//        a.delete("gig"); //should not throw an exception
//        print(a); //should print the same list
//        a.deleteFirst();
//        print(a); //should print empty list
//        a.add(0, "kek");
//        a.add(1, "orbidol");
//        a.add(1, "lol");
//        print(a); //should print [kek lol orbidol]
//        System.out.println(a.size()); // should print 3
//        //a.add(4,"try"); //should throw an exception that index out of bounds
//        a.delete("orbidol");
//        a.delete("lol");
//        print(a); //should print [kek]
//        //a.delete(1); //should throw an exception that index out of bounds
//        a.deleteLast();
//        print(a); //should print empty list
//        //a.deleteLast(); //should throw an exception no such element
//        //a.deleteFirst(); //should also throw an exception no such element
//        a.addLast("ne");
//        a.addLast("zabivayte");
//        a.addLast("myagkiy");
//        a.addLast("znak");
//        a.addLast("v");
//        a.addLast("imeni");
//        a.addLast("sami");
//        a.addLast("znayete");
//        a.addLast("kogo");
//        print(a); //should print ne zabivayte myagkiy znak v imeni sami znayete kogo
//        a.set(5,"familii");
//        print(a); //should print ne zabivayte myagkiy znak v familii sami znayete kogo
//        //a.set(9, "naturlih"); //should throw an exception index out of bounds
    }


    /**
     * <h1>Print Tournaments</h1>
     * This function implements the printing of football
     * tournaments including the ranked list of teams.
     *
     * @param tournaments The ADT list of tournaments to print.
     */
    public static void printingTournaments(ADT<Tournament> tournaments){
        for(int i = 0; i < tournaments.size(); ++i){
            //prints the name of the i-th tournament
            System.out.println(tournaments.get(i).tournamentName);

            //prints the ranked list of teams
            for(int j = 0; j < tournaments.get(i).teams.size(); ++j){
                (tournaments.get(i).teams.get(j)).printInFormat((j + 1));
            }
            //prints the empty line between different tournaments
            System.out.println();
        }
    }

    /**
     * <h1>Read Tournaments</h1>
     * This function implements the reading of football
     * tournaments including the list of teams and played matches in the series.
     *
     * @param tournamentsToRead The number of tournaments you want to read.
     * @param input The object that is responsible for input of type Scanner.
     * @param tournaments The ADT list of tournaments, where the read tournaments wil be stored.
     */
    public static void readingTournaments(int tournamentsToRead, Scanner input, ADT<Tournament> tournaments) {
        for (int i = 0; i < tournamentsToRead; ++i){
            //input the name of the i-th tournament
            String nameOfTournament = input.nextLine();

            //input the number of teams in the i-th tournament
            int numberOfTeams = input.nextInt();

            //creates the tournament as an object
            Tournament tournament = new Tournament(nameOfTournament, numberOfTeams);

            //adding i-th tournament to the list of tournaments
            tournaments.add(i,tournament);

            //moves the cursor on the next line
            input.nextLine();

            /* input the teams of i-th tournament and
             * adding them in the structure of tournament
             */
            for(int j = 0; j < numberOfTeams; ++j){
                String nameOfNewTeam = input.nextLine();
                tournament.addTeam(nameOfNewTeam);
            }

            //input the number of played games in the tournament
            int numberOfGames = input.nextInt();
            input.nextLine();

            for(int j = 0; j < numberOfGames; ++j){
                /* separate the matches info in the form:
                 * match[0] - name of the first team
                 * match[1] - first team's score for this match
                 * match[2] - second team's score for this match
                 * match[3] - name of the second team
                 */
                String[] match = input.nextLine().split("[#:]");
                int firstScore = Integer.parseInt(match[1]);
                int secondScore = Integer.parseInt(match[2]);

                //updates the fields of two teams according to the match
                for(int k = 0; k < tournament.teams.size(); ++k){
                    Team curTeam = tournament.teams.get(k);
                    if((curTeam.getName()).equals(match[0])){
                        curTeam.update(firstScore, secondScore);
                    }else if((curTeam.getName()).equals(match[3])){
                        curTeam.update(secondScore, firstScore);
                    }
                }
            }

        }
    }

    /**
     * Sort the list passed as an argument.
     * Sorting algorithm is implemented by Insertion sort.
     * @param list List.
     * @param <E> Type of the elements in the list. They should be comparable.
     */
    public static <E extends Comparable> void sort(ADT list){
        for(int i = 1; i < list.size(); ++i){
            E key = (E)list.get(i);
            int j = i - 1;
            while( (j >= 0) && (((E)list.get(j)).compareTo(key) > 0) ){
                list.set((j + 1), list.get(j));
                j--;
            }
            list.set((j + 1), key);
        }
    }

    /**function for testing*/
//    public static void print(ADT list){
//        System.out.println("List:");
//        for(int i = 0; i < list.size(); ++i){
//            System.out.print(list.get(i) + " ");
//        }
//        System.out.println();
//    }
}

class Tournament{

    /*Variables*/

    String tournamentName;
    int numberOfTeams;
    DynamicArray<Team> teams;
    int games;

    /*Constructor*/

    public Tournament(String tournamentName, int numberOfTeams) {
        this.tournamentName = tournamentName;
        teams = new DynamicArray<>();
        games = 0;
        this.numberOfTeams = numberOfTeams;
    }

    /**
     * Adds the team to the list of teams in the
     * tournament with name passed as an argument.
     * @param name Name of the team.
     */
    public void addTeam(String name){
        Team team = new Team(name);
        teams.addFirst(team);
    }
}

class Team implements Comparable{

    /*Variables*/

    private String name;
    private int totalPoints;
    private int playedGames;
    private int wins;
    private int ties;
    private int losses;
    private int goalDifference;
    private int goalsScored;
    private int goalsAgainst;

    /*Constructor*/

    public Team(String name) {
        this.name = name;
        totalPoints = 0;
        playedGames = 0;
        wins = 0;
        ties = 0;
        losses = 0;
        goalDifference = 0;
        goalsScored = 0;
        goalsAgainst = 0;
    }

    /**How to compare two teams according to the task:
     * most points earned;
     * most wins;
     * most goal difference (i.e. goals scored – goals against);
     * case-insensitive lexicographic order
     */
    @Override
    public int compareTo(Object other) {
        if(this.getTotalPoints() > ((Team) other).getTotalPoints()){
            return -1;
        }else if(this.getTotalPoints() < ((Team) other).getTotalPoints()){
            return 1;
        }else if(this.getWins() > ((Team) other).getWins()){
            return -1;
        }else if(this.getWins() < ((Team) other).getWins()){
            return 1;
        }else if(this.getGoalDifference() > ((Team) other).getGoalDifference()){
            return -1;
        }else if(this.getGoalDifference() < ((Team) other).getGoalDifference()){
            return 1;
        }else if(this.getName().compareTo(((Team) other).getName()) < 0){
            return 1;
        }else if(this.getName().compareTo(((Team) other).getName()) > 0){
            return -1;
        }else{
            return 0;
        }
    }

    /*Getters and Adders*/


    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void addTotalPoints(int totalPoints) {
        this.totalPoints += totalPoints;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void addPlayedGames(int playedGames) {
        this.playedGames += playedGames;
    }

    public int getWins() {
        return wins;
    }

    public void addWins(int wins) {
        this.wins += wins;
    }

    public int getTies() {
        return ties;
    }

    public void addTies(int ties) {
        this.ties += ties;
    }

    public int getLosses() {
        return losses;
    }

    public void addLosses(int losses) {
        this.losses += losses;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void updateGoalDifference() {
        goalDifference = goalsScored - goalsAgainst;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void addGoalsScored(int goalsScored) {
        this.goalsScored += goalsScored;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void addGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst += goalsAgainst;
    }

    /*Help functions*/

    /**
     * This function gets the team's score and opponent's score
     * after that it modifies each field of the team like totalPoints, playedGames,
     * wins, ties, losses, goalsScored, goalsAgainst according to the match.
     * @param teamScore The number of points that the team got after match.
     * @param opponentScore The number of points that the team's opponent got after match.
     */
    public void update(int teamScore, int opponentScore){
        //this is one more game that the team has played
        this.addPlayedGames(1);

        //statistics about goals
        this.addGoalsScored(teamScore);
        this.addGoalsAgainst(opponentScore);
        this.updateGoalDifference();

        /* determining win or loss or tie
         * taking it into account
         * and adding corresponding points to total points of the team
         * 3 points for win
         * 1 point for tie
         * 0 points for loss
         */
        if(checkWin(teamScore, opponentScore)){
            this.addWins(1);
            //add points for win
            this.addTotalPoints(3);
        }
        else if(checkTie(teamScore, opponentScore)){
            this.addTies(1);
            //add points for tie
            this.addTotalPoints(1);
        }
        else{
            this.addLosses(1);
            //for loss there are no points are given
        }
    }

    public boolean checkWin(int yourPoints, int opponentPoints){
        if(yourPoints > opponentPoints){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkTie(int yourPoints, int opponentPoints){
        if (yourPoints == opponentPoints){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkLoss(int yourPoints, int opponentPoints){
        if(yourPoints < opponentPoints){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Print the team's place in the format.
     * [a]) [Team_name] [b]p, [c]g ([d]–[e]-[f]), [g]gd ([h]-[i]), where
     * [a] is team rank,
     * [b] is the total points earned,
     * [c] is the number of games played,
     * [d] is wins,
     * [e] is ties,
     * [f] is losses,
     * [g] is goal difference,
     * [h] is goals scored and
     * [i] is goals against.
     * @param rank The place of the team in the tournament.
     */
    public void printInFormat(int rank) {
        System.out.print(rank + ")" + " ");
        System.out.print(this.getName() + " ");
        System.out.print(this.getTotalPoints() + "p," + " ");
        System.out.print(this.getPlayedGames() + "g" + " ");
        System.out.print("(" + this.getWins() + "-" + this.getTies() + "-" + this.getLosses() + ")," + " ");
        System.out.print(this.getGoalDifference() + "gd" + " ");
        System.out.print("(" + this.getGoalsScored() + "-" + this.getGoalsAgainst() + ")");
        System.out.println();
    }
}

class DynamicArray<E> implements ADT<E>{

    /*Variables*/

    //size of the array
    private int elements = 0;

    //array of generic type for keeping elements
    private E[] array;

    /*Constructors*/

    public DynamicArray() {
        array = (E[]) new Object[elements];
    }

    /*Operations*/

    @Override
    public boolean isEmpty() {
        return elements == 0;
    }

    @Override
    public void add(int i, Object o) {
        checkIndex(i, elements + 1);

        /* If array is empty, then the size of array will turn to 1.
         * If array is full, the size of it increases twice.
         */
        if(isEmpty()){
            resize(1);
        }
        else if(elements == array.length){
            resize(2 * elements);
        }

        //shifting all elements after (i-1)-th position to the right by one
        int j = elements;
        while(j > i){
            array[j] = array[j-1];
            j--;
        }

        //putting desired element to the i-th position
        array[i] = (E) o;

        //incrementing our size variable due to adding one element
        elements++;
    }

    @Override
    public void addFirst(Object o) {
        /* If array is empty, then the size of array will turn to 1.
         * If array is full, the size of it increases twice.
         */
        if(isEmpty()){
            resize(1);
        }
        else if(elements == array.length){
            resize(2 * elements);
        }

        elements++;

        int j = elements - 1;
        while(j > 0){
            array[j] = array[j-1];
            j--;
        }

        array[0] = (E) o;
    }

    @Override
    public void addLast(Object o) {
        /* If array is empty, then the size of array will turn to 1.
         * If array is full, the size of it increases twice.
         */
        if(isEmpty()){
            resize(1);
        }
        else if(elements == array.length){
            resize(2 * elements);
        }

        elements++;
        array[elements-1] = (E) o;
    }

    @Override
    public void delete(Object o) {
        //find the index of element that equals o and delete it
        for(int i = 0; i < elements; ++i){
            if(array[i] == (E) o){
                delete(i);
                elements--;
                break;
            }
        }
    }

    @Override
    public void delete(int i) {
        checkIndex(i, elements);

        int j = i;

        /* Shifting all elements that were after i-th element to the left by one.
         * So, by this our i-th element will be lost.
         * We get the same result as after deleting.
         */
        while(j < elements - 1){
            array[j] = array[j+1];
            j++;
        }

        //Our size decreases by one
        elements--;
    }

    @Override
    public void deleteFirst() {
        /* handling the case, when the array is empty:
         * There is nothing to delete
         */
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        array[0] = null;

        //shifting all remaining elements to the left by one
        for(int i = 0; i < elements - 1; ++i){
            array[i] = array[i+1];
        }

        /* we just decrease our size of array by one
         * by doing it, we delete last element of array
         * because now it is duplicate of the previous element in array
         */
        elements--;
    }

    @Override
    public void deleteLast() {
        /* handling the case, when the array is empty:
         * There is nothing to delete
         */
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        /* we just decrease our size of array by one
         * by doing it, we exactly delete last element of array
         */
        elements--;
    }

    @Override
    public void set(int i, Object o) {
        checkIndex(i, elements);
        array[i] = (E) o;
    }

    @Override
    public E get(int i) {
        checkIndex(i, elements);
        return array[i];
    }

    @Override
    public int size() {
        return elements;
    }

    /*Help-Functions*/

    /**
     * checkIndex is the function which implements checking
     * whether i is in the range[0, n - 1].
     * @exception IndexOutOfBoundsException if i is not in the range.
     * @param i Index of the element in the Linked List.
     * @param n The upper bound for any i.
     */
    private void checkIndex(int i, int n){
        if(i < 0 || i >= n){
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Resize the array's size to given n (n >= old size).
     * If n < old size, array will lose last items.
     * @param n New array's size.
     */
    private void resize(int n){
        //creates an array with size n
        E[] newArray = (E[]) new Object[n];

        //copies elements from the array with smaller size to the new array
        for(int i = 0; i < elements; ++i){
            newArray[i] = array[i];
        }

        /* makes array reference to new array.
         * So, the previous array with smaller size
         * is the work for garbage collector now.
         */
        array = newArray;
    }
}

class DoublyLinkedList<E> implements ADT<E>{

    /*Variables*/

    private Node<E> head;
    private Node<E> tail;
    private int elements = 0;

    /*Constructor*/

    public DoublyLinkedList() {
        head = new Node<>(null,null,null);
        tail = new Node<>(null, head, null);
        head.setNext(tail);
    }

    /*Operations*/

    @Override
    public boolean isEmpty() {
        return elements == 0;
    }

    @Override
    public void add(int i, E e) {
        checkIndex(i,elements + 1);

        if(i == elements){
            addLast(e);
        }
        else{
            //the node that is at position i now will be the next node for new one
            Node<E> next = getNode(i);
            //the node that is at position (i-1) now will be the previous node for new one
            Node<E> previous = next.getPrevious();
            /* creating a new node and add references to other nodes
             * prev<-newNode->next
             */
            Node<E> newNode = new Node<>(e,previous,next);

            //previous->next  =>  previous->newNode
            previous.setNext(newNode);
            //previous<-next  =>  newNode<-next
            next.setPrevious(newNode);

            //increasing size of list by one because we added the element
            elements++;
        }


    }

    @Override
    public void addFirst(E e) {
        /* create new node, put the link to the head, because
         * now this element turns to be the first
         * and also put the link on the next element,
         * which was after head
         * head<->prevFirst  =>  head<-temp->prevFirst
         */
        Node<E> temp = new Node<>(e, head, head.getNext());

        /* connect head with new element
         * head->prevFirst  =>  head->temp
         */
        head.setNext(temp);

        /* connect previous first element
         *(it will be second element now) with new first element
         * head<-prevFirst  =>  temp<-prevFirst
         */
        temp.getNext().setPrevious(temp);

        //increase the size of list by one
        elements++;
    }

    @Override
    public void addLast(E e) {
        /* create new node, put the link to the previous last element, because
         * now THIS element turns to be the last
         * and also put the link to the tail
         * prevLast<->tail  =>  prevLast<-temp->tail
         */
        Node<E> temp = new Node<>(e, tail.getPrevious(), tail);

        /* connect the previous last element(it will be second
         * from the end now) with new last element
         * prevLast->tail  =>  prevLast->temp
         */
        tail.getPrevious().setNext(temp);

        /* connect the tail with new last element
         * prevLast<-tail  =>  temp<-tail
         */
        tail.setPrevious(temp);

        //increases the size of array by one
        elements++;
    }

    @Override
    public void delete(E e) {
        //get the first node
        Node<E> toDelete = head.getNext();

        //checking each node's value with e
        for(int i = 0; i < elements; ++i){
            if(toDelete.getCurrent() == e){

                //delete found node with value e
                deleteNode(toDelete);

                //we met the element with value e, so we can exit from loop
                break;
            }

            //get next node to check on next step of the loop
            toDelete = toDelete.getNext();
        }
    }

    @Override
    public void delete(int i) {
        checkIndex(i,elements);

        //get the node at the position i
        Node<E> toDeleteElement = getNode(i);

        //delete that node, so element will be also deleted
        deleteNode(toDeleteElement);
    }

    @Override
    public void deleteFirst() {
        /* handling the case, when the list is empty:
         * There is nothing to delete
         */
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        /* second node from the beginning now references to the head-node
         * this way we delete first element
         * head<-First<-newFirst  =>  head<-newFirst
         */
        Node<E> newFirst = head.getNext().getNext();
        newFirst.setPrevious(head);

        /* and make the newFirst node to be next for head
         * head->First->newFirst  =>  head->newFirst
         */
        head.setNext(newFirst);

        elements--;
    }

    @Override
    public void deleteLast() {
        /* handling the case, when the array is empty:
         * There is nothing to delete
         */
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        /* second node from the end now references to the tail-node
         * this way we delete last element
         * newLast->Last->tail  =>  newLast->tail
         */
        Node<E> newLast = tail.getPrevious().getPrevious();
        newLast.setNext(tail);

        /* and make the newLast node to be previous for tail
         * newLast<-Last<-tail  =>  newLast<-tail
         */
        tail.setPrevious(newLast);

        elements--;
    }

    @Override
    public void set(int i, E e) {
        checkIndex(i, elements);

        //get the node at the position i
        Node<E> toSet = getNode(i);

        //set the value of current node to e
        toSet.setCurrent(e);
    }

    @Override
    public E get(int i) {
        checkIndex(i,elements);

        //get the node at the position i
        Node<E> toGet = getNode(i);

        //return value of node at the position i
        return toGet.getCurrent();
    }

    @Override
    public int size() {
        return elements;
    }

    /*Help-Functions*/

    /**
     * checkIndex is the function which implements checking
     * whether i is in the range[0, n - 1].
     * @exception IndexOutOfBoundsException if i is not in the range.
     * @param i Index of the element in the Linked List.
     * @param n The upper bound for any i.
     */
    private void checkIndex(int i, int n){
        if(i < 0 || i >= n){
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * getNode is the function which returns the Node on position i.
     * <b>Note:</b> this function will throw an exception if i is
     * out of range of the list.
     * @param i Index of the element in Linked List we want to get.
     * @return Node in the Linked List on position i.
     */
    private Node<E> getNode(int i){
        //checks whether the index os correct
        checkIndex(i,elements);

        int j = 0;

        //gets the first node
        Node<E> element = head.getNext();

        //gets the appropriate node at position i
        while(j != i){
            element = element.getNext();
            j++;
        }

        //returns found node
        return element;
    }

    /**
     * deleteNode is the function that deletes
     * the Node passed as an argument.
     * @param toDeleteNode Node that you want to delete.
     * @exception NoSuchElementException if we trying to delete the node that is not in the list.
     */
    private void deleteNode (Node<E> toDeleteNode) {
        try {
            Node<E> previous = toDeleteNode.getPrevious();
            Node<E> next = toDeleteNode.getNext();

            //previous->toDelete->next  =>  previous->next
            previous.setNext(next);
            //previous<-toDelete<-next  =>  previous<-next
            next.setPrevious(previous);

            toDeleteNode.setNext(null);
            toDeleteNode.setPrevious(null);

            //decreases the size of the list by one
            elements--;
        }catch (NullPointerException e){
            throw new NoSuchElementException();
        }
    }
}

/**
 * Node is the basic element of Linked List.
 *
 * @param <E> Type of element
 */
class Node<E>{

    /*Variables*/

    private E current;
    private Node<E> previous;
    private Node<E> next;

    /*Constructor*/

    public Node(E current, Node<E> previous, Node<E> next) {
        this.current = current;
        this.previous = previous;
        this.next = next;
    }

    /*Getters and Setters*/

    public E getCurrent() {
        return current;
    }

    public Node<E> getPrevious() {
        return previous;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setCurrent(E current) {
        this.current = current;
    }

    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
}

/**
 * ADT is an ordered collection of objects.
 * @param <E> Type of element
 */
interface ADT<E> {
    /**
     * Check if the list is empty.
     * @return true if it is empty, and false otherwise.
     */
    boolean isEmpty();

    /**
     * Add element of type E at position i.
     * <b>Note:</b> this function will throw an exception
     * if there will be i which is out of range[0, sizeOfList].
     * @param i Position in the array.
     * @param e Element.
     */
    void add(int i, E e);

    /**
     * Add element of type E to the start of the list.
     *
     * @param e Element.
     */
    void addFirst(E e);

    /**
     * Add element of type E to the end of the list.
     * @param e Element.
     */
    void addLast(E e);

    /**
     * Delete element of type E if it exists in the list.
     * @param e Element.
     */
    void delete(E e);

    /**
     * Delete element at position i.
     * <b>Note:</b> throws an exception if i is out
     * of range of the list.
     * @param i Position of the element in the list.
     */
    void delete(int i);

    /**
     * Remove the first element from the list.
     * <b>Note:</b> throws an exception if list is empty.
     * @exception NoSuchElementException
     */
    void deleteFirst();

    /**
     * Remove the last element from the list.
     * <b>Note:</b> throws an exception if list is empty.
     * @exception NoSuchElementException
     */
    void deleteLast();

    /**
     * Replace element at position i with new element of type E.
     * <b>Note:</b> throws an exception if i is out of range of the list.
     * @param i Position.
     * @param e Element.
     */
    void set(int i, E e);

    /**
     * Retrieve element at position i.
     * <b>Note:</b> throws an exception if i is out of range of the list.
     * @param i Position.
     * @return The element at position i.
     */
    E get(int i);

    /**
     * Returns the size of list.
     * @return Size.
     */
    int size();
}