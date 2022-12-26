package test;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board{
    private static final int  WORD_SIZE = 15;
    private static final char STAR_LET = '*';
    private static final char TRIPLE_WORD = '$';
    private static final char DOUBLE_WORD = '@';
    private static final char TRIPLE_LET= '#';
    private static final char DOUBLE_LET = '%';
    private static final int STAR = 7;
    private static int star = 0; // static var to make sure that only first player getting star bonus
    private Tile[][] boardMat = new Tile[WORD_SIZE][WORD_SIZE];
    private char[][] bonusMat = new char[WORD_SIZE][WORD_SIZE];
    private static Board mat;


    public Board(char[][] bonusMat,Tile[][] tilesBoard) {
        this.bonusMat = bonusMat;
        this.boardMat = tilesBoard;
    }

    // initialised two matrix, one for bonus and one for board Tiles
    public static Board getBoard() {
        if (mat == null) {
            // $ - Triple word
            // @ - Double word
            // # - Triple letter
            // % - Double letter
            char[][] bonusBoard = {{'$', '-', '-', '%', '-', '-', '-', '$', '-', '-', '-', '%', '-', '-', '$'},
                    {'-', '@', '-', '-', '-', '#', '-', '-', '-', '#', '-', '-', '-', '@', '-'},
                    {'-', '-', '@', '-', '-', '-', '%', '-', '%', '-', '-', '-', '@', '-', '-'},
                    {'%', '-', '-', '@', '-', '-', '-', '%', '-', '-', '-', '@', '-', '-', '%'},
                    {'-', '-', '-', '-', '@', '-', '-', '-', '-', '-', '@', '-', '-', '-', '-'},
                    {'-', '#', '-', '-', '-', '#', '-', '-', '-', '#', '-', '-', '-', '#', '-'},
                    {'-', '-', '%', '-', '-', '-', '%', '-', '%', '-', '-', '-', '%', '-', '-'},
                    {'$', '-', '-', '%', '-', '-', '-', '*', '-', '-', '-', '%', '-', '-', '$'},
                    {'-', '-', '%', '-', '-', '-', '%', '-', '%', '-', '-', '-', '%', '-', '-'},
                    {'-', '#', '-', '-', '-', '#', '-', '-', '-', '#', '-', '-', '-', '#', '-'},
                    {'-', '-', '-', '-', '@', '-', '-', '-', '-', '-', '@', '-', '-', '-', '-'},
                    {'%', '-', '-', '@', '-', '-', '-', '%', '-', '-', '-', '@', '-', '-', '%'},
                    {'-', '-', '@', '-', '-', '-', '%', '-', '%', '-', '-', '-', '@', '-', '-'},
                    {'-', '@', '-', '-', '-', '#', '-', '-', '-', '#', '-', '-', '-', '@', '-'},
                    {'$', '-', '-', '%', '-', '-', '-', '$', '-', '-', '-', '%', '-', '-', '$'}};
            Tile[][] tilesBoard = new Tile[WORD_SIZE][WORD_SIZE];
            mat = new Board(bonusBoard,tilesBoard);
            return mat;
        }
        return mat;
    }

    // getting only the tiles that placed on the board
    public Tile[][] getTiles(){
        Tile[][] tilesArr = new Tile[WORD_SIZE][WORD_SIZE];
        int i,j;
        for(i =0; i < WORD_SIZE; i++){
            for(j = 0; j < WORD_SIZE; j++) {
                    tilesArr[i][j] = mat.boardMat[i][j];
                }
            }
        return tilesArr;
    }

    // checking if given word is legal according to conditions
    public boolean boardLegal(Word w){
        boardMat = getTiles();
        int count = 0,i,flag;
        Tile tmpTile;
        boolean tmp;
        int wordSize = w.tilelsWordArr.length;

        // if the word index is in limits
        if(w.col < 0 || w.row < 0 || w.col > WORD_SIZE || w.row > WORD_SIZE ){
            return false;
        }
        // if the word size is in limit under 15 and above 0
        if((!w.vert && wordSize + w.col > WORD_SIZE) || (w.vert && wordSize + w.row > WORD_SIZE)){
            return false;
        }

        // checking first word on star
        tmp = checkStar(w,wordSize,boardMat);
        if(!tmp){
            return false;
        }
        // only when board is not empty
        if(boardMat[STAR][STAR] != null){
            // checking if word is close to other tiles
            flag = checkWordPlace(w);
            if(flag != 1){
                return false;
            }
            else{
                // check if tile is not running over existing tile
                flag = checkBoardTiles(w);
                if(flag != 1){
                    return false;
                }
            }
        }
        return true;
    }

    // check if the star location is filled first
    private boolean checkStar(Word w,int wordSize,Tile[][] tilesArr){
        if(tilesArr[STAR][STAR] == null){
            if(w.vert){
                if(w.row + wordSize >= STAR && w.col == STAR){
                    return true;
                }
            }
            if(!w.vert){
                if(w.col + wordSize >= STAR && w.row == STAR){
                    return true;
                }
            }
        }
        else{
            return true;
        }
        return false;
    }

    //check if one of the letters is close to let in board
    private int checkWordPlace(Word w) {
        Tile tmpTile = null;
        int i, wordSize = w.tilelsWordArr.length, flag = 0;
        int row = w.row;
        int col = w.col;
        if(boardMat[STAR][STAR] == null){
            return flag = 1;
        }
        for (i = 0; i < wordSize; i++) {
            if (w.vert) {
                // check up
                if (i == 0 && row - 1 > 0) {
                    if (boardMat[row - 1][col] != null) {
                        return flag = 1;
                    }
                }
                // check left
                 if (col - 1 > 0) {
                    if (boardMat[row + i][col - 1] != null) {
                        return flag = 1;
                    }
                }
                // check right
                 if (col + 1 < WORD_SIZE) {
                    if (boardMat[row + i][col + 1] != null) {
                        return flag = 1;
                    }
                }
                // check down
                 if (i == wordSize - 1 && row + 1 < WORD_SIZE) {
                    if (boardMat[row + 1][col + i + 1] != null) {
                        return flag = 1;
                    }
                }
            }

                if (!w.vert) {
                    //check left
                    if (i == row && col - 1 > 0) {
                        if (boardMat[row][col - 1] != null) {
                            return flag = 1;
                        }
                    }
                    //check up
                     if (row - 1 > 0) {
                        if (boardMat[row - 1][col + i] != null) {
                            return flag = 1;
                        }
                    }
                    //check down
                     if (row + 1 < WORD_SIZE) {
                        if (boardMat[row + 1][col + i] != null) {
                            return flag = 1;
                        }
                    }
                    //check right
                     if (i == wordSize - 1 && col + 1 < WORD_SIZE) {
                        if (boardMat[row + i + 1][col] != null) {
                            return flag = 1;
                        }
                    }

                }

        }
        return flag;
    }

    // check if a tile is replaced in the board
    private int checkBoardTiles(Word w) {
        int flag = 0,i,wordSize = w.tilelsWordArr.length;
        int count = 0;
        int row = w.row;
        int col = w.col;

        for(i =0; i < wordSize; i++){
            if(w.tilelsWordArr[i] != null){
                count++;
            }
            if(w.vert){
                // checking words like "FA_M", the R is already on board
                if(boardMat[row+i][col] != null && w.tilelsWordArr[i] == null){
                    return flag = 1;
                }
            }
            if(!w.vert){
                if(boardMat[row][col+i] != null && w.tilelsWordArr[i] == null){
                    return flag =1;
                }
            }
        }
        // only when a word is full, with no null Tiles
        if(count == wordSize){
            flag = 1;
        }
        return flag;
    }

    public boolean dictionaryLegal(Word w){
        return true;
    }

    // getting all the new words that created from w(to get score+bonus on them)
    public ArrayList<Word> getWords(Word w){
        ArrayList<Word> wordsArr = new ArrayList<Word>();
        Word tmpWord = null;
        int i = 0,wordSize = w.tilelsWordArr.length;
        int row = w.row;
        int col = w.col;

        for(i =0; i < wordSize; i++){
            if(w.vert) {
                if (w.tilelsWordArr[i] != null) {
                    tmpWord = traceWordsLeftToRight(w.tilelsWordArr[i], row + i, col, w.vert);
                    if (dictionaryLegal(tmpWord)) {
                        wordsArr.add(tmpWord);
                    }
                }
                else{
                    w.tilelsWordArr[i] = mat.boardMat[row+i][col];
                }
            }

            else {
                if (w.tilelsWordArr[i] != null) {
                    tmpWord = traceWordsUpToDown(w.tilelsWordArr[i], row, col + i, w.vert);
                    if (dictionaryLegal(tmpWord)) {
                        wordsArr.add(tmpWord);
                    }
                }
                else{
                    // when a tile is null = the tile already at board
                    w.tilelsWordArr[i] = mat.boardMat[row][col+i];
                }
            }
        }
        wordsArr.add(w);
        return wordsArr;
    }

    // only for !vert words
    private Word traceWordsUpToDown(Tile tile,int row,int col,boolean vert){
        Word word = null;
        int tmpCol = 0,tmpRow = 0,i =1;
        int tmpSize = 0;
        //check for upper tiels
            while (boardMat[row - i][col] != null && row - i > 0) {
                i++;
            }
            // if there is no upper tiles and the possibility of first null tile
            if( i == 1 && boardMat[row + 1][col] != null){
                boardMat[row][col] = tile;
            }
            tmpCol = col;
            tmpRow = row-i+1;
            tmpSize = i-1;
            ArrayList<Tile> tmpArr = new ArrayList<>(tmpSize);
            i = 0;
            // from the uppest tile look for word down
                while (boardMat[tmpRow + i][tmpCol] != null) {
                    if (i >= tmpSize) {
                        tmpSize += 1;
                    }
                    tmpArr.add(boardMat[tmpRow + i][tmpCol]);
                    // if the next tile is null for w but exist in board
                    if(tmpRow + i + 1 == row){
                        boardMat[tmpRow + i + 1][tmpCol] = tile;
                    }
                    i++;
                }
                word = new Word(tmpArr.toArray(new Tile[tmpSize]), tmpRow, tmpCol, true);
        return word;

    }

    private Word traceWordsLeftToRight(Tile tile,int row,int col,boolean vert){

        Word word = null;
        int tmpSize = 0;
        int tmpCol = 0,tmpRow = 0,i =1;
        //check for lefter tiels
            while(boardMat[row][col-i] != null && col -i > 0){
                i++;
            }
            if( i == 1 && boardMat[row][col+1] != null){
                boardMat[row][col] = tile;
            }
            tmpCol = col-i+1;
            tmpRow = row;
            tmpSize = i-1;
            ArrayList<Tile> tmpArr = new ArrayList<>(tmpSize);
            i = 0;
            // from the leftest tile look for word to right
            while(boardMat[tmpRow][tmpCol+i] != null){
                if(i >= tmpSize){
                    tmpSize += 1;
                }
                tmpArr.add(boardMat[tmpRow][tmpCol+i]);
                // if the next tile is null for w but exist in board
                if(tmpCol + i + 1 == col){
                    boardMat[tmpRow][tmpCol+ i + 1] = tile;
                }
                i++;
            }
            word = new Word(tmpArr.toArray(new Tile[tmpSize]),tmpRow,tmpCol,false);
        return word;
    }
    // calculating score for each word in the list
    public int getScore(Word w){
        int i,score = 0;
        int tripleWord = 0,doubleWord = 0;
        int wordSize = w.tilelsWordArr.length;

        for(i = 0; i < wordSize; i++){
                if(w.vert) {
                    if(bonusMat[w.row+i][w.col] == STAR_LET && star == 0){
                        score += w.tilelsWordArr[i].getVal();
                        doubleWord++;
                        star++;
                    }
                    else if(bonusMat[w.row+i][w.col] == TRIPLE_WORD) {
                        score += w.tilelsWordArr[i].getVal();
                        tripleWord++;
                    }
                    else if(bonusMat[w.row+i][w.col] == DOUBLE_WORD){
                        score += w.tilelsWordArr[i].getVal();
                        doubleWord++;
                    }
                    else if(bonusMat[w.row+i][w.col] == TRIPLE_LET){
                        score += w.tilelsWordArr[i].getVal() * 3;
                    }
                    else if(bonusMat[w.row+i][w.col] == DOUBLE_LET){
                        score += w.tilelsWordArr[i].getVal() * 2;
                    }
                    else{
                        score += w.tilelsWordArr[i].getVal();
                    }
                }
                else{
                    if(bonusMat[w.row][w.col+i] == STAR_LET && star == 0){
                        score += w.tilelsWordArr[i].getVal();
                        doubleWord++;
                        star++;
                    }
                    else if(bonusMat[w.row][w.col+i] == TRIPLE_WORD) {
                        score += w.tilelsWordArr[i].getVal();
                        tripleWord++;
                    }
                    else if(bonusMat[w.row][w.col+i] == DOUBLE_WORD){
                        score += w.tilelsWordArr[i].getVal();
                        doubleWord++;
                    }
                    else if(bonusMat[w.row][w.col+i] == TRIPLE_LET){
                        score += w.tilelsWordArr[i].getVal() * 3;
                    }
                    else if(bonusMat[w.row][w.col+i] == DOUBLE_LET){
                        score += w.tilelsWordArr[i].getVal() * 2;
                    }
                    else{
                       score += w.tilelsWordArr[i].getVal();
                    }
                }
        }
        score = sumBonuses(score,doubleWord,tripleWord);
        return score;
    }

    // side func in getScore to calculate word bonuses
    public int sumBonuses(int score,int doubleBonus, int tripleBonus){
        int i;
        for(i =0; i < doubleBonus; i++){
            score *= 2;
        }
        for(i =0; i < tripleBonus; i++){
            score *= 3;
        }
        return score;
    }

    // placing word at board, first checking legal and the other words from list, and then placing at board
    public int tryPlaceWord(Word w){
        int i,j,sumScore = 0;
        boolean flag = boardLegal(w);
        Word tmpWord = null;
        if(flag){
            ArrayList<Word> wordsArr = getWords(w);
            for(i =0; i < wordsArr.size(); i++){
                tmpWord = wordsArr.get(i);
                if(tmpWord != null){
                    sumScore += getScore(tmpWord);
                    for(j =0; j < tmpWord.tilelsWordArr.length; j++){
                        if(tmpWord.vert){
                            mat.boardMat[tmpWord.row+j][tmpWord.col] = tmpWord.tilelsWordArr[j];
                        }
                        else{
                            mat.boardMat[tmpWord.row][tmpWord.col+j] = tmpWord.tilelsWordArr[j];
                        }
                    }
                }
            }
        }
        return sumScore;
    }



}

