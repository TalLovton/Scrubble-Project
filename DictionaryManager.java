

package test;

import java.util.Dictionary;
import java.util.HashMap;

public class DictionaryManager {
    private static DictionaryManager dm;
    private HashMap<String,test.Dictionary> dMap;

    public DictionaryManager() {
        this.dMap = new HashMap<String,test.Dictionary>();
    }

    public static DictionaryManager get() {
        if (dm == null) {
            dm = new DictionaryManager();
        }

        return dm;
    }

    public int getSize() {
        return dMap.size();
    }

    public boolean query(String...args) {
        int i = 0;
        int argsSize = args.length;
        String searchQ = args[argsSize-1];
        test.Dictionary tmpDictionary = new test.Dictionary(args);
        for(i =0; i < argsSize-1; i++){
            if(dm.dMap.containsKey(args[i])){
                return false;
            }
            else{
                if(tmpDictionary.query(searchQ)){
                    return true;
                }
                dm.dMap.put(args[i],tmpDictionary);
                return false;
            }
        }
        return true;

    }

    public boolean challenge(String... args) {
        int i = 0;
        int argsSize = args.length;
        String searchQ = args[argsSize-1];
        test.Dictionary tmpDictionary = new test.Dictionary(args);
        for(i =0; i < argsSize-1; i++){
            if(dm.dMap.containsKey(args[i])){
                return false;
            }
            if(tmpDictionary.challenge(searchQ)){
                /*i*//*f(!dm.dMap.containsKey(args[i])){
                    dm.dMap.put(searchQ,tmpDictionary);
                }*/
                return true;
            }
            else{
                dm.dMap.put(args[i],tmpDictionary);
                return false;
            }

        }
        return false;
    }
}
