package test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {
    DictionaryManager dm;
    String[] strArr;
    PrintWriter out;
    Scanner in;
    boolean res;
    String strRes;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
            dm = new DictionaryManager();
            in = new Scanner(inFromclient);
            out = new PrintWriter(outToClient);
            String text = in.next();
            strArr = text.split(",");
            if (strArr[0].equals("Q")) {
                strArr = removeFirstStr(strArr);
                res = dm.query(strArr);
            } else {
                strArr = removeFirstStr(strArr);
                res = dm.challenge(strArr);
            }

            strRes = String.valueOf(res);
            out.println(strRes);
            out.flush();
    }
    @Override
    public void close() {
            this.in.close();
            this.out.close();
    }

    public String[] removeFirstStr(String[] strArr) {
        List<String> tmpList = new ArrayList(Arrays.asList(strArr));
        tmpList.remove(0);
        int strSize = tmpList.size();
        return (String[])tmpList.toArray(new String[strSize]);
    }
}