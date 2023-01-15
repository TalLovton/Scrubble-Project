package test;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    private String[] filesArr;
    private CacheManager caschLRU;
    private CacheManager caschLFU;
    private BloomFilter bFilter;


    public Dictionary(String...args){
        this.filesArr = new String[args.length];
        caschLRU = new CacheManager(400,new LRU());
        caschLFU = new CacheManager(100,new LFU());
        bFilter = new BloomFilter(256,"MD5","SHA1");
        String temp = null;
        int i = 0;
        try{
            while(i < args.length) {
                this.filesArr[i] = args[i];
                File tmpFile = new File(args[i]);
                Scanner scan = new Scanner(tmpFile);
                while (scan.hasNext()) {
                    temp = scan.next();
                    bFilter.add(temp);
                }
                scan.close();
                i++;
            }

        }
        catch(FileNotFoundException f){
            System.out.println("Error with opened File");
        }
    }

    public boolean query(String word){
        boolean tmp;
        if(caschLRU.query(word)){
            return true;
        }
        else if(caschLFU.query(word)){
            return false;
        }
        else{
            tmp = bFilter.contains(word);
            if(tmp){
                caschLRU.add(word);
            }
            else{
                caschLFU.add(word);
            }
        }
        return tmp;
    }

    public boolean challenge(String word){
        boolean tmp;
        tmp = IOSearcher.search(word,filesArr);
        if(tmp){
            caschLRU.add(word);
        }
        else{
            caschLFU.add(word);
        }
        return tmp;
    }
}
