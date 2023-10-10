import java.util.List;

public class Square {
    private int row;
    private int column;
//    private Soldier soldier;

    public Square(int row, int column) {
        this.row = row;
        this.column = column;
//        this.soldier = soldier;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Square{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
