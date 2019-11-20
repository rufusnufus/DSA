/** @author Rufina Talalaeva **/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


        //Number of command lines in the input
        int N = Integer.parseInt(in.readLine());

        ArrayList<data> datas = new ArrayList<>();
        todoList list = new todoList();

        for (int i = 0; i < N; i++) {
            String[] s = in.readLine().split("\\s+");

            if(s[0].equals("DO")){
                String[] dates = s[1].split("\\.");
                data date = new data(dates[0], dates[1], dates[2]);
                list.solve(date);
            }else{
                String[] dates = s[0].split("\\.");
                data date = new data(dates[0], dates[1], dates[2]);
                String name = s[1];
                Integer priority = Integer.parseInt(s[2]);

                boolean contain = false;
                for (int j = 0; j < datas.size(); j++) {

                    if (datas.get(j).res == date.res) {
                        contain = true;
                    }
                }
                if (!contain) {
                    datas.add(date);
                }

                task newTask = new task(date, name, priority);
                list.add(newTask);
            }
        }
        datas.sort(data::compareTo);

        for (data i: datas) {
            System.out.println("TODOList " + i.strRes);
            while (list.todo.get(i).max().k != null) {
                System.out.println("    "+list.todo.get(i).max().v);
                list.todo.get(i).removeMax();
            }
        }
    }
}

class data implements Comparable<data> {
    int res;
    String strRes;

    public data(String dd, String mm, String yy){
        res = Integer.parseInt(yy)*10000 + Integer.parseInt(mm)*100 + Integer.parseInt(dd);
        strRes = dd + "." + mm + "." + yy;
    }
    @Override
    public int compareTo(data other) {
        return res - other.res;
    }
}

/**
 * Class for task. A task consists of a name (a short description)
 * and priority value (an integer).
 */
class task{
    private data date;
    private String name;
    private int priority;

    /** Constructor **/
    public task(data date, String name, int priority){
        this.date = date;
        this.name = name;
        this.priority = priority;
    }

    /** Functions-helpers **/

    public String getName(){
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public data getDate() {
        return date;
    }
}

/**
 * Class representing TODO-list structure.
 * You can add tasks - you can solve them.
 */
class todoList{
    HashMap<data, MergeableHeap<Integer, String>> todo = new HashMap<>();

    /** Constructors **/

    /**
     * Creates an empty TODO-list.
     */
    public todoList(){
        todo = new HashMap<>();
    }

    /**
     * Given a list of tasks with priorities, create a new TODO-list.
     *
     * @param arr List of tasks.
     */
    public todoList(List<task> arr){
        for (int i = 0; i < arr.size(); i++) {
            add(arr.get(i));
        }
    }

    /**
     * Add a new prioritized task to an existing TODO-list
     *
     * @param newTask New task to add.
     */
    public void add(task newTask){
        if(todo.containsKey(newTask.getDate())){
            todo.get(newTask.getDate()).insert(newTask.getPriority(), newTask.getName());
        }else{
            MergeableHeap<Integer, String> dayHeap = new BinomialHeap<>();
            node<Integer, String> dayRoot = new node<>(newTask.getPriority(), newTask.getName());
            dayHeap.setRoot(dayRoot);
            todo.put(newTask.getDate(), dayHeap);
        }
    }

    /**
     * Solve the task with the highest priority from the TODO-list.
     * Remove the task from the list with the highest priority.
     */
    public void solve(data date){
        if(todo.containsKey(date)){
            todo.get(date).removeMax();
        }
    }

    /**
     * Merge two TODO-lists.
     *
     * @param other Other TODO-list to merge.
     */
    public void merge(todoList other){
        for (int i = 0; i < other.todo.size(); i++) {
            return;
        }
    }
}

/**
 * Binomial heap - one of the realizations of Mergeable Heap.
 *
 * @param <K> Key.
 * @param <V> Value.
 */
class BinomialHeap<K extends Comparable,V> implements MergeableHeap<K,V> {

    /** Variables **/

    private int size;
    node root;

    /** Constructor **/

    public BinomialHeap(){
        size = 0;
        root = null;
    }

    /** Overridden Methods **/

    @Override
    public void insert(K k, V v){
        BinomialHeap<Integer, String> h = new BinomialHeap<>();
        node newNode = new node(k,v);
        h.setRoot(newNode);
        this.merge(h);
    }

    @Override
    public Pair max() {
        Pair res = new Pair<>(null, null);
        node cur = root;
        if (cur == null) {
            return res;
        }

        K max = (K) root.key;
        while (cur != null) {
            if (cur.key.compareTo(max) >= 0) {
                max = (K) cur.key;
                res.k = cur.key;
                res.v = cur.value;
            }
            cur = cur.sibling;
        }
        return res;

    }

    public void removeMax(){
        //checking whether the heap is empty or not
        //if it is empty, just exit from the method.
        if(this.root == null){
            return;
        }

        LinkedList<node> list = new LinkedList<>();
        Pair pair = max();

        node prev = null;
        node cur = this.root;

        while((cur.key).compareTo((pair.k)) != 0){
            prev = cur;
            cur = cur.sibling;
        }

        if(cur == root){
            root = cur.sibling;
            return;
        }else{
            prev.sibling = cur.sibling;
        }

        cur = cur.child;

        if (cur != null) {
            while (cur != null) {
                list.addFirst(cur);
                cur = cur.sibling;
            }
            if (list.size() != 0) {
                MergeableHeap<K,V> h = new BinomialHeap<>();

                h.setRoot(list.getFirst());
                list.removeFirst();
                cur = h.getRoot();

                while(!list.isEmpty()){
                    cur.sibling = list.getFirst();
                    list.removeFirst();
                    cur = cur.sibling;
                }

                cur.sibling = null;
                merge(h);
            }
        }
    }


