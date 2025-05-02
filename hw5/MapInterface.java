// This represents a Key-Value Map from key-type K to value-type V

public interface MapInterface<K, V>
{
  /* Insert something into the map
   * If key does not exist in the map, then put it there.
   * If key has already been inserted into the map, then overwrite it with the new value.
   */
  public void put(K key, V value);

  /* Find the value of key. Returns null if the key has never been inserted
   */
  public V get(K key);
}