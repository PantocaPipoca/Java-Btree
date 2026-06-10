# FTree & QuickSort

Two data structures we built for a uni project.

**FTree** is a B-Tree with the usual stuff: insert, search, floor, ceiling, rank, select, range queries. Has a small CLI client so you can poke around with it interactively.

**QuickSort** has a few flavors: randomized pivot, median-of-three, and a `quickSelect` for grabbing the k-th smallest element without sorting the whole array. Falls back to insertion sort for small subarrays.

Everything uses generics so it works with any `Comparable` type.

## Requirements

Java 8 or higher. That's it.

**How to run**

```bash
javac FTree.java FTreeClient.java
java FTreeClient
```

Once it's running, type `help` to see all available commands.

**Authors:** Daniel Pantyukhov & Valen

---
