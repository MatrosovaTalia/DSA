/** Nataliya Matrosova
 * B18-06 group
 */

import java.io.IOException;

/* List ADT implementation via Dynamic Array */
public class DynArrayList<E> implements MyList<E>{
    /*instance variables*/
    private static final int CAPACITY = 64;    // default array capacity
    private E[] data;         // generic array used for storage
    private int size = 0;     // default size

    /*constructors*/

    /**
     * default capacity constructor
     */
    public DynArrayList() {
        data =(E[]) new Object[CAPACITY];
    }

    /**
     * constructor with given capacity
     * @param capacity the size of an array to be created
     */
    public DynArrayList(int capacity) {
        data = (E[]) new Object[capacity];
    }


    /**
     * creates a new arary with given capacity
     * copies all the elements from the current array to the new one
     *
     * @param capacity capacity of an array to be created
     *
     */
    protected void resize(int capacity){
        E[] temp = (E[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }
            data = temp;
    }

    /**
     * checks whether the index is valid
     * @param i index to be examined
     * @throws IndexOutOfBoundsException
     */
    protected void validIndex(int i) throws IndexOutOfBoundsException{
        if (i < 0 || i >= size){
            throw new IndexOutOfBoundsException("Invalid index " + i);
        }
    }

    /**
     * @return index of an element if it is in the list
     *          -1 otherwise
     */
    protected int IndOfElement(E e){
        for (int i = 0; i < size; i++){
            if (e.equals(data[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * check whether the array need to be resised to a bigger one to add an element
     */
    protected void checkSize(){
        if (size == data.length){
            resize( 2 * data.length);
        }
    }

    /**
     * sort for the list which elements extend comparable
     * ascending order
     *
     * was tested by:
     * 1. reverse sorted order Dll
     * 2. 'unsorted' element in the first and last positions
     * 3. sort empty list
     */
    @Override
    public void sort() {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (((Comparable) data[i]).compareTo(data[j]) > 0) {
                    E c = data[i];
                    data[i] = data[j];
                    data[j] = c;

                }
            }
        }
    }

    /**
     * @return True if empty
     * False otherwise
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
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
        if (this.isEmpty()){
            throw new IllegalStateException("List is empty");
        }
        for (int j = i; j < size - 1; j++){
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
        if (this.isEmpty()){
            throw new IllegalStateException("List is empty");
        }
        for (int j = 0; j < size - 1; j++){
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
    public void delete(E e) throws IllegalArgumentException{
        if (IndOfElement(e) != -1){
            delete(IndOfElement(e));
        }
        else{
            throw new IllegalArgumentException("There is no element " + e + " in the list");
        }
    }
    public static void main(String[] args) throws IOException {
        //Scanner input = new Scanner(new FileInputStream("input"));
        //PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
        //Scanner input = new Scanner(System.in);
        //String expression = input.nextLine();

        DynArrayList<Integer> TestList = new DynArrayList(5);
        //System.out.printf("Is Empty: %b\n", TestList.isEmpty());

        TestList.sort();


        TestList.add(0, new Integer(3));
        TestList.add(1, new Integer(4));
        TestList.add(2, new Integer(2));
        TestList.add(3, new Integer(3));
        TestList.add(4, new Integer(6));
        TestList.addLast(new Integer(7));
        TestList.delete(new Integer(3));
        TestList.sort();


        for(int i = 0; i < TestList.size; i++){
            System.out.printf("The element %d is %d\n", i, TestList.get(i));
        }

    }

}
