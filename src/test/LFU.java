package test;
import java.util.HashMap;
import java.util.Map;

public class LFU implements CacheReplacementPolicy {
    private Map<String,Integer> freqMap;

    public LFU() {
        this.freqMap = new HashMap<String, Integer>();
    }

    @Override
    public void add(String word){
        int tmp = 0;
        if(freqMap.containsKey(word)){
            tmp = freqMap.get(word);
            tmp++;
        }
        else{
            tmp = 1;
        }
        freqMap.put(word,tmp);
    }
    @Override
    public String remove(){
        String tmpWord = null;
        int min = 0;
        for(Map.Entry<String, Integer> entry : freqMap.entrySet()){
            if(min == 0){
                min = entry.getValue();
                tmpWord = entry.getKey();
            }
            else if(entry.getValue() < min){
                min = entry.getValue();
                tmpWord = entry.getKey();
            }
        }
        return tmpWord;
    }
}
