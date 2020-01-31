package com.osk.hashing;

import java.util.LinkedList;
import java.util.Random;

public class HashTable {

    private LinkedList<Integer>[] chains;
    private HashTable[] tables;

    private final long p;
    private int a, b, size;

    private static final Random RANDOM = new Random();

    private static final int SIZE_RATE = 1;

    public HashTable(Integer[] data) {
        if (data == null || data.length == 0) throw new IllegalArgumentException("Data could not be NULL or EMPTY!!!");

        this.size = data.length * SIZE_RATE;

        p = getNextPrime(getMaxVal(data) * 2);

        chains = new LinkedList[size];
        makeFirstLevelHash(data);
        makeSecondLevelHash();
        clearTablesChain();
    }

    public boolean contains(Integer val) {
        int flIdx = getHashedValue(val);
        HashTable secondLevelTable = tables[flIdx];
        if (secondLevelTable != null && secondLevelTable.size > 0) {
            int slIdx = secondLevelTable.getHashedValue(val);
            LinkedList<Integer> slChain = secondLevelTable.chains[slIdx];
            if (slChain != null && !slChain.isEmpty()) {
                if (slChain.size() > 1) {
                    throw new RuntimeException("HashTable has collisions on second level!!!");
                } else {
                    return slChain.contains(val);
                }
            }
        }
        return false;
    }

    private HashTable(LinkedList<Integer> chain, long p) {
        size = (int) Math.pow(chain.size(), 2);
        this.p = p;
        chains = new LinkedList[size];
        makeHash(chain);
    }

    private void rehashFirstLevel(Integer[] data) {
        this.a = RANDOM.nextInt();
        this.b = RANDOM.nextInt();
        for (LinkedList chain : chains) {
            if (chain != null) chain.clear();
        }
        storeDataInChains(data);
    }

    private void storeDataInChains(Integer[] data) {
        for (Integer aData : data) {
            int val_idx = getHashedValue(aData);
            if (chains[val_idx] == null) {
                chains[val_idx] = new LinkedList<>();
            }
            chains[val_idx].add(aData);
        }
    }

    private void makeFirstLevelHash(Integer[] data) {
        rehashFirstLevel(data);
        while (getChainSquareSum() > size * 3) {
            rehashFirstLevel(data);
        }
    }

    private void makeSecondLevelHash() {
        tables = new HashTable[size];
        for (int i = 0; i < size; i++) {
            if (chains[i] != null && !chains[i].isEmpty()) {
                tables[i] = new HashTable(chains[i], p);
            }
        }
    }

    private void makeHash(LinkedList<Integer> chain) {
        int maxChainSize = Integer.MAX_VALUE;
        while (maxChainSize > 1) {
            clearTablesChain();
            // 1. make random a and b params
            a = RANDOM.nextInt();
            b = RANDOM.nextInt();
            // 2. get elem from chain
            for (Integer value : chain) {
                // 3. for each value from list
                //      3.1. hash value and put into table
                int idx = hash(value, a, b, (int) p, size);
                add(idx, value);
            }
            // 4. get maximum chain size
            maxChainSize = getMaxChainSize();
        }
        // 5. if maximum chain size > 1 go to 1.
    }

    private void add(int idx, Integer value) {
        if (chains[idx] == null) chains[idx] = new LinkedList<>();
        chains[idx].add(value);
    }

    private void clearTablesChain() {
        for (LinkedList chain : chains) {
            if (chain != null) chain.clear();
        }
    }

    private long getMaxVal(Integer[] data) {
        long max = Integer.MIN_VALUE;
        for (Integer aData : data) {
            if (aData > max) max = aData;
        }
        return max;
    }

    private long getChainSquareSum() {
        long res = 0;
        for (LinkedList chain : chains) {
            if (chain != null) res += Math.pow(chain.size(), 2);
        }
        return res;
    }

    private int getMaxChainSize() {
        int max = 0;
        for (LinkedList chain : chains) {
            if (chain != null && chain.size() >= max) max = chain.size();
        }
        return max;
    }

    private int hash(Integer value, int a, int b, long p, int tableSize) {
        long res = (long) ((value * a) + b);
        res %= p;
        res %= tableSize;
        if (res < 0) res *= -1;
        return (int) (res);
    }

    private int getHashedValue(Integer x) {
        return hash(x, a, b, p, size);
    }

    private long getNextPrime(long num) {
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }

    private boolean isPrime(long n) {
        for (long i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (HashTable table : tables) {
            if (table != null && table.chains != null) {
                sb.append("{");
                for (LinkedList chain : table.chains) {
                    if (chain != null) sb.append(chain.toString());
                    else sb.append("[]");
                }
                sb.append("}")
                        .append(" a = ").append(table.a)
                        .append("; b = ").append(table.b)
                        .append("\n");
            }
        }
        return sb.toString();
    }
}
