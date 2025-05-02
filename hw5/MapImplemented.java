import java.util.ArrayList;

public class MapImplemented<K, V> implements MapInterface<K, V> {
  /* Insert something into the map
   * If key does not exist in the map, then put it there.
   * If key has already been inserted into the map, then overwrite it with the new value.
   */
  private ArrayList<K> keys = new ArrayList<K>();
  private ArrayList<V> values = new ArrayList<V>();

  public void put(K key, V value) {
    // Implement this!
    if (!keys.isEmpty()) {
      for (int j = 0; j < keys.size(); j++) {
        if (keys.get(j).equals(key)) {
          values.set(j, value);
          return;
        }
      }
    }
    keys.add(key);
    values.add(value);
  }

  /* Find the value of key. Returns null if the key has never been inserted
   */
  public V get(K key) {
    // Implement this!
    for (int i = 0; i < keys.size(); i++) {
      if (key.equals(keys.get(i))) {
        return values.get(i);
      }
    }
    return null;  // Return the value or null if absent
  }
}
