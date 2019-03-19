/** Nataliya Matrosova
 * B18-06 group
 */

import java.io.IOException;

/* List ADT implementation via Double Linked List */
public class DoubleLinkedList<E> implements List<E> {


    private static class Node<E>{

        /*instance variables*/
        private E element;       // current element
        private Node<E> prev;    // previous element
        private Node<E> next;    // next element

        /*constructor*/
        private Node(E el, Node<E> pr, Node<E> nx){
            element  = el;
            prev = pr;
            next = nx;
        }

    }

    private Node<E> head;        // head sentinel
    private Node<E> tail;        // tail sentinel
    private int size = 0;        // default number of elements in the list

    /**
     * constructor empty DLL
     */

    private DoubleLinkedList(){
        head = new Node<>(null, null, null);
        tail = new Node<>(null, head, null);
        head.next = tail;
    }

    /**
     * checks whether the index exists in the list
     * @param i index to be inspected
     * @throws IndexOutOfBoundsException
     *
     */
    private void checkBounds(int i) throws IndexOutOfBoundsException{
        if (i < 0 || i >= size){
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }
    }

    /**
     * returns the index of element e, returns -1 if element is not in the list
     *
     * @param e the element to find an index
     * @return
     */
    protected int IndexOfElement(E e){
        Node<E> current = head;
        for (int i = 0; i < size; i++){
            current = current.next;
            if (e.equals(current.element)){
                return i;
            }
        }
        return -1;
    }

    /**
     * @return True if empty
     * False otherwise
     */
    @Override
    public boolean isEmpty() {               // return False if the list contains elements, true otherwise
        return size == 0;
    }

    /**
     * inserts an element e into position i
     * position i == size considered to be valid
     *
     * @param i index of an element to be added
     * @param e value of an element to be added
     * @throws IndexOutOfBoundsException
     *
     * was tested by:
     * 1. add to the first and to the last positions
     * 2. add to the middle
     * 3. add with invalid index
     *
     */
    @Override
    public void add(int i, E e) throws IndexOutOfBoundsException {
        if (i == size){
            addLast(e);

        }
        else {
            checkBounds(i);

            Node<E> current = head;
            for (int j = 0; j <= i; j++) {
                current = current.next;
            }
            Node<E> element = new Node(e, current.prev, current);

            element.next.prev = element;
            element.prev.next = element;
            size++;
        }
    }

    /**
     * adds element with value e to the beginning of the list
     * shifts all the following to the end of the list
     * @param e value of an element to be added to the list
     */
    @Override
    public void addFirst(E e) {
        Node<E> element = new Node (e, head, head.next);
        head.next = element;
        element.next.prev = element;
        size++;
    }

    /**
     * add the element with value e to the end of the list
     * @param e the value of the element to be added
     */
    @Override
    public void addLast(E e) {
        Node<E> element = new Node (e, tail.prev, tail);
        tail.prev = element;
        element.prev.next = element;
        size++;
    }

    /**
     * change the value of element at index i into e
     * @param i an index of an element to be set
     * @param e value of an element to be set
     * @throws IndexOutOfBoundsException
     *
     * was tested by:
     * 1. set first and last elements
     * 2. set elements in the middle of the list
     * 3. set element with invalid index
     *
     */
    @Override
    public void set(int i, E e) throws IndexOutOfBoundsException {
        checkBounds(i);
        Node<E> current = head;
        for (int j = 0; j <= i; j++){
            current = current.next;
        }
        current.element = e;
    }

    /**
     * return the value of an element at position i
     * @param i index of an element to return
     * @return value of an element at index i
     * @throws IndexOutOfBoundsException
     *
     * was tested by:
     * 1. get first and last elements
     * 2. get an elent in the middle
     * 3. get an element with invalid index
     */
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        checkBounds(i);
        if (i == size){
            throw new IndexOutOfBoundsException("Invalid index " + i);
        }
        Node<E> current = head;
        for (int j = 0; j <= i; j++){
            current = current.next;
        }
        return  current.element;

    }

    /**
     * removes the element at position i from the list
     * @param i index of an element to be deleted
     * @throws IndexOutOfBoundsException
     * @throws IllegalStateException
     *
     * was tested by:
     * 1. delete element in the middle of the list
     * 2. delete first and last elements
     * 3. delete an element which index is out of bounds
     */
    @Override
    public void delete(int i) throws IndexOutOfBoundsException, IllegalStateException {
        if (this.isEmpty()){
            throw new IllegalStateException("List is empty");
        }
        Node<E> current = head;
        for (int j = 0; j < i; j++){
            current = current.next;
        }
        current.next = current.next.next;
        current.next.prev = current;
        size--;
    }

    /**
     * removes the first element from the list
     * @throws IllegalStateException
     */
    @Override
    public void deleteFirst() throws IllegalStateException {
        if (this.isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        head.next.prev = null;
        head = head.next;
        size--;
    }

    /**
     * removes the last element from the list
     * @throws IllegalStateException
     */
    @Override
    public void deleteLast() throws IllegalStateException {
        if (this.isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        tail.prev.next = null;
        tail = tail.prev;
        size--;
    }

    /**
     * removes the element with value e from the list
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
     */
    @Override
    public void delete(E e) throws IllegalStateException, IllegalArgumentException{
        if (this.isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        int i = IndexOfElement(e);
        if (i != -1){
            Node<E> current = head;
            for (int j = 0; j < i; j++){
                current = current.next;
            }
            current.next = current.next.next;
            current.next.prev = current;
            size--;
        }
        else{
            throw new IllegalArgumentException("There is no element " + e + " in the list");
        }
    }

    /**
     * sorts the DLL in ascending order
     * (swaps the elements of a nodes, if the order needed)
     * was tested by:
     * 1. reverse sorted order Dll
     * 2. 'unsorted' element in the first and last positions
     * 3. sort empty list
     */
    @Override
    public void sort() {
        Node<E> current1 = head.next;
        Node<E> current2;
        for (int i = 0; i < size; i++){
            current2 = current1.next;
            for (int j = i + 1; j < size; j++){
                if (((Comparable) current2.element).compareTo(current1.element) < 0) {
                    E temp = current1.element;
                    current1.element = current2.element;
                    current2.element = temp;
                }
                current2 = current2.next;
            }
            current1 = current1.next;
        }

    }

    public static void main(String[] args) throws IOException {

        DoubleLinkedList<Integer> TestDLL = new DoubleLinkedList();
        TestDLL.sort();
        TestDLL.add(0, new Integer(3));
        TestDLL.add(1, new Integer(3));
        TestDLL.add(2, new Integer(2));
        TestDLL.add(3, new Integer(5));
        TestDLL.add(4, new Integer(6));
        TestDLL.addLast(new Integer(7));
        //TestDLL.get(5);
        //TestDLL.delete(6);
        //TestDLL.sort();



        for(int i = 0; i < TestDLL.size; i++){
            System.out.printf("The element %d is %s\n", i, TestDLL.get(i));
        }


    }




}
