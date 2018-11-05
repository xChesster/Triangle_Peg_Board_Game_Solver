// 1 1 1 1 1 [5] (0,0) (0,1) (0,2) (0,3) (0,4)
//  1 1 1 1  [4]    (1,0) (1,1) (1,2) (1,3)
//   1 1 1   [3]       (2,0) (2,1) (2,2)
//    1 1    [2]          (3,0) (3,1)
//     0     [1]             (4,0)
// solving triangle tee game
public class TriangleGame {
   
   // declaring board variable to be used for game
   Board gameBoard;
   
   // constructor for triangle game
   public TriangleGame() {
      // initializing a board
      gameBoard = new Board();
   }
   
   // function calls the Board's solve method to solve the board
   public boolean play() {
      return gameBoard.solve();
   }
   
   // Board class used to manipulate game board
   private class Board {
      // initializing String that will store the winning sequence of moves for the gameboard
      String sequence = "";
      // initializing rows to be stored in game board
      private int size = 5;
      // board list holds all the rows
      private int[][] board = new int[size][size];
      // initializing counter to keep track of how many pieces remain on the board
      private int pieces = 14;
      // boolean variable to keep track of whether the game has been solved
      private boolean solved = false;
      // board constructor
      public Board() {
         // initializing the board
         initBoard();
      }
      
      // initializing the game board
      public void initBoard() {
         for(int i = 0; i < size; i++) {
            // length of a row
            int rowLen = size - i;
            // looping through each element in the row to mark 1's
            // where tees are located and 0 at the selected empty position
            for(int j = 0; j < rowLen; j++) {
               if(i == 0 && j == 0)
                  this.board[i][j] = 0;
               else
                  this.board[i][j] = 1;
            }
         }
         // initializing winning sequence string with first board position
         this.sequence = toString(board, sequence);
      }
      
      // function used by TriangleGame class to call the Board's solve function
      public boolean solve() {
         solve(board, pieces, sequence);
         return solved;
      }
      
      // function used to call the move functions to solve the board
      // board is solved when only one tee remains on the board
      public void solve(int[][] board, int pieces, String sequence) {
         // checking if the board has been solved
         if(solved)
            return;
         // checking if only one piece remains on the board
         if(pieces == 1) {
            System.out.print(sequence);
            solved = true;
            return;
         }
         // looping through each row of board
         for(int i = 0; i < size; i++) {
            // length of a row
            int rowLen = size - i;
            // looping through elements of row i of board
            for(int j = 0; j < rowLen; j++) {
               checkMove(i, j, pieces, copy(board), sequence);
            }
         }
      }
      
      // function used to check if a move on the board is legal
      // where x is a row of the board and y is an element of that row
      public boolean isLegalMove(int x, int y, int[][] board) {
         // checking if (x,y) coordinates are within the bounds of the gameboard
         if(!isWithinBounds(x, y, board))
            return false;
         // checking if (x,y) is occupied by a piece
         if(isOccupied(x, y, board))
            return false;
         return true;
      }
      
      // function used to check if (x,y) coordinates are within the bounds of the gameboard
      public boolean isWithinBounds(int x, int y, int[][] board) {
         // length of a row
         int rowLen = size - x;
         // first checking if the move is within the bounds of the board
         if(x < 0 || x >= board.length || y < 0)
            return false;
         // checking if y is within the bounds of row x
         if(y >= rowLen)
            return false;
         return true;
      }
      
      // function used to check if there is an occupied position at (x,y)
      public boolean isOccupied(int x, int y, int[][] board) {
         if(board[x][y] == 1)
            return true;
         return false;
      }
      
      // function used to check if an element is out of bounds for row x
      public boolean outOfBounds(int x, int y) {
         // checking if (x,y) is out of bounds for row x
         if(y >= size - x)
            return true;
         return false;
      }
      
