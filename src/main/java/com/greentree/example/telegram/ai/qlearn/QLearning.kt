package com.greentree.example.telegram.ai.qlearn

import java.lang.Math.*
import java.util.*
import kotlin.collections.set

private val random = Random()

private interface Activator<S, A> {

	val state: S
	fun action(action: A): Float
	fun actions(s: S = state): Iterable<A>
}

private class EnvironmentActivator<S : Any, A : Any>(private val environment: Environment<S, A>) : Activator<S, A> {

	var alpha = 1
	override var state = environment.beginState()

	override fun actions(s: S) = environment.actions(state)

	override fun action(action: A): Float {
		alpha = -alpha
		state = environment.step(action, state)
		return alpha * environment.cost(state)
	}
}

private fun <T> println(vararg args: T) {
	val j = StringJoiner(", ")
	for(arg in args)
		j.add(arg.toString())
	kotlin.io.println(j)
}

private fun <T> randomElement(actions: Iterable<T>): T {
	val actions = actions.toList()
	return actions[random.nextInt(actions.size)]
}

private const val STEP = 1_00_000

class QLearning<S : Any, A : Any>(val alpha: Float = .9f) : AgentSystem<S, A> {

	private val q = mutableMapOf<S, MutableMap<A, Float>>()

	override fun action(state: S): A {
//		for((k, v) in q) {
//			println(v.maxBy { it.value }.key)
//			println(k)
//		}
		println(q.getOrPut(state) {
			mutableMapOf()
		})
		return q.getOrPut(state) {
			mutableMapOf()
		}.maxBy { it.value }.key
	}

	override fun learn(e: Environment<S, A>) {
		repeat(STEP) { i ->
//			println("${((i + 1) * 100.0) / STEP}%")
			var activator = EnvironmentActivator(e)
			var s = activator.state
			var a = randomElement(activator.actions())
			var r = activator.action(a)
			while(true) {
				val sp = s
				val ap = a
				s = activator.state
				val actions = activator.actions(s)
				if(!actions.iterator().hasNext())
					break
				init(activator, s)
				init(activator, sp)
				q.getOrPut(sp) {
					mutableMapOf()
				}[ap] = r + alpha * q.getOrPut(s) {
					mutableMapOf()
				}.values.max()
				a = actions.maxBy {
					q.getOrPut(s) {
						mutableMapOf()
					}[it]!!
				}
				r = activator.action(a)
			}
		}
	}

	private fun init(activator: EnvironmentActivator<S, A>, s: S) {
		q.getOrPut(s) {
			mutableMapOf<A, Float>().also {
				for(a in activator.actions(s))
					it[a] = ((random() * 2 - 1) * .1).toFloat()
			}
		}
	}
}