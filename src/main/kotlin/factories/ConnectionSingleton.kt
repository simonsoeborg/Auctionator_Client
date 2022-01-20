package factories

import org.jspace.RemoteSpace
import java.io.IOException

object ConnectionSingleton {
    val lobby = RemoteSpace("tcp://127.0.0.1:9001/lobby?keep")
}





