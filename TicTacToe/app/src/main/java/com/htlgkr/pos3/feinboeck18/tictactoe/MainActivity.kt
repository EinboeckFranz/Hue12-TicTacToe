package com.htlgkr.pos3.feinboeck18.tictactoe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import com.htlgkr.pos3.feinboeck18.tictactoe.game.Player
import com.htlgkr.pos3.feinboeck18.tictactoe.game.TicTacToe
import com.htlgkr.pos3.feinboeck18.tictactoe.minmax.Minimax

//FOUND MOST OF THIS CODE ON STACK OVERFLOW ... in Java ( -> whole Project translated and changed)
class MainActivity : AppCompatActivity() {
    private var ticTacToeInstance: TicTacToe = TicTacToe()
    private var currentPlayer: Player = Player.O
    private var emptyFieldsCount: Int = 9

    private lateinit var freeSpacesTextView: TextView
    private lateinit var playerTextView: TextView
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get those TextViews, ...
        freeSpacesTextView = findViewById(R.id.freeSpaces)
        playerTextView = findViewById(R.id.playerShow)
        tableLayout = findViewById(R.id.table)

        reloadEmptyFields()
        resetPlayer()
    }

    //GAME LOGIC
    fun setButtonText(view: View) {
        if(currentPlayer == Player.O) {
            val pressedButton: Button = view as AppCompatButton
            if(pressedButton.text.toString() == "") {
                pressedButton.text = currentPlayer.toString()
                startNextRound()
            }
        }
    }

    private fun startNextRound() {
        emptyFieldsCount--
        reloadEmptyFields()
        val currentTable: Array<Array<Int>> = playFieldToIntArray(tableLayout)

        val hasWinner: Int = ticTacToeInstance.gameOverCheck(currentTable)
        when {
            hasWinner == 1 -> {
                endGame(Player.O)
                return
            }
            hasWinner == -1 -> {
                endGame(Player.X)
                return
            }
            ticTacToeInstance.tieChecker(currentTable) -> {
                endGame(Player.EMPTY)
                return
            }
        }

        this.currentPlayer = if(this.currentPlayer == Player.X) Player.O else Player.X
        resetPlayer()

         if(this.currentPlayer == Player.X) {
             val minimax = Minimax()
             minimaxDoMove(minimax.getBestMiniMaxMove(currentTable))
         }
    }

    @Suppress("UNCHECKED_CAST")
    private fun minimaxDoMove(bestMiniMaxMove: IntArray) {
        val rows: List<TableRow> = this.tableLayout.children.toList() as List<TableRow>
        val buttons: List<AppCompatButton> = rows[bestMiniMaxMove[0]].children.toList() as List<AppCompatButton>
        buttons[bestMiniMaxMove[1]].text = currentPlayer.toString()
        startNextRound()
    }

    private fun resetGame() {
        this.currentPlayer = Player.O
        this.emptyFieldsCount = 9

        resetPlayer()
        reloadEmptyFields()

        resetGameTable()
    }

    //Layout STUFF
    @SuppressLint("SetTextI18n")
    private fun resetPlayer() {
        playerTextView.text = "Player: $currentPlayer"
    }

    private fun reloadEmptyFields() {
        freeSpacesTextView.text = "$emptyFieldsCount"
    }

    @Suppress("UNCHECKED_CAST")
    private fun resetGameTable() {
        val table: List<TableRow> = tableLayout.children.toList() as List<TableRow>

        for (row in table) {
            val buttons = row.children.toList() as List<AppCompatButton>
            for(button in buttons)
                button.text = ""
        }
    }

    //HELPER
    @Suppress("UNCHECKED_CAST")
    private fun playFieldToIntArray(playField: TableLayout): Array<Array<Int>> {
        val row: List<TableRow> = playField.children.toList() as List<TableRow>
        val tempButtonArray: Array<Array<Int>> = Array(row.count()) {Array(0) {0}}

        for((rowIndex, currentRow) in row.withIndex()) {
            val buttonsOfRow: List<AppCompatButton> = currentRow.children.toList() as List<AppCompatButton>
            tempButtonArray[rowIndex] = Array(buttonsOfRow.size) {0}

            for((columnIndex, button) in buttonsOfRow.withIndex()) {
                when(button.text.toString()) {
                    "X" -> tempButtonArray[rowIndex][columnIndex] = 1
                    "O" -> tempButtonArray[rowIndex][columnIndex] = -1
                }
            }
        }
        return tempButtonArray
    }

    private fun endGame(winningPlayer: Player) {
        if(winningPlayer == Player.EMPTY)
            showToast("TIE!")
        else
            showToast("${Player.X} won!")
        resetGame()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}