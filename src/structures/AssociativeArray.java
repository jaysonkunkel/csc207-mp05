package structures;

import static java.lang.reflect.Array.newInstance;
import java.util.List;
import java.util.ArrayList;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @author Jayson Kunkel
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> arr = new AssociativeArray<K, V>();
    arr.size = this.size;
    arr.pairs = this.pairs;
    return arr;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {

    // if the array is empty
    if(this.size == 0){
      return "{}";
    } // if

    int end = this.pairs.length;
    String contents = "";
    for(int i = 0; i < end; i++){
      if(this.pairs[i] != null){
        contents += this.pairs[i].key + ": " + this.pairs[i].value + ", ";
      }
    } // for

    // call to substring removes the comma after the final KV pair
    return "{ " + contents.substring(0, contents.length()-2) + " }";
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   */
  public void set(K key, V value) {
    // if the array is full, expand it, set desired KV pair at first open index
    if(this.isFull() && !this.hasKey(key)){
      this.expand();
      // this.pairs[++this.size].key = key;
      // this.pairs[this.size].value = value;
      this.size++;
      this.pairs[size] = new KVPair<K,V> (key, value);
    } // if
    else{
      // search for the first null index or existing key, and set given KV pair
      int firstNull = 0;
      boolean foundNull = false;
      boolean foundDup = false;

      for(int i = 0; i < this.pairs.length; i++){
        // check for null index and keep track of it
        if(this.pairs[i] == null && !foundNull){
          firstNull = i;
          foundNull = true;
        } // if
        // if given key already exists, set that index to given KV pair
        if(this.pairs[i] != null && this.pairs[i].key.equals(key) && !foundDup){
          foundDup = true;
          this.pairs[i].key = key;
          this.pairs[i].value = value;
          size++;  
        } // if
      } // for

      //if an existing key wasnt found, set given KV pair at first null index
      if(!foundDup){
        this.pairs[firstNull] = new KVPair<K,V>(key, value);
        this.size++;
      } // if
    } // else
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException
   *                              when the key does not appear in the associative
   *                              array.
   */
  public V get(K key) throws KeyNotFoundException {
    // check each pair for matching key
    for (KVPair<K,V> kvPair : pairs) {
      if(kvPair != null && kvPair.key.equals(key)){
        return kvPair.value;
      } // if
    } // for
    throw new KeyNotFoundException("Given key does not appear in the associative array.");
  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */
  public boolean hasKey(K key) {
    // check each pair for matching key
    for (KVPair<K,V> kvPair : pairs) {
      if(kvPair != null && kvPair.key.equals(key)){
        return true;
      } // if
    } // for
    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   */
  public void remove(K key){

    for(int i = 0; i < this.pairs.length; i++){
      // if given key exists, set that index to null
      if(this.pairs[i] != null && this.pairs[i].key.equals(key)){
        this.pairs[i] = null;
        this.size--;
      } // if
    } // for

    // if(this.hasKey(key)){
    //   try{
    //     int location = this.find(key);
    //     this.pairs[location] = null;
    //     this.size--;
    //   } catch (Exception e) {
    //     System.err.println(new KeyNotFoundException());
    //   }
    // }

  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  /**
   * Determine if the array is full.
   */
  public boolean isFull(){
    return this.size == this.pairs.length;
  } // isFull()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    // search each key for desired pair
    for(int i = 0; i < this.pairs.length; i++){
      if(this.pairs[i] != null && this.pairs[i].key.equals(key)){
        // return index of key, if it exists
        return i;
      }
    }
    // otherwise throw an exception
    throw new KeyNotFoundException("Given key does not appear in the associative array.");
  } // find(K)

  /**
   * Returns an array of all the keys in this associative array. 
   */
  @SuppressWarnings({"unchecked"})
  public K[] getKeys(){
    // if there are no KVPairs, return null
    if(this.pairs.length == 0){
      return null;
    } // if

    // creating a generic array the same way as in the constructor
    K[] keys = (K[]) newInstance((this.pairs[0].key).getClass(),
        this.pairs.length);

    // go through pairs and add each key to the array
    for(int i = 0; i < this.pairs.length; i++){
      if(this.pairs[i].key != null){
        keys[i] = this.pairs[i].key;
      } // if
    } // for

    // return an array of all the keys
    return keys;
  } // getKeys()

} // class AssociativeArray
