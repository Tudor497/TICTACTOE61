/**
 * Created by tudor on 15-May-17.
 */
public class Cell {
    int row;
    int col;
    Content cellContent;

    public Cell (int row, int col)
    {
        this.row = row;
        this.col = col;
        cellContent = Content.EMPTY;
    }
    public void print() {
        switch (cellContent) {
            case X: System.out.print(" X "); break;
            case O: System.out.print(" O "); break;
            case EMPTY: System.out.print("   "); break;
        }
    }
}
