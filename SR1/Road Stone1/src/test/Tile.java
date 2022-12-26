package test;
import java.util.Objects;
import java.util.Random;


public class Tile {
    public final char letter;
    public final int val;

    // Getter's
    public char getLet() {return letter;}
    public int getVal() {return val;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && val == tile.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, val);
    }

    private Tile(char let, int val) {
        this.letter = let;
        this.val = val;
    }

// -------------------- Bag Class -----------------------
    public static class Bag {
        private static int counter;
        private static final int SIZE = 26;
        private static final int BAG_SIZE = 98;
        int[] letArr = new int[SIZE];
        Tile[] tileArr = new Tile[SIZE];
        private static Bag bag = null;

        private Bag(int[] letArr, Tile[] tileArr) {
            this.letArr = letArr;
            this.tileArr = tileArr;
        }
        // initialized the cash according to Epsilon
        public static Bag getBag() {
            Tile[] bagTiles = new Tile[SIZE];
            int[] scoreArr = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
            int[] amountArr = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            int i;
            if (bag == null) {
                for (i = 0; i < SIZE; i++) {
                    bagTiles[i] = new Tile((char)('A' + i), scoreArr[i]);
                }
                bag = new Bag(amountArr,bagTiles);
                return bag;
            }
            return bag;
        }

        // getting a random tile from cash
        public Tile getRand() {
            counter = 0;
            Random rand = new Random();
            int tmpRand = 0;
            int randNum = 0;
            randNum = rand.nextInt( SIZE - counter);
            if (this.size() != 0) {
                if (letArr[randNum] > 0) {
                    letArr[randNum]--;
                }
                else {
                    counter++;
                    tmpRand = randNum;
                    randNum = (randNum + 1) % 26;
                    while (tmpRand != randNum) {
                        if (letArr[randNum] > 0) {
                            letArr[randNum]--;
                            tmpRand = randNum;
                        } else {
                            randNum = (randNum++) % 26;
                        }
                    }
                }
                return tileArr[randNum];
            }
            return null;
        }

        // return a Tile from let
        public Tile getTile(char let) {
            int i;
            for (i = 0; i < SIZE; i++) {
                if(let >= 'A' && let <= 'Z'){
                    if (tileArr[i].letter == let) {
                        return tileArr[i];
                    }
                }
            }
            return null;
        }

        // return tile back to the cash
        public void put(Tile t) {
            if (size() < BAG_SIZE) {
                for (int i = 0; i < SIZE; i++) {
                    if (tileArr[i].letter == t.letter) {
                        letArr[i]++;
                    }
                }
            }
        }

        // summing the total amounts of Tiles in cash
        public int size() {
            int sumLet = 0;
            for (int i = 0; i < SIZE; i++) {
                sumLet += letArr[i];
            }
            return sumLet;
        }


        public int[] getQuantities() {
            int[] copiedArr = new int[SIZE];
            copiedArr = (int[]) letArr.clone();
            return copiedArr;
        }



    }
}
