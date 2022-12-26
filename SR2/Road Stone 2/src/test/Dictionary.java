package test;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    private String file1;
    private String file2;
    private CacheManager caschLRU;
    private CacheManager caschLFU;
    private BloomFilter bFilter;


    public Dictionary(String fName1,String fName2){
        this.file1 = fName1;
        this.file2 = fName2;
        caschLRU = new CacheManager(400,new LRU());
        caschLFU = new CacheManager(100,new LFU());
        bFilter = new BloomFilter(256,"MD5","SHA1");
        String temp1 = null;
        String temp2 = null;
        try{
            File file1 = new File(fName1);
            File file2 = new File(fName2);
            Scanner scan1 = new Scanner(file1);
            Scanner scan2 = new Scanner(file2);
            while(scan1.hasNext() && scan2.hasNext()){
                temp1 = scan1.next();
                temp2 = scan2.next();
                bFilter.add(temp1);
                bFilter.add(temp2);
            }
            scan1.close();
            scan2.close();
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
            caschLFU.add(word);
        }
        return tmp;
    }

    public boolean challenge(String word){
        boolean tmp;
        tmp = IOSearcher.search(word,file1,file2);
        if(tmp){
            caschLRU.add(word);
        }
        caschLFU.add(word);
        return tmp;
    }
}
