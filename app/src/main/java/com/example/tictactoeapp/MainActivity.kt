package com.example.tictactoeapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoeapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Button>
    private lateinit var statusText: TextView
    private lateinit var playAgainButton: Button
    private lateinit var trophyIcon: ImageView
    private var currentPlayer = "X"
    private val board = Array(9) { "" }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8)
        )

        statusText = findViewById(R.id.statusText)
        playAgainButton = findViewById(R.id.playAgainButton)
        trophyIcon = findViewById(R.id.trophyIcon)

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                onButtonClick(index)
            }
        }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onButtonClick(index: Int) {
        if (board[index].isEmpty() && gameActive) {
            board[index] = currentPlayer
            buttons[index].text = currentPlayer

            if (checkWinner()) {
                gameActive = false
                statusText.text = "Player $currentPlayer wins!"
                playAgainButton.visibility = View.VISIBLE
                trophyIcon.visibility = View.VISIBLE
            } else if (board.none { it.isEmpty() }) {
                gameActive = false
                statusText.text = "It's a draw!"
                playAgainButton.visibility = View.VISIBLE
                trophyIcon.visibility = View.GONE
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                statusText.text = "Player $currentPlayer's turn"
            }
        }
    }

    private fun checkWinner(): Boolean {
        val winningCombinations = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), // Rows
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), // Columns
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6) // Diagonals
        )

        for (combination in winningCombinations) {
            if (board[combination[0]] == board[combination[1]] &&
                board[combination[1]] == board[combination[2]] &&
                board[combination[0]].isNotEmpty()
            ) {
                return true
            }
        }
        return false
    }

    private fun resetGame() {
        board.fill("")
        buttons.forEach { button ->
            button.text = ""
        }
        currentPlayer = "X"
        gameActive = true
        statusText.text = "Player X's turn"
        playAgainButton.visibility = View.GONE
        trophyIcon.visibility = View.GONE
    }
}