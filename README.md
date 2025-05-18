# 🧩 Sudoku Generator - Melhorias no Projeto DIO

Este repositório contém aprimoramentos para o projeto original **Sudoku** da Digital Innovation One: [https://github.com/digitalinnovationone/sudoku](https://github.com/digitalinnovationone/sudoku)

Agora, em vez de usar uma grade fixa, a cada inicialização um novo tabuleiro é gerado e preparado para jogo, mantendo sempre uma solução única.

## 🧠 Objetivo

* Gerar tabuleiros completos de Sudoku
* Remover números para criar desafios com solução única
* Substituir o tabuleiro estático original por um gerador dinâmico

## 📄 Descrição do Projeto

A classe principal `SudokuGenerator` provê dois métodos:

1. `generateFullBoard()`

   * Constrói um tabuleiro completo e válido usando algoritmo de *backtracking*.
   * Garante que cada linha, coluna e região 3×3 contenha números de 1 a 9 sem repetição.

2. `removeNumbers(int clues)`

   * Remove aleatoriamente células do tabuleiro completo até sobrar o número de "pistas" (clues) desejado.
   * Mantém a condição de solução única, restaurando valores se a unicidade for violada.

O fluxo de uso típico é:

```java
SudokuGenerator generator = new SudokuGenerator();
// Gera grade completa
generator.generateFullBoard();
// Remove números, mantendo 30 pistas, por exemplo
generator.removeNumbers(30);
int[][] board = generator.getBoard();
// board agora pode ser exibido ou utilizado pelo jogo Sudoku existente
```


