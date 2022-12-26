package test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class IOSearcher {
        public static boolean search(String word,String fName1,String fName2 ){
                String temp1 = null;
                String temp2 = null;
                try{
                        File file1 = new File(fName1);
                        File file2 = new File(fName2);
                        Scanner scan1 = new Scanner(file1);
                        Scanner scan2 = new Scanner(file2);
                        while(scan1.hasNextLine() && scan2.hasNextLine()){
                            temp1 = scan1.nextLine();
                            temp2 = scan2.nextLine();
                            if(temp1.contains(word) || temp2.contains(word)){
                                    return true;
                            }
                        }
                        scan1.close();
                        scan2.close();

                }
                catch(FileNotFoundException f){
                        System.out.println("Error with opened File");
                }
                return false;
        }
}
