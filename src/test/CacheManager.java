package test;
import java.util.HashSet;

public class CacheManager {
    private int size;
    private CacheReplacementPolicy crp;
    private HashSet<String> wordsCache;

    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.size = size;
        this.crp = crp;
        wordsCache = new HashSet<String>();
    }



    public void add(String word){
        String tmpStr = null;
        if(wordsCache.size() < this.size){
            crp.add(word);
        }
        else{
           tmpStr = crp.remove();
            wordsCache.remove(tmpStr);
        }
        wordsCache.add(word);
    }

    public boolean query(String word){
        boolean flag = true;
        if(!wordsCache.contains(word)){
            flag = false;
        }
        return flag;

    }
}
