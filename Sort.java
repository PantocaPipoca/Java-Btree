/**
 * QuickSort
 * @author Daniel Pantyukhov
 * @version 1.0 [public]
 */
public class Sort {

    protected static <T extends Comparable<T>> boolean less(T v, T w)
    {
        return v.compareTo(w) < 0;
    }

    protected static <T extends Comparable<T>> void exchange(T[] a, int i, int j)
    {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] a)
    {
        for (int i = 1; i < a.length; i++)
        {
            if (less(a[i],a[i-1])) return false;
        }
        return true;
    }
}
