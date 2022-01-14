package factories

import org.jspace.RemoteSpace

object ConnectionSingleton {
    val rs = RemoteSpace("tcp://127.0.0.1:9001/lobby?keep")
}