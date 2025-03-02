import java.util.Scanner;

public class FTreeClient {
    public static void main(String[] args) {
        
        FTree<Integer, String> tree = new FTree<>(3);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello World!");
        printMenu();

        while (true) {
            System.out.print("\nEnter command: ");
            String command = scanner.next().toLowerCase();
            
            if (command.equals("exit") || command.equals("quit")) {
                System.out.println("Goodbye cruel world :-(");
                break;
            }
            
            switch (command) {
                case "insert":
                    System.out.println("Enter key-value pairs to insert (type 'stop' as key to finish the insert):");
                    while (true) {
                        System.out.print("Enter key (or type 'stop'): ");
                        String keyInput = scanner.next();
                        if (keyInput.equalsIgnoreCase("stop")) {
                            break;
                        }
                        try {
                            int key = Integer.parseInt(keyInput);
                            System.out.print("Enter value (string): ");
                            String value = scanner.next();
                            tree.put(key, value);
                            System.out.println("---Inserted: (" + key + ", " + value + ")---");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid key input. Please enter an integer or 'stop'.");
                        }
                    }
                    break;
                case "get":
                    System.out.print("Enter key (integer): ");
                    int key = scanner.nextInt();
                    String result = tree.get(key);
                    if (result != null)
                        System.out.println("Value for key \"" + key + "\" is: \"" + result + "\"");
                    else
                        System.out.println("Key \"" + key + "\" not found.");
                    break;
                case "contains":
                    System.out.print("Enter key (integer): ");
                    key = scanner.nextInt();
                    boolean contains = tree.contains(key);
                    System.out.println(contains ? "YES" : "NO");
                    break;
                case "size":
                    System.out.println("Total number of keys in tree: " + tree.size());
                    break;
                case "height":
                    System.out.println("Height of tree: " + tree.height());
                    break;
                case "min":
                    Integer minKey = tree.min();
                    if (minKey != null)
                        System.out.println("Minimum key in tree: " + minKey);
                    else
                        System.out.println("Tree is empty.");
                    break;
                case "max":
                    Integer maxKey = tree.max();
                    if (maxKey != null)
                        System.out.println("Maximum key in tree: " + maxKey);
                    else
                        System.out.println("Tree is empty.");
                    break;
                case "keys":
                    System.out.println("Keys in tree:");
                    for (Integer k : tree.keys()) {
                        System.out.print(k + " ");
                    }
                    System.out.println();
                    break;
                case "values":
                    System.out.println("Values in tree:");
                    for (String v : tree.values()) {
                        System.out.print(v + " ");
                    }
                    System.out.println();
                    break;
                case "keysbetween":
                case "keys_between":
                    System.out.print("Enter minimum key (integer): ");
                    int minBound = scanner.nextInt();
                    System.out.print("Enter maximum key (integer): ");
                    int maxBound = scanner.nextInt();
                    System.out.println("Keys between " + minBound + " and " + maxBound + ":");
                    for (Integer k : tree.keys(minBound, maxBound)) {
                        System.out.print(k + " ");
                    }
                    System.out.println();
                    break;
                case "valuesbetween":
                case "values_between":
                    System.out.print("Enter minimum key (integer): ");
                    minBound = scanner.nextInt();
                    System.out.print("Enter maximum key (integer): ");
                    maxBound = scanner.nextInt();
                    System.out.println("Values between keys " + minBound + " and " + maxBound + ":");
                    for (String v : tree.values(minBound, maxBound)) {
                        System.out.print(v + " ");
                    }
                    System.out.println();
                    break;
                case "rank":
                    System.out.print("Enter key (integer): ");
                    key = scanner.nextInt();
                    int rank = tree.rank(key);
                    System.out.println("Number of keys less than " + key + " is: " + rank);
                    break;
                case "select":
                    System.out.print("Enter index (integer): ");
                    int index = scanner.nextInt();
                    Integer selectedKey = tree.select(index);
                    if (selectedKey != null)
                        System.out.println("Key at rank " + index + " is: " + selectedKey);
                    else
                        System.out.println("No key found at index " + index);
                    break;
                case "floor":
                    System.out.print("Enter key (integer): ");
                    key = scanner.nextInt();
                    Integer floorKey = tree.floor(key);
                    if (floorKey != null)
                        System.out.println("Floor of " + key + " is: " + floorKey);
                    else
                        System.out.println("No floor found for key " + key);
                    break;
                case "ceiling":
                    System.out.print("Enter key (integer): ");
                    key = scanner.nextInt();
                    Integer ceilingKey = tree.ceiling(key);
                    if (ceilingKey != null)
                        System.out.println("Ceiling of " + key + " is: " + ceilingKey);
                    else
                        System.out.println("No ceiling found for key " + key);
                    break;
                case "print":
                    System.out.println("Tree keys by level:");
                    tree.printlnKeys();
                    break;
                case "help":
                    printMenu();
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' to see available commands.");
                    break;
            }
        }
        scanner.close();
    }
    
    private static void printMenu() {
        System.out.println("\nAvailable commands:");
        System.out.println("insert        - Insert new key-value pairs");
        System.out.println("get           - Get the value for a given key");
        System.out.println("contains      - Check if a key exists in the tree");
        System.out.println("size          - Get the total number of keys in the tree");
        System.out.println("height        - Get the height of the tree");
        System.out.println("min           - Get the minimum key in the tree");
        System.out.println("max           - Get the maximum key in the tree");
        System.out.println("keys          - List all keys in the tree");
        System.out.println("values        - List all values in the tree");
        System.out.println("keysBetween   - List keys between two bounds (inclusive)");
        System.out.println("valuesBetween - List values between two key bounds (inclusive)");
        System.out.println("rank          - Get the rank of a key");
        System.out.println("select        - Select a key by its rank (index)");
        System.out.println("floor         - Get the floor key for a given key");
        System.out.println("ceiling       - Get the ceiling key for a given key");
        System.out.println("print         - Print the tree keys by level");
        System.out.println("help          - Show this menu");
        System.out.println("exit          - Exit the client");
    }
}