      // function used to try all possible moves from a given
      // spot on the board
      // possible moves: (x,y) (6) possible moves
      // down-right(+2,0), down-left(+2,-2),
      // right(0,+2), left(0,-2)
      // up-right(-2,+2), up-left(-2,0)
      public void checkMove(int x, int y, int pieces, int[][] board, String sequence) {
         // checking if a move is legal and making the move if it is a legal move
         // 1. first checking if there is an adjacent piece
         // 2. checking if there is an empty space behind the adjacent piece
         // 3. if there is an empty space then the adjacent piece can be jumped over and removed
         // checking if (x, y) are within bounds of board
         if(!isWithinBounds(x, y, board))
            return;
         // checking if (x, y) is occupied
         if(!isOccupied(x, y, board))
            return;
         // checking down-right
         if(isWithinBounds(x + 1, y, board) && isWithinBounds(x + 2, y, board)) {
            if(isOccupied(x + 1, y, board)) {
               // checking for empty space behind adjacent piece
               if(!isOccupied(x + 2, y, board)) {
                  move(x, y, x + 1, y, x + 2, y, pieces, board, sequence);
               }
            }
         }
         // checking down-left
         if(isWithinBounds(x + 1, y - 1, board) && isWithinBounds(x + 2, y - 2, board)) {
            if(isOccupied(x + 1, y - 1, board)) {
               // checking for empty space behind adjacent piece
               if(!isOccupied(x + 2, y - 2, board)) {
                  move(x, y, x + 1, y - 1, x + 2, y - 2, pieces, board, sequence);
               }
            }
         }
         // checking right
         if(isWithinBounds(x, y + 1, board) && isWithinBounds(x, y + 2, board)) {
            if(isOccupied(x, y + 1, board)) {
               // checking for empty space behind adjacent piece
               if(!isOccupied(x, y + 2, board)) {
                  move(x, y, x, y + 1, x, y + 2, pieces, board, sequence);
               }
            }
         }
         // checking left
         if(isWithinBounds(x, y - 1, board) && isWithinBounds(x, y - 2, board)) {
            if(isOccupied(x, y - 1, board)) {
               // checking for empty space behind adjacent piece
               if(!isOccupied(x, y - 2, board)) {
                  move(x, y, x, y - 1, x, y - 2, pieces, board, sequence);
               }
            }
         }
         // checking up-right
         if(isWithinBounds(x - 1, y + 1, board) && isWithinBounds(x - 2, y + 2, board)) {
            if(isOccupied(x - 1, y + 1, board)) {
               // checking for empty space behind adjacent piece
               if(!isOccupied(x - 2, y + 2, board)) {
                  move(x, y, x - 1, y + 1, x - 2, y + 2, pieces, board, sequence);
               }
            }
         }
         // checking up-left
         if(isWithinBounds(x - 1, y, board) && isWithinBounds(x - 2, y, board)) {
            if(isOccupied(x - 1, y, board)) {
               // checking for empty space behind adjacent piece
               if(!isOccupied(x - 2, y, board)) {
                  move(x, y, x - 1, y, x - 2, y, pieces, board, sequence);
               }
            }
         }
      }
         
      // function used to test a move on the board
      // A copy of the board is passed as a parameter and the copy is manipulated
      // rx, ry are the previous coordinates of the piece that has moved that will be marked as empty
      // rx2, ry2 are the coordinates of the adjacent piece that was jumped over and will be marked as empty
      // newX, newY are the new coordinates of the piece that has moved
      public void move(int rx, int ry, int rx2, int ry2, int newX, int newY, int pieces, int[][] board, String sequence) {
         // marking space that piece has been moved from as empty
         board[rx][ry] = 0;
         // marking adjacent piece that has been jumped over as empty
         board[rx2][ry2] = 0;
         // setting new coordinates of the moved piece as occupied
         board[newX][newY] = 1;
         // calling solve() function and passing modified board and decremented piece count into the function
         solve(board, pieces - 1, toString(board, sequence));
      }
         
      // function used to create a copy of a board
      public int[][] copy(int[][] board) {
         int[][] copy = new int[size][size];
         for(int i = 0; i < board.length; i++) {
            // length of a row
            int rowLen = size - i;
            for(int j = 0; j < rowLen; j++) {
               copy[i][j] = board[i][j];
            }
         }
         return copy;
      }
      
      // toString() method for game board winning sequence
      public String toString(int[][] board, String sequence) {
         for(int i = 0; i < size; i++) {
            // adding in space buffer for rows
            for(int k = 0; k < i; k++) {
               sequence += " ";
            }
            for(int j = 0; j < size - i; j++) {
               sequence += board[i][j] + " ";
            }
            sequence += "\n";
         }
         sequence += "\n";
         return sequence;
      }
      
      // function used to print the game board
      public void printBoard(int[][] board) {
         for(int i = 0; i < size; i++) {
            for(int j = 0; j < size - i; j++) {
               System.out.print(board[i][j]);
            }
            System.out.print("\n");
         }
      }
   }
   
   public static void main(String[] args) {
      TriangleGame game = new TriangleGame();
      System.out.println(game.play());
   }
}