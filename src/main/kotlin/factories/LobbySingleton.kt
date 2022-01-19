package factories

import repository.LobbyRepository_impl

object LobbySingleton {
    var instance = LobbyRepository_impl()
}
