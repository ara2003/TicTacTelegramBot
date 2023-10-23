package com.greentree.example.telegram.ai

interface AiController {

	fun move(api: AiInterface?): Pair<Int, Int>?
}
