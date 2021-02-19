package com.htlgkr.pos3.feinboeck18.tictactoe.game

class TicTacToe {
    fun gameOverCheck(currentTable: Array<Array<Int>>): Int {
        var rowValue = 0
        var colValue = 0
        var diagonalValue = 0
        var antiDiagonalValue = 0

        //Row and Column Checking
        (0..2).forEach { i ->
            for(j in 0..2) {
                rowValue += currentTable[i][j]
                colValue += currentTable[j][i]
            }
            if(rowValue == 3 || colValue == 3)
                return 1
            else if(rowValue == -3 || colValue == -3)
                return -1
            else {
                rowValue = 0
                colValue = 0
            }
        }

        //Diagonal and Anti-Diagonal Checking
        for(i in 0..2) {
            diagonalValue += currentTable[i][i]
            antiDiagonalValue += currentTable[(currentTable.size-1)-i][i]
        }

        if(diagonalValue == 3 || antiDiagonalValue == 3)
            return 1
        else if(diagonalValue == -3 || antiDiagonalValue == -3)
            return -1

        return 0
    }

    fun tieChecker(currentTable: Array<Array<Int>>): Boolean {
        for(i in 0..2)
            for(j in 0..2)
                if(currentTable[i][j] == 0)
                    return false
        return true
    }
}