import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {

    private int collisions;
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.80d;
    public LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;

    public HashTable() {
        this.slots = new LinkedList[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive integer");
        }
        this.slots = new LinkedList[capacity];
        this.capacity = capacity;
    }

    public void add(K key, V value) {
        growIfNeeded();
        int slotNumber = findSlotNumber(key);

        if (slots[slotNumber] == null) {
            slots[slotNumber] = new LinkedList<>();
        } else {
            collisions++; // Увеличиваем счетчик коллизий
        }

        slots[slotNumber].add(new KeyValue<>(key, value));
        count++;
    }

    public int getCollisions() {
        return collisions;
    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    private void growIfNeeded() {
        if ((double) (this.size() + 1) / this.capacity > LOAD_FACTOR) {
            grow();
        }
    }

    private void grow() {
        int newCapacity = this.capacity * 2;
        LinkedList<KeyValue<K, V>>[] newSlots = new LinkedList[newCapacity];

        for (LinkedList<KeyValue<K, V>> slot : slots) {
            if (slot != null) {
                for (KeyValue<K, V> keyValue : slot) {
                    int newSlotNumber = Math.abs(keyValue.getKey().hashCode()) % newCapacity;
                    if (newSlots[newSlotNumber] == null) {
                        newSlots[newSlotNumber] = new LinkedList<>();
                    }
                    newSlots[newSlotNumber].add(keyValue);
                }
            }
        }

        this.slots = newSlots;
        this.capacity = newCapacity;
    }

    public int size() {
        return this.count;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean addOrReplace(K key, V value) {
        growIfNeeded();
        int slotNumber = findSlotNumber(key);

        if (slots[slotNumber] == null) {
            slots[slotNumber] = new LinkedList<>();
        }

        for (KeyValue<K, V> keyValue : slots[slotNumber]) {
            if (keyValue.getKey().equals(key)) {
                keyValue.setValue(value);
                return true;
            }
        }

        slots[slotNumber].add(new KeyValue<>(key, value));
        count++;
        return false;
    }

    public V get(K key) {
        int slotNumber = findSlotNumber(key);

        if (slots[slotNumber] != null) {
            for (KeyValue<K, V> keyValue : slots[slotNumber]) {
                if (keyValue.getKey().equals(key)) {
                    return keyValue.getValue();
                }
            }
        }

        return null;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<KeyValue<K, V>> {
        private int currentSlot = 0;
        private Iterator<KeyValue<K, V>> currentIterator = getNextIterator();

        @Override
        public boolean hasNext() {
            while ((currentIterator == null || !currentIterator.hasNext()) && currentSlot < capacity - 1) {
                currentSlot++;
                currentIterator = getNextIterator();
            }
            return currentIterator != null && currentIterator.hasNext();
        }

        @Override
        public KeyValue<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the hash table");
            }
            return currentIterator.next();
        }

        private Iterator<KeyValue<K, V>> getNextIterator() {
            if (currentSlot < capacity && slots[currentSlot] != null) {
                return slots[currentSlot].iterator();
            }
            return null;
        }
    }

    public void remove(K key) {
        int slotNumber = findSlotNumber(key);

        if (slots[slotNumber] != null) {
            Iterator<KeyValue<K, V>> iterator = slots[slotNumber].iterator();
            while (iterator.hasNext()) {
                KeyValue<K, V> entry = iterator.next();
                if (entry.getKey().equals(key)) {
                    iterator.remove();
                    count--;
                    break;
                }
            }
        }
    }

}
