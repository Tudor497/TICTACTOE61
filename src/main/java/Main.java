import java.awt.*;
import java.util.Random;
import java.util.Scanner;
import java.sql.*;

/**
 * Created by tudor on 15-May-17.
 */
public class Main {
    private Board currentBoard =new Board();
    private GameState currentState;
    private Content user,pc, currentPlayer;
    public static Scanner sc = new Scanner(System.in);
    private int Verify = 0;

    public Main()
    {
        Menu();
    }

    public void Menu()
    {

        System.out.println("Menu");
        System.out.println("1.New Game");
        System.out.println("2.Load Game");
        System.out.println("3.Save Game");
        int choice;
        do {
            System.out.print("Your option:");
            choice = sc.nextInt();
            switch (choice)
            {
                case 1 : newGame(); break;
                case 2 : loadGame(); break;
                case 3 : saveGame(); break;
                default: System.out.println("Select 1,2 or 3 "); break;
            }
        }
        while (choice<1 || choice>3);
    }

    public void newGame()
    {
        //currentBoard=new Board();
        if (Verify == 0) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    currentBoard.cells[i][j].cellContent = Content.EMPTY;
        }
        Verify = 0;
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
                System.exit(0);
            }
            else
            {
                currentState = GameState.PCWIN;
                System.out.println("PC Win");
                System.exit(0);
            }

        }
        else if (currentBoard.DrawGame())
        {
            currentState = GameState.DRAW;
            System.out.println("It's a draw");
            System.exit(0);
        }
    }
    public void saveGame()
    {
        String Name;
        System.out.println("Name of the game: ");
        Name = sc.next();
        char[][] Matrix = new char[3][3];
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++) {
                if (currentBoard.cells[i][j].cellContent == Content.X) Matrix[i][j] = 'X';
                else if (currentBoard.cells[i][j].cellContent == Content.O) Matrix[i][j] = 'O';
                else Matrix[i][j]='N';
            }

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
            Statement myStmt = myConn.createStatement();
             myStmt.executeUpdate("insert into GAME (GameName, SaveDate, P11, P12, P13, P21, P22, P23, P31, P32, P33) " +
                    "values('" + Name + "', SYSDATE(), '" + Matrix[0][0] + "','" + Matrix[0][1] + "','" +  Matrix[0][2] + "','" +  Matrix[1][0] + "','" +
                    Matrix[1][1] + "','"  + Matrix[1][2] + "','" + Matrix[2][0] + "','" + Matrix[2][1] + "','" + Matrix[2][2] + "');"  );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                currentBoard.cells[i][j].cellContent = Content.EMPTY;
    }
    public  void loadGame()
    {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
            Statement myStmt = myConn.createStatement();
            ResultSet myResult =  myStmt.executeQuery("select * from GAME");
            while(myResult.next())
            {
                System.out.println(myResult.getString(1) + ". " + myResult.getString(2)+ " " + myResult.getString(3));
            }
            System.out.println("What is your choice (select the number of the game) :");
            String myChoice=null;
            if(sc.hasNext()) {
               myChoice = sc.next();
            }
            ResultSet myQuerry = myStmt.executeQuery("select P11, P12, P13, P21, P22, P23, P31, P32, P33 from GAME where GameId = " +myChoice+";");
            //newGame();
            while(myQuerry.next()) {
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++) {
                        if (myQuerry.getString(3 * i + j + 1).charAt(0) == 'X')
                            currentBoard.cells[i][j].cellContent = Content.X;
                        else if (myQuerry.getString(3 * i + j + 1).charAt(0) == 'O')
                            currentBoard.cells[i][j].cellContent = Content.O;
                        else currentBoard.cells[i][j].cellContent = Content.EMPTY;
                    }

            }
            Verify = 1;
            newGame();


        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Main();
    }

}
