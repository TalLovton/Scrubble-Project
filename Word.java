package test;


import java.util.Arrays;

public class Word {
    private static final int WORD_SIZE = 15;
    Tile[] tilelsWordArr = new Tile[WORD_SIZE];
    int row,col;
    boolean vert;

    public Word(Tile[] tielsArr, int row, int col, boolean vert) {
        this.tilelsWordArr = tielsArr;
        this.row = row;
        this.col = col;
        this.vert = vert;
    }

    public Tile[] getTielsArr() {return tilelsWordArr;}

    public int getRow() {return row;}

    public int getCol() {return col;}

    public boolean isVert() {return vert;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vert == word.vert && Arrays.equals(tilelsWordArr, word.tilelsWordArr);
    }

}
