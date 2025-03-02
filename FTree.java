import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * FTree
 * @author Daniel Pantyukhov && Valentim Khakhitva
 * @version 1.0 [public]
 */
public class FTree<Key extends Comparable<Key>, Value>
{
    private int g;
    private Node root;
    private int TWOxG;
    private int TWOxG_1;
    private int g_1;

    /**
     * Constructor for the FTree
     * @param degree the degree of the FTree (minimum 2) which determines the number of children each node can have (g children and g-1 keys)
     * These varyables like TWOxG, TWOxG_1 and g_1 are used to avoid recalculating the same values multiple times
     */
    public FTree(int degree)
    {
        assert(degree >= 2);
        this.g = degree;
        root = new Node();
        TWOxG = 2 * g;
        TWOxG_1 = 2 * g - 1;
        g_1 = g - 1;
    }

    /**
     * Node class for the FTree
     * Each node has a list of keys, values, and children
     */
    private class Node {
        ArrayList<Key> keys;
        ArrayList<Value> values;
        ArrayList<Node> children;
        int size;
        int height;
    
        /**
         * Constructor for the Node
         */
        Node(){
            keys = new ArrayList<Key>(TWOxG_1);
            values = new ArrayList<Value>(TWOxG_1);
            children = new ArrayList<Node>(TWOxG);
            height = 0;
        }
    
        boolean isLeaf(){
            return children.isEmpty();
        }

        boolean isFull(){
            return keys.size() == TWOxG_1;
        }

        boolean isEmpty(){
            return keys.size() == 0;
        }
    }

    /**
     * @return the number of keys in the FTree
     */
    public int size() 
	{
		return root.size;
    }

        /**
         * 
         * @param n Node to be updated
         */
        private void updateSize(Node n){
            if (n == null) return;
        
            int size = n.keys.size();
            for (Node child : n.children){
                size += child.size;
            }
            n.size = size;
        }
    /**
     * @param min key of the first key
     * @param max key of the last key
     * @return the number of keys in the FTree between min and max (inclusive)
     */
    public int size(Key min, Key max){
        if (root == null || root.isEmpty() || min.compareTo(max) > 0) return 0;
        
        int size = rank(max) - rank(min);
        if (contains(max)) size++;
        
        return size;
    }

    /**
     * The height of a tree is the number of edges on the longest path between the root and a leaf
     * @return the height of the FTree
     */
    public int height()
    {
		return root.height;
    }

        /**
         * @param node Node to calculate the height from
         * @return the height of the node strarting from a given node
         */
        @SuppressWarnings("unused")
        private int height(Node node){
            if (node.isLeaf()) return 0;
            return 1 + height(node.children.get(0));
        }

    /**
     * Checks if the key is present in the FTree
     * @param k key to be checked
     * @return true if the key is in the FTree, false otherwise
     */
    public boolean contains(Key k) 
	{
        return get(k) != null;
    }

    /**
     * Gets the value of the key
     * @param k key to get the value of
     * @return the value of the key
     */
    public Value get(Key k) 
	{
        return get(root, k);
    }

        /**
         * Gets the value of the key from a given node n using binary search (specially usefull for big nodes)
         * @param n Node to get the value from
         * @param k key to get the value of
         * @return the value of the key if it exists, or the position of a child where the key could be and null if the key is not in the node and there are no more children
         */
        private Value get(Node n, Key k){
            if (n.isEmpty()) return null;
        
            int i = binarySearch(n, k);
        
            if (i < n.keys.size() && k.compareTo(n.keys.get(i)) == 0){ // Checks if the key is in this node
                return n.values.get(i);
            } else if (n.isLeaf()){
                return null; 
            } else { // If the key is not in this node, we go to the child where it could be
                return get(n.children.get(i), k);
            }
        }
    
    /**
     * Inserts a key-value pair into the FTree
     * @param k key to be inserted
     * @param v value to be inserted
     */
    public void put(Key k, Value v)
    {
        if (root.isFull())
        {
            Node newRoot = new Node();
            newRoot.children.add(root);
            split(newRoot, 0);
            root = newRoot;
        }
        put(root, k, v);
    }

