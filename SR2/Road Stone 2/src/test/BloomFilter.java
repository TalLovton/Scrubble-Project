package test;

import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
	private BitSet bs;
    private int bitSize;
    private String hash1;
    private String hash2;


    public BloomFilter(int bitSize, String hash1,String hash2) {
        this.bitSize = bitSize;
        this.hash1 = hash1;
        this.hash2 = hash2;
        this.bs = new BitSet(bitSize);
        bs.clear();
    }

    public void add(String word){
        int ind1 = 0,ind2 = 0,ind = 0;
        try {
            MessageDigest obj1 = MessageDigest.getInstance(hash1);
            MessageDigest obj2 = MessageDigest.getInstance(hash2);
            byte[] byteArr1 = obj1.digest(word.getBytes());
            byte[] byteArr2 = obj2.digest(word.getBytes());
            BigInteger tmpVal1 = new BigInteger(byteArr1);
            BigInteger tmpVal2 = new BigInteger(byteArr2);
            ind1 = tmpVal1.intValue();
            if(ind1 < 0){
                ind1 *= -1;
            }
            ind = (ind1 % this.bitSize);
            bs.set(ind);

            ind2 = tmpVal2.intValue();
            if(ind2 < 0){
                ind2 *= -1;
            }
            ind = (ind2 % this.bitSize);
            bs.set(ind);
        }
        catch (NoSuchAlgorithmException e){
            System.out.println("ERROR with getInstance");
        }
    }

    public boolean contains(String word){
        boolean flag = true;
        BloomFilter wordBitSet = new BloomFilter(bitSize,hash1,hash2);
        wordBitSet.add(word);
        if(wordBitSet.bs.length() > this.bitSize || this.bs.isEmpty()){
            flag = false;
        }
        else if(!this.bs.intersects(wordBitSet.bs)){
            flag = false;
        }
        return flag;
    }

    public String toString(){
        String byteStr = "";
        for(int i = 0; i < bs.length(); i++) {
            if(bs.get(i)) {
                byteStr += "1";
            } else {
                byteStr += "0";
            }
        }
        return byteStr;
    }

}
