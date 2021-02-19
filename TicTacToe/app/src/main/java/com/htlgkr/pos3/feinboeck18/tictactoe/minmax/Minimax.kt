package com.htlgkr.pos3.feinboeck18.tictactoe.minmax

import com.htlgkr.pos3.feinboeck18.tictactoe.game.TicTacToe

class Minimax {
    private val maxDepth = 6
    private val ticTacToe: TicTacToe = TicTacToe()

    fun getBestMiniMaxMove(currentTable: Array<Array<Int>>): IntArray {
        val positionOfTheBestMove = intArrayOf(-1, -1)
        var best: Int = Int.MIN_VALUE

        for(i in 0..2) {
            for(j in 0..2) {
                if(currentTable[i][j] == 0) {
                    currentTable[i][j] = 1
                    val valueOfThisMove = miniMax(currentTable, maxDepth, false)

                    currentTable[i][j] = 0
                    if(valueOfThisMove > best) {
                        positionOfTheBestMove[0] = i
                        positionOfTheBestMove[1] = j
                        best = valueOfThisMove
                    }
                }
            }
        }
        //PRINTING FOR DEBUGGING STUFF
        currentTable.forEach { row: Array<Int> ->
            row.forEach { currentElement: Int -> print("$currentElement ") }
            print("\n")
        }
        println("Best Move Position [" + positionOfTheBestMove[0] + "] [" + positionOfTheBestMove[1] + "]")

        return positionOfTheBestMove
    }

    private fun miniMax(currentTable: Array<Array<Int>>, currentDepth: Int, isMaximum: Boolean): Int {
        val getCurrentTableValue: Int = ticTacToe.gameOverCheck(currentTable)

        if(kotlin.math.abs(getCurrentTableValue) == 1 || currentDepth == 0 || ticTacToe.tieChecker(currentTable)) {
            return when (getCurrentTableValue) {
                1 -> getCurrentTableValue + currentDepth
                -1 -> getCurrentTableValue - currentDepth
                else -> getCurrentTableValue
            }
        }

        return when {
            isMaximum -> {
                var highestValue: Int = Int.MIN_VALUE
                for(i in 0..2) {
                    for(j in 0..2) {
                        if(currentTable[i][j] == 0) {
                            currentTable[i][j] = 1
                            highestValue = kotlin.math.max(highestValue, miniMax(currentTable, currentDepth - 1, false))
                            currentTable[i][j] = 0
                        }
                    }
                }
                highestValue
            }
            else -> {
                var lowestValue: Int = Int.MAX_VALUE
                for (i in 0..2) {
                    for (j in 0..2) {
                        if (currentTable[i][j] == 0) {
                            currentTable[i][j] = -1
                            lowestValue = kotlin.math.min(lowestValue, miniMax(currentTable, currentDepth - 1, true))
                            currentTable[i][j] = 0
                        }
                    }
                }
                lowestValue
            }
        }
    }
}