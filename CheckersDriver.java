import java.util.*;

public class CheckersDriver {
   
   public static int[][] board = new int[8][8];

   public static void main(String[] args) {
   
      game();
   
   }
   
   public static void game() {
      
      CPU cpu = new CPU();
      int[] cpuMove = new int[2];
      
      //Filling the board with 1s and 2s
      for(int i = 0; i < 8 ; i++) {
         board[(i + 1) % 2][i] = 2;
         board[5 - 3 * (i % 2)][i] = 1 + (i % 2);
         board[7 - (i % 2)][i] = 1;
      }
      
      //Input GUI for main menu
      
      Scanner input = new Scanner(System.in);
      System.out.println("Please enter your game choice:");
      System.out.println("1 - PvP");
      System.out.println("2 - PvCPU playing as piece 1");
      System.out.println("3 - PvCPU playing as piece 2");
      int userChoice = input.nextInt();
      
      //Open the GUI for the board
      
      if(userChoice == 1) {
         
         printBoard();
         while(true) {
            
            //Prior to a player making a move, the computer determines if a move can me made
            if(checkMove(1) == false) {
               //if 1 can't move, 2 wins
               gameOver(2);
               break;
            }
            System.out.println("PLAYER 1'S TURN");
            playerTurn(1);
            printBoard();
            
            if(checkMove(2) == false) {
               //if 2 can't move, 1 wins
               gameOver(1);
               break;
            }
            System.out.println("PLAYER 2'S TURN");
            playerTurn(2);
            printBoard();
            
         }
         
      }
      
      if(userChoice == 2) {
      
         printBoard();
         while(true) {
         
            if(checkMove(1) == false) {
               gameOver(2);
               break;
            }
            System.out.println("PLAYER 1'S TURN");
            playerTurn(1);
            printBoard();
            
            
            if(checkMove(2) == false) {
               gameOver(1);
               break;
            }
            System.out.println("PLAYER 2'S TURN");
            cpuMove = cpu.makeMove(board, 2);
            board[cpuMove[1] / 8][cpuMove[1] % 8] = 2;
            board[cpuMove[0] / 8][cpuMove[0] % 8] = 0;
            if(Math.abs(cpuMove[1] - cpuMove[0]) >= 14) {
               board[((cpuMove[0] + cpuMove[1]) / 2) / 8][((cpuMove[0] + cpuMove[1]) / 2) % 8] = 0;
            }
            
            printBoard();
            
         }
         
      }
      
      if(userChoice == 3) {
         
         printBoard();
         while(true) {
         
            if(checkMove(1) == false) {
               gameOver(2);
               break;
            }
            System.out.println("PLAYER 1'S TURN");
            cpuMove = cpu.makeMove(board, 1);
            board[cpuMove[1] / 8][cpuMove[1] % 8] = 1;
            board[cpuMove[0] / 8][cpuMove[0] % 8] = 0;
            if(Math.abs(cpuMove[1] - cpuMove[0]) >= 14) {
               board[((cpuMove[0] + cpuMove[1]) / 2) / 8][((cpuMove[0] + cpuMove[1]) / 2) % 8] = 0;
            }
            printBoard();
            
            
            if(checkMove(2) == false) {
               gameOver(1);
               break;
            }
            System.out.println("PLAYER 2'S TURN");
            playerTurn(2);
            
            printBoard();
            
         }
         
      }

      input.close();

   }
   
   public static void playerTurn(int pieceType) {
      
      Scanner input = new Scanner(System.in);
      
      //New GUI: the user can now click on the appropriate pieces, they light up
      //and nearby squares (where the piece can move to) also light up.
      //The user can deselect a piece by clicking it again
      
      boolean validClick1, validClick2, reselect;
      int firstClick, fR, fC, lastClick, lR, lC;
      
      do {
         
         reselect = false;
      
         do {
         
            validClick1 = true;

            System.out.print("firstClick = ");
            firstClick = input.nextInt();     
         
            //Depending on what the value is, the coordinates of the corresponding slot in the
            //board array can be calculated
            //fR = firstRow, fC = firstCol
            fR = firstClick / 8;
            fC = firstClick % 8;
      
            //If this is called, the user did not click on their piece
            if(board[fR][fC] != pieceType) {
               //Make user fill firstClick again
               validClick1 = false;
            }
         
         } while(validClick1 == false);
      
         //Once the user selects a piece, the computer then needs to calculate what spots will
         //light up (where the piece can move to)
      
         //These integers will hold the values of the boxes that the selected piece can move to
         //If they can't move, their values are -1
         int moveLeft = checkMoveLeft(firstClick, pieceType);
         int moveRight = checkMoveRight(firstClick, pieceType);
           
         do {
         
            validClick2 = true;
         
            System.out.print("lastClick = ");
            lastClick = input.nextInt();
              
            if(lastClick == firstClick) {
               reselect = true;
               break;
            }
            
            //If this is called, the user did not select a square that the 
            //highlighted piece can move to
            if(lastClick != moveLeft && lastClick != moveRight) {
               //Make the user fill lastClick again
               validClick2 = false;
            }
         
         } while(validClick2 == false);
      
      //If the user clicks the same square twice, the piece deselects and we go back to firstClick
      } while(reselect == true);
 
      lR = lastClick / 8;
      lC = lastClick % 8;
      
      //Updating the board array to match what the user clicked
      board[lR][lC] = board[fR][fC];
      board[fR][fC] = 0;
      //if the piece moved 2 spots, then it hoped an opponent
      if(fR - lR == 2 || fR - lR == -2) {
         board[(fR + lR) / 2][(fC + lC) / 2] = 0;
      }
      
   }
   
