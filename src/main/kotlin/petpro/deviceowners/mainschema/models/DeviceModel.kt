package petpro.deviceowners.mainschema.models

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture

@GraphQLDescription("Contains DeviceModel data including name, kind and relation to producer")
@Serializable
data class DeviceModel(
    val id: Int,
    val name: String,
    val kind: DeviceKind,
    @SerialName("producer_id") val producerId: Int? = null,
) {
    fun producer(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<Producer?> {
        return if (producerId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(DataLoaderFactory.DEFAULT_PRODUCER_LOADER.name, producerId)
        } else CompletableFuture.completedFuture(null)
    }
}