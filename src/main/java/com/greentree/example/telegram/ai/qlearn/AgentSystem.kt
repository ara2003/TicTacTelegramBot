package com.greentree.example.telegram.ai.qlearn

interface AgentSystem<S : Any, A : Any> {

	fun action(state: S): A
	fun learn(environment: Environment<S, A>)
}