   public static int checkMoveLeft(int firstClick, int pieceType) {
      
      int fR = firstClick / 8;
      int fC = firstClick % 8;
      
      //CHECKING LEFT
      //Checking if it can move one spot to the left
      if(fC > 0 && (-7) * (pieceType - 1) < (int)Math.pow(-1, pieceType + 1) * fR) {
         if(board[fR + (int)Math.pow(-1, pieceType)][fC - 1] == 0) {
            return 8 * (fR + (int)Math.pow(-1, pieceType)) + fC - 1;
         }
      }
      //Checking if it can hop a piece to the left
      if(fC > 1 && (-7) * (pieceType - 1) < (int)Math.pow(-1, pieceType + 1) * fR - 1) {
         if(board[fR + (int)Math.pow(-1, pieceType)][fC - 1] == (pieceType % 2) + 1 &&
            board[fR + 2 * (int)Math.pow(-1, pieceType)][fC - 2] == 0) {
            return 8 * (fR + 2 * (int)Math.pow(-1, pieceType)) + fC - 2;
         }
      }
      //if it can't do either, it can't move left
      return -1;
   
   }
   
   public static int checkMoveRight(int firstClick, int pieceType) {
      
      int fR = firstClick / 8;
      int fC = firstClick % 8;
      
      //CHECKING RIGHT
      //Checking if it can move one spot to the right
      if(fC < 7 && (-7) * (pieceType - 1) < (int)Math.pow(-1, pieceType + 1) * fR) {
         if(board[fR + (int)Math.pow(-1, pieceType)][fC + 1] == 0) {
            return 8 * (fR + (int)Math.pow(-1, pieceType)) + fC + 1;
         }
      }
      //Checking if it can hop a piece to the right
      if(fC < 6 && (-7) * (pieceType - 1) < (int)Math.pow(-1, pieceType + 1) * fR - 1) {
         if(board[fR + (int)Math.pow(-1, pieceType)][fC + 1] == (pieceType % 2) + 1 && 
            board[fR + 2 * (int)Math.pow(-1, pieceType)][fC + 2] == 0) {
            return 8 * (fR + 2 * (int)Math.pow(-1, pieceType)) + fC + 2;
         }
      }
      //if it can't do either, it can't move right
      return -1;
   
   }
   
   //This method determines if a particular piece type (white/red) can move
   public static boolean checkMove(int pieceType) {
      
      int leftMove, rightMove;

      for(int i = 0; i < 8; i++) {
         for(int j = (i + 1) % 2; j < 8; j += 2) {
            if(board[i][j] == pieceType) {
               leftMove = checkMoveLeft(8 * i + j, pieceType);
               rightMove = checkMoveRight(8 * i + j, pieceType);
               if(leftMove != -1 || rightMove != -1) {
                  return true;
               }
            }
         }
      }
      
      //If we have gone through the entire array, and no valid move has been found, the pieceType
      //cannot move
      return false;
 
   }
   
   //This is method is called once a pieceType can no longer move
   //The opposing piece wins the game
   public static void gameOver(int pieceType) {
      
      System.out.println("Congratulations! Piece " + pieceType + " wins!");
   
   }
   
   //Method for testing
   public static void printBoard() {
      for(int i = 0; i < 8; i++) {
         for(int j = 0; j < 8; j++) {
            System.out.print(board[i][j] + " ");
         }
         System.out.println();
      }
   }
   
}