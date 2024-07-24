package com.calebstride.snake.game

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    fun isOpposite(direction: Direction): Boolean {
        return (this == UP && direction == DOWN) || (this == DOWN && direction == UP) ||
                (this == LEFT && direction == RIGHT) || (this == RIGHT && direction == LEFT)
    }
}