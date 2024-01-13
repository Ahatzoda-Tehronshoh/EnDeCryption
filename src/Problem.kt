import kotlinx.coroutines.*

suspend fun main() {
    val coro1 = CoroutineScope(Dispatchers.IO).launch {
        launch(Dispatchers.IO) {
            println("first starts working:")
            for (i in 0..10) {
                print(i)
                //delay(10)
            }
            println("\nfirst finished!")
        }

        launch(Dispatchers.Default) {
            println("Second starts working:")
            for (j in 0..10) {
                print(j)
                delay(10)
            }
            println("\nSecond finished!")
        }
    }

    val coro2 = CoroutineScope(Dispatchers.Default).launch {
        println("Third starts working:")
        repeat(5) {
            println("Tehron Shoh")
            delay(10)
        }
        println("Third finished!")
    }

    coro1.join()
    coro2.join()
}