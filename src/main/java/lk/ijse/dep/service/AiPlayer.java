package lk.ijse.dep.service;

public class AiPlayer extends Player {
    public AiPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
        col = selectNode();
        board.updateMove(col,Piece.GREEN);

        board.getBoardUI().update(col,false);

        Winner winner = board.findWinner();
        if(winner.getWinningPiece() == Piece.GREEN){
            board.getBoardUI().notifyWinner(winner);
        }else{
            if(!board.existLegalMoves()){
                board.getBoardUI().notifyWinner(winner);
            }
        }

    }

    private int selectNode() {
        boolean isUserWin = false;
        int tiedCol = 0;


        for(int col = 0; col < 6; ++col){   //column count ekata iterate wenawa
            if(board.isLegalMove(col)){
                int row = board.findNextAvailableSpot(col);
                board.updateMove(col,Piece.GREEN);
                int heuristicValue = minMax(0,false);
                board.updateMove(col,row,Piece.EMPTY);
                if(heuristicValue == 1){
                    return col; // to win AI this column
                }
                else if(heuristicValue == -1){
                    isUserWin = true; // user win chance eka wadi
                }else {
                    tiedCol = col; // to tied this ,no win or loss
                }
            }
        }
        if(isUserWin && board.isLegalMove(tiedCol)){  //uer  win is block
            return tiedCol;
        }else {
            int randomCol;

            do{
                randomCol = (int) (Math.random() * Board.NUM_OF_COLS);
            }while (!board.isLegalMove(randomCol));

            return randomCol; //nathnam normelly random num generate
        }
    }

    private int minMax(int depth, boolean maximizingPlayer) {
        Winner winner = board.findWinner();
        if(winner.getWinningPiece() == Piece.GREEN){
            return 1;
        }else if(winner.getWinningPiece() == Piece.BLUE){
            return -1;
        } else if (board.existLegalMoves() && depth == 2) {
            return 0;
        }

        int highScore;

        if(maximizingPlayer){
            highScore = Integer.MIN_VALUE;
            for(int col = 0; col < 6; ++col){
                if(board.isLegalMove(col)){
                    int row = board.findNextAvailableSpot(col);
                    board.updateMove(col,Piece.GREEN);
                    int score = minMax(depth + 1 , false);
                    board.updateMove(col , row, Piece.EMPTY);
                    if(score > highScore){// high score eka min nisa
                        highScore = score;
                    }
                }
            }
        }else {
            highScore = Integer.MAX_VALUE;
            for(int col = 0; col < 6; ++col){
                if(board.isLegalMove(col)){
                    int row = board.findNextAvailableSpot(col);
                    board.updateMove(col,Piece.BLUE);
                    int score = minMax(depth + 1 , true);
                    board.updateMove(col,row,Piece.EMPTY);
                    if(score < highScore){ //high score eka max nisa
                        highScore = score;
                    }
                }
            }
        }
        return highScore;
    }


}