        /**
         * Splits a node in two
         * @param parent Node that is the parent of the node to be split
         * @param index index of the node to be split
         */
        private void split(Node parent, int index)
        {
            Node splitNode = parent.children.get(index); // Node to be split
            Node newNode = new Node(); // Node to be created where the second half of the splitNode will be
            int oldSize = splitNode.size;

            // Moves the middle entry of the splitNode to the parent
            parent.keys.add(index, splitNode.keys.get(g_1));
            parent.values.add(index, splitNode.values.get(g_1));
            parent.children.add(index + 1, newNode);

            for (int i = g; i < TWOxG; i++) {
                if (i < TWOxG_1) {
                    newNode.keys.add(splitNode.keys.remove(g));
                    newNode.values.add(splitNode.values.remove(g));
                }
                if (!splitNode.isLeaf()) {
                    newNode.children.add(splitNode.children.remove(g));
                }
            }
            splitNode.keys.remove(g_1);
            splitNode.values.remove(g_1);

            // Updates the sizes of the nodes
            if(splitNode.isLeaf()){
                splitNode.size = g_1;
                newNode.size = g_1;
            }
            else{
                updateSize(splitNode); 
                newNode.size = oldSize - splitNode.size - 1;
            }
            parent.size++;
        }

        /**
         * Inserts a key-value pair into the FTree from a given node
         * @param node Node to insert the key-value pair
         * @param key key to be inserted
         * @param value value to be inserted
         */
        private void put(Node node, Key key, Value value){
            int i = binarySearch(node, key);

            // Checks if the key is already in the node
            if (i < node.keys.size() && key.compareTo(node.keys.get(i)) == 0){
                node.values.set(i, value);
                return;
            }
            if (node.isLeaf()){
                node.keys.add(i, key);
                node.values.add(i, value);
                node.size++;
            } else {
                if (node.children.get(i).isFull()){
                    split(node, i);
                    // Checks if the key is in the new node after the split
                    int cmp = key.compareTo(node.keys.get(i));
                    if (cmp == 0){
                        node.values.set(i, value);
                        return;
                    }
                    else if (cmp > 0) i++;
                }
                put(node.children.get(i), key, value);
                updateSize(node);
            }
            node.height = node.isLeaf() ? 0 : node.children.get(0).height + 1;
        }

    /**
     * Iterates through all the keys in the FTree in order
     * @return an iterable of all the keys in the FTree
     */
    public Iterable<Key> keys(){
        Queue<Key> q = new LinkedList<>();
        orderKeys(root, q);
        return q;
    }

        /**
         * Iterates through all the keys in the FTree in order from a given node
         * @param n Node to start the iteration from
         * @param q Queue to store the keys
         */
        private void orderKeys(Node n, Queue<Key> q){
            if (n.isEmpty()) return;
            for (int i = 0; i < n.keys.size(); i++){
                if (!n.isLeaf()){
                    orderKeys(n.children.get(i), q);
                }
                q.add(n.keys.get(i));
            }
            // Visits the rightmost last child
            if (!n.isLeaf()){
                orderKeys(n.children.get(n.children.size() - 1), q);
            }
        }

    /**
     * Iterates through all the values in the FTree in order
     * @return an iterable of all the values in the FTree
     */
    public Iterable<Value> values()
    {
        Queue<Value> q = new LinkedList<>(); 
        orderValues(root, q);
        return q;
    }
        /**
         * Iterates through all the values in the FTree in order from a given node
         * @param n Node to start the iteration from
         * @param q Queue to store the values
         */
        private void orderValues(Node n, Queue<Value> q){
            if (n == null) return;
            for (int i = 0; i < n.keys.size(); i++){
                if (!n.isLeaf()){
                    orderValues(n.children.get(i), q);
                }
                q.add(n.values.get(i));
            }
            // Visits the rightmost last child
            if (!n.isLeaf()){
                orderValues(n.children.get(n.children.size() - 1), q);
            }
        }
    
