package com.greentree.example.telegram.ai

import com.greentree.example.telegram.Game

private val GameInfo.move
	get() = Pair(x, y)
private val Game.children
	get() = run {
		val result = mutableListOf<GameInfo>()
		for(x in 0 ..< width)
			for(y in 0 ..< height)
				if(!taken(x, y)) {
					val inv = inverse()
					inv[x, y] = CellState.O
					result.add(GameInfo(x, y, inv))
				}
		result
	}

private data class GameInfo(val x: Int, val y: Int, val game: Game)

class TreeSmartAiController : AiController {

	override fun move(api: AiInterface): Pair<Int, Int> {
		return bestMove(api.toGame())
	}

	private fun bestMove(game: Game) = bestChild(game).move

	private fun bestChild(game: Game) = childCache.getOrPut(game) {
		val children = game.children
		children.maxBy { winRate(it.game) }
	}

	private fun pow(x: Int, p: Int): Int = run {
		if(p == 0)
			1
		else
			if(p % 2 == 0) {
				val a = pow(x, p / 2)
				a * a
			} else {
				val a = pow(x, (p - 1) / 2)
				a * a * x
			}
	}

	private fun winRate(game: Game): Float = winRateCache.getOrPut(game) {
		when(game.win) {
			CellState.X -> 0f
			CellState.O -> 1f
			CellState.Empty -> 0.1f
			else -> (1f - winRate(bestChild(game).game)) * .9f
		}
	}

	companion object {

		private val childCache = mutableMapOf<Game, GameInfo>()
		private val winRateCache = mutableMapOf<Game, Float>()
	}
}
