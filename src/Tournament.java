/** Nataliya Matrosova
 * B18-06 group
 *
 * Codeforces submission number is:
 * 49862629
 */

import java.io.IOException;
import java.util.*;
import java.util.Comparator;



public class Tournament {
    /*List ADT interface*/
    public interface MyList<E>{
        /*returns true if list contains 0 elements, False otherwise*/
        boolean isEmpty();

        /*adds element i at index i, shifts all following elements to the end of the list*/
        void add(int i, E e) throws  IndexOutOfBoundsException;

        /*adds element e at the beginning of the list*/
        void addFirst(E e);

        /*adds element e at the end of the list*/
        void addLast(E e);

        /*removes element e from the list (if it exists)*/
        void delete(E e) throws IllegalArgumentException;

        /*removes element at index i*/
        void delete(int i) throws  IndexOutOfBoundsException;

        /*removes the first element from the list */
        void deleteFirst() throws IllegalStateException;

        /*removes the last element from the list*/
        void deleteLast() throws IllegalStateException;

        /*replace the element at index i (if it exists) with el. e*/
        void set(int i, E e) throws IndexOutOfBoundsException;

        /*returns the element at index i (if it exists)*/
        E get(int i) throws IndexOutOfBoundsException;
    }

    /* List ADT implementation via Dynamic Array */
    public static class DynArrayList<E> implements MyList<E> {
        /*instance variables*/
        private static final int CAPACITY = 64;                                 //default array capacity
        private E[] data;                                                       //generic array used for storage
        private int size = 0;                                                   //default size

        /*constructors*/

        public DynArrayList() {
            data = (E[]) new Object[CAPACITY];               //constructs list with default capacity
        }

        public DynArrayList(int capacity) {                  //constructs  the list with given capacity
            data = (E[]) new Object[capacity];
        }


        public int getSize() {
            return size;
        }                // returns the size of the list



        protected void validIndex(int i) throws IndexOutOfBoundsException {      // checks whether it is possible to add an element at index i
            if (i < 0 || i >= size) {
                throw new IndexOutOfBoundsException("Invalid index " + i);
            }
        }

        protected int IndOfElement(E e) {             // returns the index of element e
            for (int i = 0; i < size; i++) {          // if it is not in the list returns -1
                if (e.equals(data[i])) {
                    return i;
                }
            }
            return -1;
        }

        protected void checkSize() {                 // checks whether it is possible to add an element to a list
            if (size == data.length) {               // if the capacity of a current array is not big enough
                resize(2 * data.length);      // invokes 'resize' routine
            }
        }

        protected void resize(int capacity) {                // creates a new array with double capacity
            E[] temp = (E[]) new Object[capacity];           // copies all elements from the current array to the new one
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }

        public static Comparator<Team> MyComparator = (Team b, Team a) -> {   // compares to elements of a list
            if (a.points != b.points)                                         // if <E> do not extend comparable
                return a.points - b.points;                                   // only the sign of a return statement matters
            else if (a.gamesWon != b.gamesWon)
                return a.gamesWon - b.gamesWon;
            else if (a.goalDiff != b.goalDiff)
                return a.goalDiff - b.goalDiff;
            else
                return a.name.toLowerCase().compareTo(b.name.toLowerCase());
        };



