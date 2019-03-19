/*List ADT interface*/
public interface List<E>{

    /*returns true if list contains 0 elements, False otherwise*/
    boolean isEmpty();

    /*adds element i at index i, shifts all following elements to the end of the list*/
    void add(int index, E element) throws  IndexOutOfBoundsException;

    /*adds element e at the beginning of the list*/
    void addFirst(E element);

    /*adds element e at the end of the list*/
    void addLast(E e);

    /*removes element e from the list (if it exists)*/
    void delete(E e);

    /*removes element at index i*/
    void delete(int i) throws  IndexOutOfBoundsException;

    /*removes the first element from the list */
    void deleteFirst();

    /*removes the last element from the list*/
    void deleteLast();

    /*replace the element at index i (if it exists) with el. e*/
    void set(int i, E e) throws IndexOutOfBoundsException;

    /*returns the element at index i (if it exists)*/
    E get(int i) throws IndexOutOfBoundsException;

    /*sorts the list in ascending order*/
    void sort();




}
