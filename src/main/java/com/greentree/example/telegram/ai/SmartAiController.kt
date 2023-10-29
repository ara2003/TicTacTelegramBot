package com.greentree.example.telegram.ai

import com.greentree.example.telegram.Game
import com.greentree.example.telegram.ai.qlearn.Environment
import com.greentree.example.telegram.ai.qlearn.QLearning

class SmartAiController(private val origin: AiController) : AiController {

	private val learn = QLearning<Game, Pair<Int, Int>>()

	override fun move(api: AiInterface): Pair<Int, Int>? {
		learn.learn(object : Environment<Game, Pair<Int, Int>> {
			override fun actions(state: Game): Iterable<Pair<Int, Int>> {
				val result = mutableListOf<Pair<Int, Int>>()
				for(x in 0 ..< state.width)
					for(y in 0 ..< state.height)
						if(!state.taken(x, y))
							result.add(x to y)
				return result
			}

			override fun step(action: Pair<Int, Int>, state: Game): Game {
				val inverse = state.inverse()
				inverse[action.first, action.second] = CellState.O
				return inverse
			}

			override fun cost(state: Game): Float {
				val win = state.win
				if(win === CellState.O)
					return -20f
				if(win === CellState.X)
					return 20f
				return -1f
			}

			override fun beginState(): Game {
				return api.toGame()
			}
		})
		try {
			return learn.action(api.toGame())
		} catch(e: Exception) {
			e.printStackTrace()
		}
		return origin.move(api)
	}
}
