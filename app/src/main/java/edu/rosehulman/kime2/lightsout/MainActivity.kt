package edu.rosehulman.kime2.lightsout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var game = LightsOutGame()
    var lightList = arrayOfNulls<Button>(LightsOutGame.NUM_LIGHTS)
    var gameState: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameState = savedInstanceState?.getString(LightsOutGame.GAME_STATE_KEY)

        setContentView(R.layout.activity_main)

        for (light in 0 until LightsOutGame.NUM_LIGHTS) {
            var id = resources.getIdentifier("button$light", "id", packageName)
            lightList[light] = findViewById(id)
            lightList[light]?.setOnClickListener {
                game.pressedLightAt(light)
                updateView()
            }
        }
        game.resetGame()
        updateView()

        new_game_button.setOnClickListener {
            game.resetGame()
            updateView()
        }

    }

    public override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putString(LightsOutGame.GAME_STATE_KEY, gameState)
            putString(LightsOutGame.TEXT_VIEW_KEY, textView.text.toString())
            LightsOutGame.MOVES_KEY = game.getMoves()
            LightsOutGame.LIGHTS_KEY = game.getLightList()
            LightsOutGame.didWin = game.getWin()
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        textView.text = savedInstanceState?.getString(LightsOutGame.TEXT_VIEW_KEY)
        game.setMoves(LightsOutGame.MOVES_KEY!!)
        game.setLightList(LightsOutGame.LIGHTS_KEY)
        game.setWin(LightsOutGame.didWin!!)
        updateView()
    }

    fun updateView() {
        if (game.didWin()) {
            textView.text = getString(R.string.won_message)
        } else if (game.getMoves() == 0) {
            textView.text = getString(R.string.beginning_text)
        } else if (game.getMoves() == 1) {
            textView.text = "You have taken 1 turn"
        } else {
            val moves = game.getMoves()
            textView.text = "You have taken $moves turns"
        }

        for (light in 0 until LightsOutGame.NUM_LIGHTS) {
            lightList[light]?.text = game.getLight(light).toString()
        }
    }
}
