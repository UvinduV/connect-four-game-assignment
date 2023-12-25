package lk.ijse.dep.service;

public abstract class Player {
    Board board;
    public abstract void movePiece(int col);
    public Player(Board board){
        this.board=board;
    }
}
