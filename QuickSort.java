import java.util.Random;

/**
 * QuickSort
 * @author Daniel Pantyukhov && Valentim Khakhitva
 * @version 1.0 [public]
 */
public class QuickSort extends Sort {

    private static int cutoff = 16;
    private static final Random rand = new Random();

    /**
     * Sorts the array using the quicksort algorithm
     * @param <T> generic type for the sorted array
     * @param a array to be sorted
     */
    public static <T extends Comparable<T>> void sort(T[] a){ 
        sort(a, 0, a.length - 1);
    }

    /**
     * Sorts one part of the array specified by the low and high indexes
     * @param <T> generic type for the sorted array
     * @param a array to be sorted
     * @param low index of the first element of the subarray
     * @param high index of the last element of the subarray
     */
    private static <T extends Comparable<T>> void sort(T[] a, int low, int high){
        if (high <= low + cutoff){
            insertionSort(a, low, high);
            return;
        }
        
        int j = partition(a, low, high);
        sort(a, low, j - 1);
        sort(a, j + 1, high);
    }

    /**
     * Partitions the array into two parts
     * @param <T> generic type for the sorted array
     * @param a array to be partitioned
     * @param low index of the first element of the subarray
     * @param high index of the last element of the subarray
     * @return index of the partitioning element
     */
    private static <T extends Comparable<T>> int partition(T[] a, int low, int high){
        int randomIndex = low + rand.nextInt(high - low + 1);
        exchange(a, low, randomIndex); // Move the random element to the first position to use as a comparison element

        int i = low, j = high + 1;
        T v = a[low]; // Pivô for comparison
        
        while (true){
            while (less(a[++i], v)) if (i == high) break; // i -> Search for elements greater than the pivot
            while (less(v, a[--j])) if (j == low) break; // j -> Search for elements less than the pivot
            if (i >= j) break; // If the pointers cross, the partition is complete
            exchange(a, i, j); // Exchange the elements that are in the wrong partition
        }
 
        exchange(a, low, j);  // Swap the pivot with the element at the partition index (Its apropiate position)
        return j; 
    }


    /**
     * Sorts the array using the quicksort algorithm with median of three partitioning and insertion sort for small subarrays (cutoff)
     * @param <T> generic type for the sorted array
     * @param a array to be sorted
     */
    public static <T extends Comparable<T>> void medianSort(T[] a){
        medianSort(a, 0, a.length - 1);
    }

    /**
     * Sorts one part of the array specified by the low and high indexes using median of three partitioning
     * @param <T> generic type for the sorted array
     * @param a array to be sorted
     * @param low index of the first element of the subarray
     * @param high index of the last element of the subarray
     */
    private static <T extends Comparable<T>> void medianSort(T[] a, int low, int high){
        if (high <= low + cutoff){
            insertionSort(a, low, high);
            return;
        }

        int j = medianPartition(a, low, high);
        medianSort(a, low, j - 1);
        medianSort(a, j + 1, high);
    }

    /**
     * Partitions the array into two parts using median of three partitioning
     * @param <T> generic type for the sorted array
     * @param a array to be partitioned
     * @param low index of the first element of the subarray
     * @param high index of the last element of the subarray
     * @return index of the partitioning element
     */
    public static <T extends Comparable<T>> int medianPartition(T [] a, int low, int high){
        int mid = (low + high) / 2;
        if (less(a[mid], a[low])) exchange(a, low, mid);
        if (less(a[high], a[low])) exchange(a, low, high);
        if (less(a[high], a[mid])) exchange(a, mid, high);

        exchange(a, low + 1, mid);
        T v = a[low + 1]; 
        int i = low + 1, j = high;

        while (true){
            while (less(a[++i], v));
            while (less(v, a[--j]));
            if (i >= j) break;
            exchange(a, i, j);
        }

        exchange(a, low + 1, j);
        return j;
    }


    /**
     * Sorts the array using insertion sort algorithm (used for small subarrays)
     * @param <T> generic type for the sorted array
     * @param a array to be sorted
     * @param low index of the first element of the subarray
     * @param high index of the last element of the subarray
     */
    public static <T extends Comparable<T>> void insertionSort(T[] a, int low, int high){
        for (int i = low + 1; i <= high; i++){
            T temp = a[i]; 
            int j;
            for(j = i - 1; j >= low && less(temp,a[j]); j--){
                a[j + 1] = a[j];
            }
            a[j + 1] = temp;
        }
    }

    /**
     * Returns the k-th smallest element of the array
     * @param <T> generic type for the sorted array
     * @param a array to be sorted
     * @param n index of the k-th smallest element
     * @return the k-th smallest element of the array
     */
    public static <T extends Comparable<T>> T quickSelect(T[] a, int n){
        int low = 0, high = a.length - 1;
        while (high > low){
            int j = partition(a, low, high);
    
            if (j < n) low = j + 1;
            else if (j > n) high = j - 1;
            else return a[j];
        }
        return a[low];
    }

    public static void main(String[] args) {
    }
}