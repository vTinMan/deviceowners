package petpro.deviceowners.mainschema.models

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.Serializable
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture

@GraphQLDescription("Contains Owner data including name and relation to Devices")
@Serializable
data class Owner(
    val id: Int,
    val name: String,
) {
    fun devices(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<List<Device?>> {
        return dataFetchingEnvironment.getValueFromDataLoader(DataLoaderFactory.DEVICE_LOADER_BY_OWNER.name, id)
    }
}