        public void sort(Comparator<E> comparator) {           // sort for lists which elements are needed to be compared using comparator
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (comparator.compare(data[j], data[i]) < 0) {
                        E c = data[i];
                        data[i] = data[j];
                        data[j] = c;
                    }
                }
            }
        }

        @Override
        public boolean isEmpty() {
            return (getSize() == 0);
        }

        /**
         * adds element with value e to the index i
         * possible to add to the end of the list
         *
         * @param i index of an element to be added
         * @param e value of an element to be added
         * @throws IndexOutOfBoundsException
         * @throws IllegalStateException
         *
         * was tested by:
         * 1. adding elements to the first and last positions
         * 2. adding elements to the middle
         * 3. adding element to position size
         * (similar to add last)
         * 4. add the element to the full list (size == capacity)
         *      -> invokes resize
         */
        @Override
        public void add(int i, E e) throws IndexOutOfBoundsException,
                IllegalStateException {
            if (i == size){
                addLast(e);
            }
            else {
                validIndex(i);
                checkSize();
                for (int j = size - 1; j >= i; j--) {
                    data[j + 1] = data[j];
                }
                data[i] = e;
                size++;
            }
        }

        /**
         * inserts element with value e to the beginning of the list
         * "shifts" following elements on one position to the end
         *
         * @param e value to be added
         * @throws IllegalStateException
         */
        @Override
        public void addFirst(E e) throws IllegalStateException {
            checkSize();
            for (int j = size - 1; j >= 0; j--) {
                data[j + 1] = data[j];
            }
            data[0] = e;
            size++;
        }

        /**
         * adds the element with value e to the and of the list
         * @param e value of the element to be added
         * @throws IllegalStateException
         */
        @Override
        public void addLast(E e) throws IllegalStateException {
            checkSize();
            data[size] = e;
            size++;
        }

        /**
         * deletes the element at index i from the list
         * shifts all the following elements one position to the beginning
         * of the list
         * @param i index of an element to be deleted
         * @throws IndexOutOfBoundsException
         * @throws IllegalStateException
         *
         * was tested by:
         * 1. delete first one and last one
         * 2. delete in the middle
         * 3. delete with invalid index
         */
        @Override
        public void delete(int i) throws IndexOutOfBoundsException, IllegalStateException {
            validIndex(i);
            if (this.isEmpty()) {
                throw new IllegalStateException("List is empty");
            }
            for (int j = i; j < size - 1; j++) {
                data[j] = data[j + 1];
            }
            data[size - 1] = null;
            size--;
        }

        /**
         * deletes the first element from the list
         * shifts all the following elements to the beginning of the list
         **/
        @Override
        public void deleteFirst() {
            if (this.isEmpty()) {
                throw new IllegalStateException("List is empty");
            }
            for (int j = 0; j < size - 1; j++) {
                data[j] = data[j + 1];
            }
            data[size - 1] = null;
            size--;
        }

        /**
         * deletes the last element from the list
         */
        @Override
        public void deleteLast() {
            if (this.isEmpty()) {
                throw new IllegalStateException("List is Empty");
            }
            data[size - 1] = null;
            size--;
        }

        /**
         * replace the value of an element at index i with e
         * @param i index of an element to be set
         * @param e value of an element to be set
         * @throws IndexOutOfBoundsException
         *
         * was tested by:
         * 1. set the first and the last elements
         * 2. set the middle elements
         * 3. set with invalid index
         */
        @Override
        public void set(int i, E e) throws IndexOutOfBoundsException {
            validIndex(i);
            data[i] = e;
        }

        /**
         * return the value of an element at position i
         * @param i index of an element to return
         * @return value of an element at index i
         * @throws IndexOutOfBoundsException
         *
         * was tested by:
         * 1. get first and last elements
         * 2. get an element in the middle
         * 3. get an element with invalid index
         */
        @Override
        public E get(int i) throws IndexOutOfBoundsException {
            validIndex(i);
            return data[i];
        }

        /**
         * removes the first element with value e from the list
         * if it is not in the list -> throws an exception
         * @param e value of a node to be deleted
         *
         * @throws IllegalStateException
         * @throws IllegalArgumentException
         *
         * was tested by:
         * 1. deleting the first and the last elements
         * 2. trying to delete the element which is not in the list
         *     -> exception
         * 3. delete element from the middle of the list
         * 4. delete element e from list with several e's elements
         */
        @Override
        public void delete(E e) {
            if (IndOfElement(e) != -1) {
                delete(IndOfElement(e));
            } else {
                System.out.println("There is no element " + e + " in the list");
            }
        }
    }


    /**
     * Class to be stored as Elements
     * in the list.
     *
     */
    public static class Team {
        int gamesWon = 0;
        int gamesLose = 0;
        int gamesTie = 0;
        int pointsWon = 0;
        int pointsLose = 0;
        int goalDiff = 0;
        int games = 0;
        int points = 0;

        String name;

        Team(String TeamName) {
            name = TeamName;
        }
    }


    /**
     * Was tested by:
     * 1. All teams have the same score
     * 2. All teams have zero score
     * 3. All team have max score (19)
     * 4. Maximum number of teams (30)
     * 5. Maximum number of tournaments (1000)
     * 6. One team in a tournament
     * 7. Zero games in a tournament
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++){
            System.out.println(sc.nextLine());
            // do not store Tournament name
            // read and print it
            int t = sc.nextInt();
            sc.nextLine();
            DynArrayList<Team> Teams = new DynArrayList<>(t + 2);
            // create List to store teams info
            for (int j = 0; j < t; j++){
                Team team = new Team(sc.nextLine());
                Teams.add(j, team);
            }
            int g = sc.nextInt();
            sc.nextLine();
            for (int k = 0; k < g; k++){
                String[] ds = sc.nextLine().split("[:#]");
                // read and assign game results to teams participated in the game
                int score1 = Integer.parseInt(ds[1]);
                int score2 = Integer.parseInt(ds[2]);
                for (int c1 = 0; c1 < t; c1++){
                    for (int c2 = 0; c2 < t; c2++) {
                        Team team1 = Teams.get(c1);
                        Team team2 = Teams.get(c2);

                        if ((team1.name.equals(ds[0]) && (team2.name.equals(ds[3])))){
                            // looking for team with the same name in the list
                            // assigning point earned
                            int ind1 = Teams.IndOfElement(team1);
                            int ind2 = Teams.IndOfElement(team2);
                            team1.games++;
                            team2.games++;
                            if (score1 - score2 > 0) {
                                team1.gamesWon++;
                                team1.points += 3;
                                team2.gamesLose++;
                            } else if (score1 < score2){
                                team1.gamesLose++;
                                team2.points += 3;
                                team2.gamesWon++;
                            }
                            else{
                                team1.gamesTie++;
                                team1.points++;
                                team2.points++;
                                team2.gamesTie++;
                            }
                            team2.pointsWon += score2;
                            team2.pointsLose += score1;
                            team1.pointsWon += score1;
                            team1.pointsLose += score2;
                            team1.goalDiff = team1.pointsWon - team1.pointsLose;
                            team2.goalDiff = team2.pointsWon - team2.pointsLose;
                            Teams.set(ind1, team1);
                            Teams.set(ind2, team2);
                            break;
                        }
                    }
                }
            }
            Teams.sort(DynArrayList.MyComparator);
            // sort list according to the results
            for (int tc = 0; tc < t; tc++){
                Team team = Teams.get(tc);
                System.out.printf("%d) %s %dp, %dg (%d-%d-%d), %dgd (%d-%d)\n", tc + 1, team.name, team.points, team.games, team.gamesWon, team.gamesTie, team.gamesLose, team.goalDiff, team.pointsWon, team.pointsLose);
            }
            System.out.println();
            // blank line between tournaments
        }
    }
}

