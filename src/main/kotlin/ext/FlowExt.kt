package ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

/* Extension function used to transform List<T>.Entity to List<T>.Model - Credit to Cyrus for helping out with this one */
inline fun <T, R> Flow<Iterable<T>>.mapIterable(crossinline trans: suspend (value: T) -> R): Flow<List<R>> = transform { iterable ->
    return@transform emit(iterable.map { trans(it) })
}