    /**
     * Iterates through all the keys in the FTree in order between min and max
     * @param min key of the first key
     * @param max key of the last key
     * @return an iterable of all the keys in the FTree between min and max (inclusive)
     */
    public Iterable<Key> keys(Key min, Key max) 
    {
        Queue<Key> q = new LinkedList<>();
        keysInBetween(root, q, min, max);       
        return q;
    }

        /**
         * Iterates through all the keys in the FTree in order between min and max from a given node
         * @param n Node to start the iteration from
         * @param q Queue to store the keys
         * @param min key of the first key
         * @param max key of the last key
         */
        private void keysInBetween(Node n, Queue<Key> q, Key min, Key max){
            if (n.isEmpty()) return;
            for (int i = 0; i < n.keys.size(); i++){
                if (!n.isLeaf()){
                    keysInBetween(n.children.get(i), q, min, max);
                }
                if (n.keys.get(i).compareTo(min) >= 0 && n.keys.get(i).compareTo(max) <= 0) q.add(n.keys.get(i));
            }
            // Visits the rightmost last child
            if (!n.isLeaf()){
                keysInBetween(n.children.get(n.children.size() - 1), q, min, max);
            }
        }

    /**
     * Iterates through all the values in the FTree in order between min and max
     * @param min key of the first key
     * @param max key of the last key
     * @return an iterable of all the values in the FTree between min and max (inclusive)
     */
    public Iterable<Value> values(Key min, Key max) 
    {
        Queue<Value> q = new LinkedList<>();
        valuesInBetween(root, q, min, max);       
        return q;
    }

        /**
         * Iterates through all the values in the FTree in order between min and max from a given node
         * @param n Node to start the iteration from
         * @param q Queue to store the values
         * @param min key of the first key
         * @param max key of the last key
         */
        private void valuesInBetween(Node n, Queue<Value> q, Key min, Key max){
            if (n.isEmpty()) return;
            for (int i = 0; i < n.keys.size(); i++){
                if (!n.isLeaf()){
                    valuesInBetween(n.children.get(i), q, min, max);
                }
                if (n.keys.get(i).compareTo(min) >= 0 && n.keys.get(i).compareTo(max) <= 0) q.add(n.values.get(i));
            }
            // Visits the rightmost last child
            if (!n.isLeaf()){
                valuesInBetween(n.children.get(n.children.size() - 1), q, min, max);
            }
        }

    /**
     * @return the minimum key in the FTree
     */
    public Key min()
	{
        if (root == null || root.isEmpty()) return null;
        Node n = root;
        while (!n.isLeaf()){
            n = n.children.get(0); // Travels to the leftmost node
        }
        return n.keys.get(0);
    }

    /**
     * @return the maximum key in the FTree
     */
    public Key max()
	{
        if (root == null || root.isEmpty()) return null;
        Node n = root;
        while (!n.isLeaf()){
            n = n.children.get(n.children.size() - 1);  // Travels to the rightmost node
        }
        return n.keys.get(n.keys.size() - 1);
    }

    /**
     * Determines the ammount of keys that are less than the given key
     * @param k key to find the rank of
     * @return the rank of the key in the FTree
     */
    public int rank(Key k){
        if(root.isEmpty()) return 0;
        if (k.compareTo(max()) > 0) return size();
        return rank(root, k);
    }

        /**
         * Determines the ammount of keys that are less than the given key from a given node
         * @param node Node to start the rank from
         * @param k key to find the rank of
         * @return the rank of the key in the FTree
         */
        private int rank(Node node, Key k){
            if (node == null || node.isEmpty()) return 0;
            int i = binarySearch(node, k);
            
            int count = i;
            for (int j = 0; j < node.children.size(); j++){
                if (j < i){
                    count += node.children.get(j).size;
                }
            }
            if (!node.isLeaf() && i < node.children.size()){
                count += rank(node.children.get(i), k);
            }
            return count;
        }

    /**
     * Finds the key that is the n-th smallest key in the FTree
     * @param n index of the key to find
     * @return the n-th smallest key in the FTree
     */
    public Key select(int n){
        if(root == null || root.isEmpty() || n >= root.size) return null;
        if(n == 0) return min();
        return select(root, n);
    }

