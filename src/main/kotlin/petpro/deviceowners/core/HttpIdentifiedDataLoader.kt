package petpro.deviceowners.core

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import graphql.GraphQLContext
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.dataloader.DataLoaderFactory
import java.util.concurrent.CompletableFuture

abstract class HttpDataLoader<K, V>(val client: HttpClient) : KotlinDataLoader<K, V>

abstract class HttpIdentifiedDataLoader<K, V>(client: HttpClient) : HttpDataLoader<K, V>(client) {
    public open override fun getDataLoader(graphQLContext: GraphQLContext): DataLoader<K, V> =
        DataLoaderFactory.newDataLoader { ids ->
            CoroutineScope(Dispatchers.IO + Job()).future {
                search(ids).toMutableList()
            }
        }

    abstract suspend fun search(ids: List<K>): List<V>
}
