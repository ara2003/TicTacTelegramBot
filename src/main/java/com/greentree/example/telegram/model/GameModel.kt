package com.greentree.example.telegram.model

import com.greentree.example.telegram.ai.CellState

interface GameModel {

	val width: Int
	val height: Int

	operator fun get(x: Int, y: Int): CellState
}

fun GameModel.taken(x: Int, y: Int): Boolean {
	return get(x, y) !== CellState.Empty
}