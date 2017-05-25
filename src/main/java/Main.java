import java.util.Random;
import java.util.Scanner;
import java.sql.*;

/**
 * Created by tudor on 15-May-17.
 */
public class Main {
    private Board currentBoard;
    private GameState currentState;
    private Content user,pc, currentPlayer;
    public static Scanner sc = new Scanner(System.in);

    public Main()
    {
        Menu();
    }

    public void Menu()
    {
        System.out.println("Menu");
        System.out.println("1.New Game");
        System.out.println("2.Load Game");
        int choice;
        do {
            System.out.print("Your option:");
            choice = sc.nextInt();
            switch (choice)
            {
                case 1 : newGame(); break;
                case 2 : loadGame(); break;
                default: System.out.println("Select 1 or 2"); break;
            }
        }
        while (choice<1 || choice>3);
    }

    public void newGame()
    {
        currentBoard = new Board();

        initialize();
        do {
            if (currentPlayer==user) userOption();
            else computerMove();
            currentBoard.BoardPrint();
            updateGame(currentPlayer);
            currentPlayer = (currentPlayer == user) ? pc : user;
        }
        while(currentState == GameState.PLAYING);

    }

    public void initialize ()
    {
        currentState = GameState.PLAYING;
        Random rand = new Random();
        int userKey = rand.nextInt(2);
        if (userKey==0)
        {
            currentPlayer = user = Content.X;
            pc = Content.O;
            currentBoard.BoardPrint();
            System.out.println("User Starts with X");
        }
        else
        {
            user = Content.O;
            currentPlayer = pc = Content.X;
            System.out.println("Computer starts with X");
        }
    }
    public void userOption()
    {
        System.out.println("Enter your move: 2 values which represent the row and the column or press m for menu");
        if (sc.hasNext("m")) {
            String menuSelected;
            menuSelected = sc.next();
            Menu();
           return;
        }
        else {
            boolean validInput = false;
            do {


                int row = sc.nextInt() - 1;
                int col = sc.nextInt() - 1;

                if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS && currentBoard.cells[row][col].cellContent == Content.EMPTY) {
                    currentBoard.cells[row][col].cellContent = user;
                    currentBoard.currentRow = row;
                    currentBoard.currentCol = col;
                    validInput = true;
                } else {
                    System.out.println("Your move is not valid. Please enter a valid move");
                }

            }
            while (!validInput);
        }
    }
    public void computerMove()
    {
        Random rand = new Random();
        int row=0,col=0,i,j,k;
        boolean position=false;

        //Win with a line
        for (i=0; position==false && i<Board.ROWS; i++)
        {
            if (currentBoard.cells[i][0].cellContent == Content.EMPTY &&
                    currentBoard.cells[i][1].cellContent == pc && currentBoard.cells[i][2].cellContent == pc )
            {
                row=i;
                col=0;
                position=true;
            }


            else if (currentBoard.cells[i][1].cellContent == Content.EMPTY &&
                    currentBoard.cells[i][0].cellContent == pc && currentBoard.cells[i][2].cellContent == pc )
            {
                row=i;
                col=1;
                position=true;
            }

            else if (currentBoard.cells[i][2].cellContent == Content.EMPTY &&
                    currentBoard.cells[i][0].cellContent == pc && currentBoard.cells[i][1].cellContent == pc )
            {
                row=i;
                col=2;
                position=true;
            }
        }
        //Win with a column

        for (j=0; position==false && j<Board.COLS; j++)
        {
            if (currentBoard.cells[0][j].cellContent == Content.EMPTY &&
                    currentBoard.cells[1][j].cellContent == pc && currentBoard.cells[2][j].cellContent == pc )
            {
                row=0;
                col=j;
                position=true;
            }

            else if (currentBoard.cells[1][j].cellContent == Content.EMPTY &&
                    currentBoard.cells[0][j].cellContent == pc && currentBoard.cells[2][j].cellContent == pc )
            {
                row=1;
                col=j;
                position=true;
            }

            else if (currentBoard.cells[2][j].cellContent == Content.EMPTY &&
                    currentBoard.cells[0][j].cellContent == pc && currentBoard.cells[1][j].cellContent == pc )
            {
                row=2;
                col=j;
                position=true;
            }
        }


        //Win with a diagonal
            if (currentBoard.cells[0][0].cellContent == Content.EMPTY &&
            currentBoard.cells[1][1].cellContent == pc && currentBoard.cells[2][2].cellContent == pc )
    {
        row=0;
        col=0;
        position=true;
    }
        else if (currentBoard.cells[1][1].cellContent == Content.EMPTY &&
                currentBoard.cells[0][0].cellContent == pc && currentBoard.cells[2][2].cellContent == pc )
        {
            row=1;
            col=1;
            position=true;
        }
        else if (currentBoard.cells[2][2].cellContent == Content.EMPTY &&
                currentBoard.cells[0][0].cellContent == pc && currentBoard.cells[1][1].cellContent == pc )
        {
            row=2;
            col=2;
            position=true;
        }

        if (currentBoard.cells[0][2].cellContent == Content.EMPTY &&
                currentBoard.cells[1][1].cellContent == pc && currentBoard.cells[2][0].cellContent == pc )
        {
            row=0;
            col=2;
            position=true;
        }
        else if (currentBoard.cells[1][1].cellContent == Content.EMPTY &&
                currentBoard.cells[0][2].cellContent == pc && currentBoard.cells[2][0].cellContent == pc )
        {
            row=1;
            col=1;
            position=true;
        }
        else if (currentBoard.cells[2][0].cellContent == Content.EMPTY &&
                currentBoard.cells[0][2].cellContent == pc && currentBoard.cells[1][1].cellContent == pc )
        {
            row=2;
            col=0;
            position=true;
        }

        //Try to draw with a line
        for (i=0; position==false && i<Board.COLS; i++)
        {
            if (currentBoard.cells[i][0].cellContent == Content.EMPTY &&
                    currentBoard.cells[i][1].cellContent == user && currentBoard.cells[i][2].cellContent == user )
            {
                row=i;
                col=0;
                position=true;
            }


            else if (currentBoard.cells[i][1].cellContent == Content.EMPTY &&
                    currentBoard.cells[i][0].cellContent == user && currentBoard.cells[i][2].cellContent == user )
            {
                row=i;
                col=1;
                position=true;
            }

            else if (currentBoard.cells[i][2].cellContent == Content.EMPTY &&
                    currentBoard.cells[i][0].cellContent == user && currentBoard.cells[i][1].cellContent == user )
            {
                row=i;
                col=2;
                position=true;
            }
        }
        //Try to draw with a column
        for (j=0; position==false && j<Board.ROWS; j++)
        {
            if (currentBoard.cells[0][j].cellContent == Content.EMPTY &&
                    currentBoard.cells[1][j].cellContent == user && currentBoard.cells[2][j].cellContent == user )
            {
                row=0;
                col=j;
                position=true;
            }

            else if (currentBoard.cells[1][j].cellContent == Content.EMPTY &&
                    currentBoard.cells[0][j].cellContent == user && currentBoard.cells[2][j].cellContent == user )
            {
                row=1;
                col=j;
                position=true;
            }

            else if (currentBoard.cells[2][j].cellContent == Content.EMPTY &&
                    currentBoard.cells[0][j].cellContent == user && currentBoard.cells[1][j].cellContent == user )
            {
                row=2;
                col=j;
                position=true;
            }
        }

        //Try to draw with a diagonal
        if (currentBoard.cells[0][0].cellContent == Content.EMPTY &&
                currentBoard.cells[1][1].cellContent == user && currentBoard.cells[2][2].cellContent == user )
        {
            row=0;
            col=0;
            position=true;
        }
        else if (currentBoard.cells[1][1].cellContent == Content.EMPTY &&
                currentBoard.cells[0][0].cellContent == user && currentBoard.cells[2][2].cellContent == user )
        {
            row=1;
            col=1;
            position=true;
        }
        else if (currentBoard.cells[2][2].cellContent == Content.EMPTY &&
                currentBoard.cells[0][0].cellContent == user && currentBoard.cells[1][1].cellContent == user )
        {
            row=2;
            col=2;
            position=true;
        }

        if (currentBoard.cells[0][2].cellContent == Content.EMPTY &&
                currentBoard.cells[1][1].cellContent == user && currentBoard.cells[2][0].cellContent == user )
        {
            row=0;
            col=2;
            position=true;
        }
        else if (currentBoard.cells[1][1].cellContent == Content.EMPTY &&
                currentBoard.cells[0][2].cellContent == user && currentBoard.cells[2][0].cellContent == user )
        {
            row=1;
            col=1;
            position=true;
        }
        else if (currentBoard.cells[2][0].cellContent == Content.EMPTY &&
                currentBoard.cells[0][2].cellContent == user && currentBoard.cells[1][1].cellContent == user )
        {
            row=2;
            col=0;
            position=true;
        }
        while (!position)
        {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
            if (currentBoard.cells[row][col].cellContent == Content.EMPTY) position=true;
        }
        currentBoard.cells[row][col].cellContent = pc;
        currentBoard.currentCol=col;
        currentBoard.currentRow=row;
        System.out.println("Computer moved");
    }

    public void updateGame(Content currentPlayer)
    {
        if (currentBoard.WinGame(currentPlayer))
        {
            if (currentPlayer == user)
            {
                currentState = GameState.USERWIN;
                System.out.println("You Win");
            }
            else
            {
                currentState = GameState.PCWIN;
                System.out.println("PC Win");
            }

        }
        else if (currentBoard.DrawGame())
        {
            currentState = GameState.DRAW;
            System.out.println("It's a draw");
        }
    }
    public void saveGame()
    {
    }
    public  void loadGame()
    {


    }
    public static void main(String[] args) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from GAME");
            while (myRs.next())
            {
                System.out.println(myRs.getString(1)+ ". " + myRs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new Main();
    }

}
