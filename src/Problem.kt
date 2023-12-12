import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

suspend fun main() {
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    val flow = flow<Int> {
        val list = listOf(1, 2, 3, 4, 5)
        for(i in list) {
            print("Printing $i number: ")
            emit(i)
        }
    }

    coroutineScope.launch {
        flow.collect {
            println(it)
        }
    }.join()
}