package petpro.deviceowners.mainschema.models

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import graphql.schema.DataFetchingEnvironment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture

@GraphQLDescription("Contains Device data including relations to Model and Owner")
@Serializable
data class Device(
    val id: Int,
    @SerialName("model_id") val modelId: Int? = null,
    @SerialName("owner_id") val ownerId: Int? = null
) {
    fun model(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<DeviceModel?> {
        return if (modelId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(DataLoaderFactory.DEFAULT_DEVICE_MODEL_LOADER.name, modelId)
        } else CompletableFuture.completedFuture(null)
    }

    fun owner(dataFetchingEnvironment: DataFetchingEnvironment): CompletableFuture<Owner?> {
        return if (ownerId != null) {
            dataFetchingEnvironment.getValueFromDataLoader(DataLoaderFactory.DEFAULT_OWNER_LOADER.name, ownerId)
        } else CompletableFuture.completedFuture(null)
    }
}
