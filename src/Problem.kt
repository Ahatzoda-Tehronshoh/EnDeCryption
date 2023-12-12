import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.transform

suspend fun main() {
    var flow = (1..15).asFlow()
    val flow1 = (16..30).asFlow()
    flow = flow.combine(flow1) { elem1, elem2 ->
        elem1 + elem2
    }

    val flow3 = flow.transform<Int, String> {
        emit("$it - ?")
    }

    flow3.collect{
        println(it)
    }
}