package com.calebstride.snake.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Class that holds the snake game state to be used in the ui. It provides simple methods to access
 * and update the game state, such as [updateDirection] and [updateGameState].
 *
 * The method [tick] is used to update the game every period of time given by [delay] in ms
 */
@Stable
class SnakeGameState {

    companion object {
        const val WIDTH = 25
        const val HEIGHT = 25
        const val SIZE = WIDTH * HEIGHT
    }

    private val _score : MutableState<Int> = mutableStateOf(0)
    val score : State<Int> = _score

    private val _gameState : MutableState<GameState> = mutableStateOf(GameState.INITIAL)
    val gameState : State<GameState> = _gameState

    /**
     * The cells have their own state so we don't need to recompose the whole grid
     */
    private val cellStates = MutableList(SIZE) { CellState() }

    private var direction = Direction.LEFT

    private var delay: Long = 500L

    private val snakeIndices = ArrayDeque<Int>()

    init {
        initStartPosition()
        createNewFood()
    }

    /**
     * Init the start position for the snake
     */
    private fun initStartPosition() {
        snakeIndices.addFirst(randomPosition())
        updateCellType(snakeIndices.first(), CellType.SNAKE)
    }

    /**
     * Get the cell state for the given x, y coordinate
     */
    fun getCellState(x: Int, y: Int): CellState {
        return cellStates[cellPosToIndex(x, y)]
    }

    /**
     * Translate a cell as x, y into the list of cells. (0, 0) is the top left position
     */
    private fun cellPosToIndex(x: Int, y: Int): Int {
        return (y * WIDTH) + x
    }

    /**
     * Update the cell for the given index.
     */
    private fun updateCellType(index: Int, cellType: CellType) {
        cellStates[index].setCellType(cellType)
    }

    /**
     * Update the direction of the snake. You can't change the direction to the opposite of the
     * current direction.
     */
    fun updateDirection(direction: Direction) {
        if (direction.isOpposite(this.direction)) return
        this.direction = direction
    }

    /**
     * Update the game state
     */
    fun updateGameState(gameState: GameState) {
        _gameState.value = gameState
    }

    /**
     * Create a random position / index in the grid
     */
    private fun randomPosition(): Int {
        return Random.nextInt(0, SIZE)
    }

    /**
     * Called every time we want to update the game. If the game isn't in a RUNNING state then
     * nothing is updated.
     *
     * This is a suspend function so it doesn't block the main thread when waiting
     */
    suspend fun tick() {
        delay(delay)
        if (_gameState.value != GameState.RUNNING) return
        // Figure out the space we're going to move to
        val nextIndex = getNextIndex(snakeIndices.first(), direction)
        val cellType = cellStates[nextIndex].cellType.value
        when(cellType) {
            // Add the snake head to this position but don't shrink the snake, also create new food
            CellType.FOOD -> {
                snakeIndices.addFirst(nextIndex)
                updateCellType(nextIndex, CellType.SNAKE)
                createNewFood()
                increaseScore()
            }
            // Add the snake head to this position and move the last snake position
            CellType.EMPTY -> {
                updateCellType(snakeIndices.removeLast(), CellType.EMPTY)
                snakeIndices.addFirst(nextIndex)
                updateCellType(nextIndex, CellType.SNAKE)
            }
            // We've hit the snake so game over
            CellType.SNAKE -> {
                _gameState.value = GameState.GAME_OVER
            }
        }
    }

    /**
     * Get the next index from the current index and the direction to travel. This makes sure that
     * we wrap the grid correctly and don't go out of bounds.
     */
    private fun getNextIndex(currentIndex: Int, direction: Direction): Int {
        return when (direction) {
            Direction.DOWN -> (currentIndex + WIDTH) % SIZE
            Direction.UP -> if (currentIndex - WIDTH < 0) currentIndex - WIDTH + SIZE else currentIndex - WIDTH
            Direction.LEFT -> moveRightOrLeft(currentIndex, currentIndex - 1, true)
            Direction.RIGHT -> moveRightOrLeft(currentIndex, currentIndex + 1, false)
        }
    }

    /**
     * Common logic for handling wrapping when moving left or right out of the grid.
     */
    private fun moveRightOrLeft(currentIndex: Int, newIndex: Int, moveLeft: Boolean): Int {
        val currentRow = currentIndex / WIDTH
        val newRow = newIndex / WIDTH
        return if (newIndex >= SIZE || newIndex < 0 || newRow != currentRow) {
            newIndex + if (moveLeft) WIDTH else -WIDTH
        } else {
            newIndex
        }
    }

    private fun increaseScore() {
        _score.value += 1 + (0.5 * snakeIndices.size).toInt()
        delay = (delay * 0.98).toLong()
    }

    /**
     * Create new food at a given random position. If the position is occupied by the snake then
     * it checks the next index. If it continues until all positions are checked then we can assume
     * the whole grid is snake, and the game is finished.
     */
    private fun createNewFood() {
        val queryIndex = randomPosition()
        for (i in queryIndex..queryIndex + SIZE) {
            if (cellStates[queryIndex % SIZE].cellType.value == CellType.EMPTY) {
                updateCellType(queryIndex, CellType.FOOD)
                return
            }
        }
        // No space was empty so we can complete the game
        _gameState.value = GameState.COMPLETE
    }

    /**
     * Restart the game and reset all values to their default
     */
    fun restartGame() {
        _score.value = 0
        delay = 500L
        for (cellState in cellStates) {
            cellState.setCellType(CellType.EMPTY)
        }
        while (snakeIndices.isNotEmpty()) {
            snakeIndices.removeLast()
        }
        initStartPosition()
        createNewFood()
        _gameState.value = GameState.RUNNING
    }
}

@Composable
fun rememberSnakeGameState(): SnakeGameState {
    return remember {
        SnakeGameState()
    }
}