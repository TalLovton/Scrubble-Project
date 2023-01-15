package test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class IOSearcher {
        public static boolean search(String word,String...fileNames){
                String temp1 = null;
                int i =0;
                try{
                        while(i < fileNames.length) {
                                File file1 = new File(fileNames[i]);
                                Scanner scan1 = new Scanner(file1);
                                while (scan1.hasNext()) {
                                        temp1 = scan1.next();
                                        if (temp1.contains(word)) {
                                                return true;
                                        }
                                }
                                scan1.close();
                                i++;
                        }

                }
                catch(FileNotFoundException f){
                        System.out.println("Error with opened File");
                }
                return false;
        }
}
