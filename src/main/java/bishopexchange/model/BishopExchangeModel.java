package bishopexchange.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
public class BishopExchangeModel {

    public static final int BOARD_WIDTH = 4;
    public static final int BOARD_HEIGHT = 5;

    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_WIDTH][BOARD_HEIGHT];

    public BishopExchangeModel() {
        for (var i = 0; i < BOARD_WIDTH; i++) {
            for (var j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<Square>(
                        switch (i) {
                            case 0 -> Square.WHITE;
                            case BOARD_HEIGHT - 1 -> Square.BLACK;
                            default -> Square.NONE;
                        }
                );
            }
        }
    }

    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public Square getSquare(Position p) {
        return board[p.row()][p.col()].get();
    }

    private void setSquare(Position p, Square square) {
        board[p.row()][p.col()].set(square);
    }

    public void move(Position from, Position to) {
        setSquare(to, getSquare(from));
        setSquare(from, Square.NONE);
    }

    public boolean canMove(Position from, Position to) {
        return isOnBoard(from) && isOnBoard(to) && !isEmpty(from) && isEmpty(to) && isPawnMove(from, to);
    }

    public boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }

    public static boolean isOnBoard(Position p) {
        return 0 <= p.row() && p.row() < BOARD_WIDTH && 0 <= p.col() && p.col() < BOARD_HEIGHT;
    }

    public static boolean isPawnMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx + dy == 1 || dx * dy == 1;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var i = 0; i < BOARD_WIDTH; i++) {
            for (var j = 0; j < BOARD_HEIGHT; j++) {
                sb.append(board[i][j].get().ordinal()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var model = new BishopExchangeModel();
        System.out.println(model);
    }
}