        /**
         * Finds the key that is the n-th smallest key in the FTree from a given node
         * @param node Node to start the search from
         * @param n index of the key to find
         * @return the n-th smallest key in the FTree
         */
        private Key select(Node node, int n){
            int count = 0; // Total number of keys in the moment

            if(n < node.keys.size() && node.isLeaf()) return node.keys.get(n);

            for (int i = 0; i < node.keys.size(); i++){
                int leftsize = node.children.get(i).size;
                int sum = count + leftsize;
                if (n < sum){
                    return select(node.children.get(i), n - count);
                }
                else if (n == sum){
                    return node.keys.get(i);
                }
                count += leftsize + 1; // Icludes the current key
            }
            if (!node.isLeaf()){
                return select(node.children.get(node.keys.size()), n - count);
            }

            return null;
        }

    /**
     * Finds the key that is the largest key less than or equal to the given key
     * @param k key to find the floor of
     * @return the floor of the key
     */
    public Key floor(Key k){
        return floor(root, k);
    }

        /**
         * Finds the key that is the largest key less than or equal to the given key from a given node
         * @param node Node to start the search from
         * @param k key to find the floor of
         * @return the floor of the key
         */
        private Key floor(Node node, Key k){
            if (node == null || node.isEmpty()) return null;
        
            int i = binarySearch(node, k);
            if (i < node.keys.size() && node.keys.get(i).compareTo(k) == 0){ // Found the exact key
                return node.keys.get(i);
            }
            if (i == 0 && node.isLeaf()) return null;
            if (i == 0) return floor(node.children.get(0), k);
            if (node.isLeaf()) return node.keys.get(i - 1);

            Key floorCandidate = floor(node.children.get(i), k);
            return (floorCandidate != null) ? floorCandidate : node.keys.get(i - 1);
        }
       
    /**
     * Finds the key that is the smallest key greater than or equal to the given key
     * @param k key to find the ceiling of
     * @return the ceiling of the key
     */
    public Key ceiling(Key k){
        return ceiling(root, k);
    }
        
        /**
         * Finds the key that is the smallest key greater than or equal to the given key from a given node
         * @param node Node to start the search from
         * @param k key to find the ceiling of
         * @return the ceiling of the key
         */
        private Key ceiling(Node node, Key k){
            if (node == null || node.isEmpty()) return null;
        
            int i = binarySearch(node, k);
            if (i < node.keys.size() && node.keys.get(i).compareTo(k) == 0){ // Found the exact key
                return node.keys.get(i);
            }
            if (node.isLeaf()){
                return (i < node.keys.size()) ? node.keys.get(i) : null;
            }
            if (i == node.keys.size()){
                return ceiling(node.children.get(i), k);
            }
        
            Key ceilingCandidate = ceiling(node.children.get(i), k);
            return (ceilingCandidate != null) ? ceilingCandidate : node.keys.get(i);
        }

    /**
     * Prints all the keys in the FTree
     */
    public void printlnKeys(){
        if (root == null) return;
    
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int currentHeight = root.height;
    
        while (!q.isEmpty()){
            Node n = q.poll();
            int nodeHeight = n.height;
    
            if (nodeHeight != currentHeight){
                System.out.println();
                currentHeight = nodeHeight;
            }
    
            System.out.print("[");
            for (int i = 0; i < n.keys.size(); i++){
                System.out.print(n.keys.get(i));
                if (i < n.keys.size() - 1) System.out.print(",");
            }
            System.out.print("]");
    
            if (!n.isLeaf()){
                q.addAll(n.children); 
            }
        }
        System.out.println();
    }

    /**
     * Binary search for the key in the node
     * @param n Node to search the key in
     * @param k key to search
     * @return the index of the key in the node
     */
    public int binarySearch(Node n, Key k){
        int low = 0;
        int high = n.keys.size() - 1;
    
        while (low <= high){
            int mid = (high + low) / 2;
            int cmp = k.compareTo(n.keys.get(mid));
    
            if (cmp == 0) return mid;
            else if (cmp < 0) high = mid - 1;
            else low = mid + 1;
        }
        return low;
    }

    public static void main(String[] args)
    {
    }
}