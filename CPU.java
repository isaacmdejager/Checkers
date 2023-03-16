import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class CPU {
   
   public static List<int[]> bestMoves;
   public static int[] listA;
   public static int[] listB;
   
   public static int[] makeMove(int[][] board, int pieceType) {
      
      bestMoves = new ArrayList<int[]>();
      listA = new int[5];
      listB = new int[5];

      int leftMove, rightMove;
      
      for(int i = 0; i < 8; i++) {
      
         for(int j = (i + 1) % 2; j < 8; j += 2) {
         
            if(board[i][j] == pieceType) {
               
               //Checking left
               leftMove = checkMoveLeft(i, j, pieceType, board);
               if(leftMove != - 1) {
                  checkList(i, j, leftMove, pieceType, board);
                  compareMoves(i, j, leftMove);
               }
               
               //Checking right
               rightMove = checkMoveRight(i, j, pieceType, board);
               if(rightMove != -1) {
                  checkList(i, j, rightMove, pieceType, board);
                  compareMoves(i, j, rightMove);
               }
            
            }
         
         }
         
      }
 
      Random r = new Random();
      return bestMoves.get(r.nextInt(bestMoves.size()));
      
   }
   
   public static int checkMoveLeft(int fR, int fC, int pieceType, int[][] board) {
      
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
   
   public static int checkMoveRight(int fR, int fC, int pieceType, int[][] board) {
      
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
   
   public static void checkList(int fR, int fC, int loc2, int pieceType, int[][] board) {
   
      int lR = loc2 / 8;
      int lC = loc2 % 8;
      
      //Does it hop a piece?
      if(Math.abs(fR - lR) == 2) {
         listB[0] = 1;
      }
      

      //Did it move to a safe place?
      //Checking if its on a corner
      if(lR == 0 || lR == 7 || lC == 0 || lC == 7) {
         listB[1] = 1;
      }
      //Checking if the adjacent squares are empty or are blocked
      else {
         
         if(
         //CHECKING RIGHT
         //Checking if the spot to the right not an enemy
         (board[lR + (int)Math.pow(-1, pieceType)][lC + 1] != (pieceType % 2) + 1 ||
         //If that spot is an enemy and there is another piece on the other side (not you) you're safe
         (board[lR - (int)Math.pow(-1, pieceType)][lC - 1] != 0) && lC - fC < 0) &&
         
         //CHECKING LEFT
         (board[lR + (int)Math.pow(-1, pieceType)][lC - 1] != (pieceType % 2) + 1 ||
         (board[lR - (int)Math.pow(-1, pieceType)][lC + 1] != 0 && lC - fC > 0))) {
         
            listB[1] = 1;
            
         }
         
      }
      
      
      //Did it protect a piece?
      if(
         //CHECKING RIGHT
         ((int)Math.pow(-1, pieceType + 1) * lR > (-7) * (pieceType - 1) + 1 && lC < 6 &&
         board[lR + (int)Math.pow(-1, pieceType)][lC + 1] == pieceType &&
         board[lR + 2 * (int)Math.pow(-1, pieceType)][lC + 2] == (pieceType % 2) + 1) ||
         
         //CHECKING LEFT
         ((int)Math.pow(-1, pieceType + 1) * lR > (-7) * (pieceType - 1) + 1 && lC > 1 &&
         board[lR + (int)Math.pow(-1, pieceType)][lC - 1] == pieceType &&
         board[lR + 2 * (int)Math.pow(-1, pieceType)][lC - 2] == (pieceType % 2) + 1)) {
         
         listB[2] = 1;
            
      }
      
      
      //Did it move out of danger?
      if(fC != 0 && fC != 7 && fR != 0 && fR != 7) {
         if(
            //CHECKING RIGHT
            (board[fR + (int)Math.pow(-1, pieceType)][fC + 1] == (pieceType % 2) + 1 &&
            board[fR - (int)Math.pow(-1, pieceType)][fC - 1] == 0) ||
            //CHECKING LEFT
            (board[fR + (int)Math.pow(-1, pieceType)][fC - 1] == (pieceType % 2) + 1 &&
            board[fR - (int)Math.pow(-1, pieceType)][fC + 1] == 0)) {
         
            listB[3] = 1;
            
         }
      
      }
      
      
      //Did it move to a spot where it can jump a piece next turn?
      //CHECKING RIGHT
      if(lC < 6 && (int)Math.pow(-1, pieceType + 1) * lR > (-7) * (pieceType - 1) + 1) {
         if(board[lR + (int)Math.pow(-1, pieceType)][lC + 1] == (pieceType % 2) + 1 &&
            board[lR + 2 * (int)Math.pow(-1, pieceType)][lC + 2] == 0) {
            listB[3] = 1;
         }
      }
      //CHECKING LEFT
      if(lC > 1 && (int)Math.pow(-1, pieceType + 1) * lR > (-7) * (pieceType - 1) + 1) {
         if(board[lR + (int)Math.pow(-1, pieceType)][lC - 1] == (pieceType % 2) + 1 &&
            board[lR + 2 * (int)Math.pow(-1, pieceType)][lC - 2] == 0) {
            listB[4] = 1;
         }
      }

   }
   
   public static void compareMoves(int fR, int fC, int loc2) {
      
      int loc1 = 8 * fR + fC;
      int[] move = {loc1, loc2};
      int count = 0;
      
      for(int i = 0; i < 5; i++) {
      
         if(listB[i] > listA[i]) {
         
            bestMoves.clear();
            bestMoves.add(move);
            for(int j = 0; j < 5; j++) {
               listA[j] = listB[j];
            }
            break;
         
         }
      
         else if(listB[i] == listA[i]) {
            count++;
         }
         
         else {
            break;
         }
         
      }
         
      if(count == 5) {
         bestMoves.add(move);
      }
      
      for(int i = 0; i < 5; i++) {
         listB[i] = 0;
      }

   }
   
}