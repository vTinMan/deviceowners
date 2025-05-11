package petpro.deviceowners.mainschema.models

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.Serializable
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture

@GraphQLDescription("Contains Producer data including title and relation to DeviceModels")
@Serializable
data class Producer(
    val id: Int,
    val title: String
) {
    fun deviceModels(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<DeviceModel?>> {
        return dataFetchingEnvironment.getValueFromDataLoader(DataLoaderFactory.DEVICE_MODEL_LOADER_BY_PRODUCER.name, id)
    }
}
