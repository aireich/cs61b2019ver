import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**@source https://github.com/PigZhuJ/cs61b_sp19/blob/963c476965fa7bfd4b829c31cbbaa9c4244f75e6/lab8/MyHashMap.java**/
public class MyHashMap<K, V> implements Map61B<K, V>{

    private int itemCnt = 0;
    private int size = 16;
    private double loadFactor = 0.75;
    private Entry<K, V>[] bins;


    public MyHashMap() {
        bins = new Entry[size];
    }

    public MyHashMap(int initialSize) {
        this.size = initialSize;
        bins = new Entry[size];
    }

    public MyHashMap(int initialSize, double loadFactor) {
        if (initialSize < 1 || loadFactor < 0) {
            throw new IllegalArgumentException();
        }
        this.size = initialSize;
        this.loadFactor = loadFactor;
        bins = new Entry[size];
    }

    private class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Entry get(K key) {
            if (key != null && key.equals(this.key)) {
                return this;
            }
            if (next == null) {
                return null;
            }
            return next.get(key);
        }
    }

    @Override
   public void clear() {
        this.bins = new Entry[bins.length];
        this.itemCnt = 0;
   }

   @Override
    public boolean containsKey(K key) {
        return get(key) != null;
   }

   @Override
    public V get(K key) {
        if (find(key, bins[hash(key, size)]) == null) {
            return null;
        } else {
            return find(key, bins[hash(key, size)]).value;
        }
   }

   @Override
    public int size() {
        return itemCnt;
   }

   private void grow() {
       MyHashMap<K,V> newMap = new MyHashMap<K, V> (bins.length*2, loadFactor);
       for (int i = 0; i < bins.length; i++) {
           Entry<K, V> e = bins[i];
           while (e != null) {
               Entry<K, V> oldNext = e.next;
               int newHashCode = hash(e.key, newMap.size);
               e.next = newMap.bins[newHashCode];
               newMap.bins[newHashCode] = e;
               e = oldNext;
           }
       }
       this.bins = newMap.bins;
       this.size = newMap.size;
       this.loadFactor = newMap.loadFactor;
   }

   /**Return the key-value pair that stored in the entry**/
   private Entry<K, V> find(K key, Entry<K, V> bin) {
       for (Entry<K, V> e = bin; e != null; e = e.next)
           if (key == null && e.key == null || key.equals (e.key))
               return e;
       return null;
   }

   private int hash(K key, int size) {
       int result = 0;
       if (key != null) {
           result = (0x7fffffff & key.hashCode()) % size;
       }
       return result;

   }

   @Override
    public void put(K key, V value) {
        int h = hash(key, size);
        Entry<K, V> e = find (key, bins[h]);
       if (e == null) {
           bins[h] =  new Entry<K,V> (key, value, bins[h]);
           itemCnt += 1;
           if (itemCnt > bins.length * loadFactor) {
               grow();
           };
       } else
           bins[h] =  new Entry<K,V> (key, value, bins[h]);
   }

   @Override
    public Set<K> keySet() {
       Set<K> s = new HashSet<K>();
       for (int i = 0; i< bins.length; i++) {
           if (bins[i] != null) {
               Entry<K, V> current = bins[i];
               while(current != null) {
                   s.add(current.key);
                   current = current.next;
               }
           }
       }
        return s;
   }

    @Override
    public MyHashMapIterator<K> iterator() {
        return new MyHashMapIterator<K>();
    }

    private class MyHashMapIterator<K> implements Iterator<K>{
        @Override
        public K next() {
            return null;
        }

        @Override
        public boolean hasNext() {
            return false;
        }
    }


    @Override
    public V remove(K key) {throw new UnsupportedOperationException();}

    @Override
    public V remove(K key, V value) {throw new UnsupportedOperationException();}

}
