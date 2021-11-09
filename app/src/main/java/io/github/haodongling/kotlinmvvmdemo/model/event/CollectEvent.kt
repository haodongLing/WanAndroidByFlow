package io.github.haodongling.kotlinmvvmdemo.model.event

class CollectEvent(var id: Int, var collect: Boolean, var position: Int) {
    override fun toString(): String {
        return "CollectEvent(id=$id, collect=$collect,position=$position)"
    }
}