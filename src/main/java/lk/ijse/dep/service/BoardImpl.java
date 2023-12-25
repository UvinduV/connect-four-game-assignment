package lk.ijse.dep.service;

public class BoardImpl implements Board{
    private BoardUI boardUI;
    private Piece[][] pieces;
   //cols=6 ,rows=5
    public BoardImpl(BoardUI boardUI){
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        initializePieces();
    }

    private void initializePieces(){ //initialize all pieces of board ,because it default null
        for(int col = 0; col < NUM_OF_COLS; col++){
            for(int row = 0; row < NUM_OF_ROWS; row++){
                pieces[col][row] = Piece.EMPTY;
            }
        }
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col){
        for(int row = NUM_OF_ROWS - 1; row >= 0; row--){
            if(pieces[col][row] == Piece.EMPTY) {//ea colomn eke available spot thiyanwanm eke row eka return
                return row;
            }
        }
        return -1; // not available spot
    }

    @Override
    public boolean isLegalMove(int col){
        if (findNextAvailableSpot(col) != -1) {//legal move ekakda kiyala check karanwa
            return true;
        }
        return false;
    }

    @Override
    public boolean existLegalMoves(){
        for(int col = 0; col < NUM_OF_COLS; col++){
            if(isLegalMove(col)){
                return true; // all board eke, eka legal move ekak ho thiyanwada kiyala column eka bagin check karanawa
            }
        }
        return false; // no have legal moves
    }

    @Override
    public void updateMove(int col,Piece move){
        pieces[col][findNextAvailableSpot(col)] = move;
        //ea specific cloumn eka athi first avilable spot ekata piece ekak move karanwa
    }

    @Override
    public void updateMove(int col,int row,Piece move){
        pieces[col][row] = move;
    }
    @Override
    public Winner findWinner(){
        // Check for a horizontal win
        for(int row = 0; row < NUM_OF_ROWS; row++){  //horizontal side check
            for(int col = 0; col < NUM_OF_COLS - 3; col++){// piece 4k nisa 3parak check karai.
                Piece piece = pieces[col][row];
                if(piece != Piece.EMPTY && piece == pieces[col + 1][row] && piece == pieces[col + 2][row] && piece ==
                        pieces[col + 3][row]){
                    // Horizontal win
                    return new Winner(piece ,col,col+3,row,row);
                }
            }
        }

        for(int col = 0; col < NUM_OF_COLS; col++){  //vertical side check
            // Check for a vertical win
            for(int row = 0; row < NUM_OF_ROWS - 3; row++){
                Piece piece = pieces[col][row];
                if(piece != Piece.EMPTY && piece == pieces[col][row + 1] && piece == pieces[col][row + 2] && piece ==
                        pieces[col][row + 3]){
                    // Vertical win
                    return new Winner(piece,col,col,row,row+3);
                }
            }
        }
        return new Winner(Piece.EMPTY,-1,-1,-1,-1);
    }
}
