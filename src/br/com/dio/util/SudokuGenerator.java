package br.com.dio.util;

import java.util.*;

public class SudokuGenerator {
    private int[][] board;
    private final int SIZE = 9;
    private final int EMPTY = 0;
    private final int SUBGRID = 3;

    public SudokuGenerator() {
        board = new int[SIZE][SIZE];
    }

    // Gera um tabuleiro completo utilizando backtracking
    public void generateFullBoard() {
        fillBoard();
    }

    private boolean fillBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == EMPTY) {
                    List<Integer> numbers = getShuffledNumbers();
                    for (int number : numbers) {
                        if (isSafe(row, col, number)) {
                            board[row][col] = number;
                            if (fillBoard()) {
                                return true;
                            } else {
                                board[row][col] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Retorna os números de 1 a 9 em ordem aleatória
    private List<Integer> getShuffledNumbers() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers;
    }

    // Verifica se é seguro colocar 'num' na posição (row, col)
    private boolean isSafe(int row, int col, int num) {
        // Checa a linha e coluna
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        // Checa o bloco 3x3
        int startRow = row - row % SUBGRID;
        int startCol = col - col % SUBGRID;
        for (int i = 0; i < SUBGRID; i++) {
            for (int j = 0; j < SUBGRID; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Remove números do tabuleiro completo, criando espaços a ser preenchidos
    public void removeNumbers(int clues) {
        int cellsToRemove = SIZE * SIZE - clues;
        Random rand = new Random();
        while (cellsToRemove > 0) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            if (board[row][col] != EMPTY) {
                int backup = board[row][col];
                board[row][col] = EMPTY;
                if (!hasUniqueSolution()) {
                    board[row][col] = backup; // Restaura se não houver solução única
                } else {
                    cellsToRemove--;
                }
            }
        }
    }

    // Verifica se o tabuleiro atual possui solução única
    private boolean hasUniqueSolution() {
        int[][] copy = getBoard();
        return countSolutions(copy, 0) == 1;
    }

    // Conta o número de soluções possíveis (interrompe se encontrar mais de uma)
    private int countSolutions(int[][] boardCopy, int count) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (boardCopy[row][col] == EMPTY) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isSafeForBoard(boardCopy, row, col, num)) {
                            boardCopy[row][col] = num;
                            count = countSolutions(boardCopy, count);
                            if (count > 1) return count;
                            boardCopy[row][col] = EMPTY;
                        }
                    }
                    return count;
                }
            }
        }
        return count + 1;
    }

    // Verifica se é seguro colocar 'num' na posição (row, col)
    private boolean isSafeForBoard(int[][] boardCopy, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (boardCopy[row][i] == num || boardCopy[i][col] == num) {
                return false;
            }
        }
        int startRow = row - row % SUBGRID;
        int startCol = col - col % SUBGRID;
        for (int i = 0; i < SUBGRID; i++) {
            for (int j = 0; j < SUBGRID; j++) {
                if (boardCopy[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }


    public int[][] getBoard() {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            copy[i] = board[i].clone(); // Clonando cada linha individualmente
        }
        return copy;
    }
}
