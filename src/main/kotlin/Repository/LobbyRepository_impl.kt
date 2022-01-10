package Repository

import org.jspace.FormalField
import org.jspace.RemoteSpace


class LobbyRepository_impl : LobbyRepository {

    var uri = "tcp://" + "85.204.194.92:9001" + "/lobby?keep"
    val lobby = RemoteSpace(uri)

    override suspend fun getAllAuctions() {
        lobby.queryAll(
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(Int::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java),
            FormalField(String::class.java)
        )
    }
}