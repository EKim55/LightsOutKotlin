package edu.rosehulman.kime2.lightsout

import java.util.*

class LightsOutGame {

    private var gameWon = false
    private var moves = 0
    private var lights = IntArray(7)

    init {
        resetGame()
    }

    fun resetGame() {
        gameWon = false
        lights = IntArray(7)
        randomizeButtons()
        moves = 0
    }

    fun randomizeButtons() {
        val random = Random()
        for (i in 0 until 10) {
            pressedLightAt(random.nextInt(NUM_LIGHTS - 1))
        }
        while (gameWon) {
            pressedLightAt(random.nextInt(NUM_LIGHTS - 1))
        }
    }

    fun pressedLightAt(index: Int) {
        if (gameWon) {
            return
        }

        flipState(index)
        if (index + 1 < NUM_LIGHTS) {
            flipState(index + 1)
        }
        if (index - 1 >= 0) {
            flipState(index - 1)
        }

        moves++
        gameWon = checkForWin()
    }

    fun flipState(index: Int) {
        if (lights[index] == 0) {
            lights[index] = 1
        } else {
            lights[index] = 0
        }
    }

    fun checkForWin(): Boolean {
        val winningNum = lights[0]
        for (light in lights) {
            if (light != winningNum) {
                return false
            }
        }
        return true
    }

    fun getLight(index: Int): Int {
        return lights[index]
    }

    fun getMoves(): Int {
        return moves
    }

    fun didWin(): Boolean {
        return gameWon
    }

    fun setMoves(moves: Int) {
        this.moves = moves
    }

    fun getLightList(): IntArray {
        return lights
    }

    fun setLightList(lights: IntArray) {
        this.lights = lights
    }

    fun getWin(): Boolean {
        return this.gameWon
    }

    fun setWin(didWin: Boolean) {
        this.gameWon = didWin
    }

    companion object {
        val NUM_LIGHTS = 7
        var TEXT_VIEW_KEY: String? = null
        var GAME_STATE_KEY: String? = null
        var MOVES_KEY: Int? = null
        var LIGHTS_KEY = IntArray(7)
        var didWin: Boolean? = null
    }
}