    @Override
    public void merge(MergeableHeap h) {
        //concatenating 2 heaps without merging entire trees
        concatenate(h);

        //if heap is empty, just return
        if(this.root == null){
            return;
        }

        node prev = null;
        node x = this.getRoot();
        node next = x.sibling;

        while (next != null){
            //different degrees => just moving further
            if(x.degree != next.degree){
                prev = x;
                x = next;
            }
            //x is the first of 3 roots of equal degree => just moving further
            else if ((x.degree == next.degree) && ((next.sibling != null) && (x.degree == next.sibling.degree))){
                prev = x;
                x = next;
            }
            else {
                //x is the first of 2 roots of equal degrees
                //we compare their roots' keys in order to have correct binary max-heap
                if((x.key).compareTo((next.key)) > 0){
                    x.sibling = next.sibling;
                    //remove x from the root list
                    binomial_link_nodes(next,x);
                }
                else{
                    if (prev == null) {
                        this.root = next;
                    }
                    else {
                        prev.sibling = next;
                    }
                    //making x the leftmost child of next
                    binomial_link_nodes(x, next);
                    //updating x for the next iteration
                    x = next;
                }
            }
            //shift our next
            next = x.sibling;
        }
    }

    @Override
    public node getRoot() {
        return root;
    }

    @Override
    public void setRoot(node n) {
        root = n;
    }

    /** Functions-helpers **/

    /**
     * Links the B_k-1 tree rooted at node a to the B_k-1 tree rooted at node b.
     * Making a the leftmost child of b.
     * b becomes the root of a B_k tree.
     *
     * @param a Root of tree, which will become a child of b.
     * @param b Root of tree, which will remain the same.
     */
    private void binomial_link_nodes(node a, node b){
        a.p = b;
        a.sibling = b.child;
        b.child = a;
        b.degree++;
    }

    /**
     * Merges the heap with another heap into a single linked
     * list that is sorted by degree into not decreasing order.
     *
     * @param h Another Mergeable heap.
     */
    private void concatenate(MergeableHeap h){
        //check whether one of the heaps is empty, so, there is nothing to concatenate actually
        if(this.getRoot() == null){
            this.root = h.getRoot();
            return;
        }else if(h.getRoot() == null){
            return;
        }

        //ourTree will save all our nodes from 2 heaps, newRoot will contain our new root
        node ourTree, newRoot;

        //pointer will be going through root nodes of current heap
        node pointer = this.root;
        //temp will be going through root nodes of passed heap
        node temp = h.getRoot();

        //identifying the root with the smallest degree
        if(pointer.degree <= temp.degree){
            newRoot = pointer;
            ourTree = pointer;
            pointer = pointer.sibling;
        }else{
            newRoot = temp;
            ourTree = temp;
            temp = temp.sibling;
        }

        //adding to the path(ourTree) all nodes
        while(pointer != null && temp != null){
            if (pointer.degree <= temp.degree){
                ourTree.sibling = pointer;
                pointer = pointer.sibling;
                ourTree = ourTree.sibling;
            }else{
                ourTree.sibling = temp;
                temp = temp.sibling;
                ourTree = ourTree.sibling;
            }
        }

        while(temp != null){
            ourTree.sibling = temp;
            temp = temp.sibling;
        }

        while(pointer != null){
            ourTree.sibling = pointer;
            pointer = pointer.sibling;
        }

        this.root = newRoot;
        h.setRoot(null);
    }


    /**
     * Finds the node with given key.
     * Runs in O(n)!!!
     *
     * @param key Key.
     * @return Node with that key.
     */
    public node find(K key) {
        LinkedList<node> nodes = new LinkedList<>();
        if(root == null){
            return null;
        }
        nodes.addLast(root);

        while (!nodes.isEmpty()) {
            node cur = nodes.getFirst();
            nodes.removeFirst();
            if (cur.key == key) {
                return cur;
            }
            if (cur.sibling != null) {
                nodes.add(cur.sibling);
            }
            if (cur.child != null) {
                nodes.add(cur.child);
            }
        }
        return null;
    }

    @Override
    public String toString(){
        String res = "";
        node current = this.getRoot();
        while(current != null){
            res +=current + "->";
            current = current.sibling;
        }
        return res;
    }

}

class Pair<K extends Comparable<K>, V> {
    K k;
    V v;

    public Pair(K k, V v) {
        this.k = k;
        this.v = v;
    }
}

/**
 * A representation of a node (element) in a binomial heap.
 *
 * @param <K> comparable key.
 * @param <V> value of the key.
 */
class node<K extends Comparable, V>{
    K key;
    V value;
    int degree;
    node p, child, sibling;

    public node(K key, V value){
        this.key = key;
        this.value = value;
        degree = 0;
        p = null;
        child = null;
        sibling = null;
    }


    @Override
    public String toString(){
        return "[key: " + key.toString() + " value: " + value.toString() + " degree: " + degree + " child: " + child + "]" ;
    }
}

/**
 * Mergeable Heap ADT according to the task
 *
 * @param <K> Key.
 * @param <V> Value.
 */
interface MergeableHeap<K extends Comparable, V>{

    /**
     * Inserts the node with key k and value v into the heap.
     * @param k Key of node.
     * @param v Value of node.
     */
    void insert(K k, V v);

    /**
     * Get the key-value pair with maximum key.
     *
     * @return Key-value pair with maximum key.
     */
    Pair max();

    /**
     * Remove maximum key-value pair from the heap;
     */
    void removeMax();

    /**
     * This function merge another heap h with the present one, incorporating
     * all entries into the current one while emptying h.
     *
     * @param h Mergeable heap to merge.
     */
    void merge(MergeableHeap h);

    node getRoot();
    void setRoot(node n);
}