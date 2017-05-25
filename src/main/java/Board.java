/**
 * Created by tudor on 15-May-17.
 */
public class Board {
    public static final int ROWS=3;
    public static final int COLS=3;
    Cell[][] cells;
    int currentRow, currentCol;

    public Board()
    {
        int i,j;
        cells = new Cell[ROWS][COLS];
        for (i=0; i<ROWS; i++)
            for (j=0; j<COLS; j++)
                cells[i][j]= new Cell(i,j);
    }

    public boolean DrawGame ()
    {
        int i,j;
        for (i=0; i<ROWS; i++)
            for (j=0; j<COLS; j++)
                if (cells[i][j].cellContent==Content.EMPTY) return false;
        return true;
    }

    public boolean WinGame (Content LastMove)
    {
        if (cells[currentRow][0].cellContent == LastMove && cells[currentRow][1].cellContent == LastMove &&
                cells[currentRow][2].cellContent == LastMove) return true;
        else if (cells[0][currentCol].cellContent == LastMove && cells[1][currentCol].cellContent == LastMove &&
                cells[2][currentCol].cellContent == LastMove ) return true;
        else if (cells[0][0].cellContent == LastMove && cells[1][1].cellContent == LastMove &&
                cells[2][2].cellContent == LastMove) return true;
        else if (cells[0][2].cellContent == LastMove && cells[1][1].cellContent == LastMove &&
                cells[2][0].cellContent == LastMove) return true;
        return  false;
    }
    public void BoardPrint()
    {
        int i,j;
        for (i=0; i<ROWS; i++)
        {
            for (j=0; j<COLS; j++)
            {
                cells[i][j].print();
                if (j<COLS-1) System.out.print("|");
            }
            System.out.println("");
            if (i<ROWS-1) System.out.println("-----------");
        }
    }
}
