package com.greentree.example.telegram.ai.qlearn

interface Environment<S : Any, A : Any> {

	fun actions(state: S): Iterable<A>
	fun step(action: A, state: S): S
	fun cost(state: S): Float

	fun beginState(): S
}