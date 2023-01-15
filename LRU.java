package test;
import java.util.LinkedHashSet;
import java.util.Deque;
import java.util.LinkedList;


public class LRU implements CacheReplacementPolicy {

    private LinkedHashSet<String> wordsList = new LinkedHashSet<String>();
    private Deque<String> cache;

    //CTOR
    public LRU() {
        this.wordsList = new LinkedHashSet<String>();
        this.cache = new LinkedList<String>();
    }

   @Override
    public void add(String word){
       String removedWord = null;
        // if a word is already at the cache, remove to add it from last
           if(cache.contains(word)){
               cache.remove(word);
           }
           cache.push(word);
           wordsList.add(word);

    }

    @Override
    public String remove(){
        String removedWord = null;
        removedWord = cache.removeLast();
        wordsList.remove(removedWord);
        return removedWord;
    }

    private boolean isFull(int size){
        int counter = 0;
        boolean flag = true;
        for(String str : cache){
            counter++;
        }
        if(counter != size){
            flag = false;
        }
        return flag;
    }

}
