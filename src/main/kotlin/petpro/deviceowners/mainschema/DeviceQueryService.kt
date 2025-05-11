package petpro.deviceowners.mainschema

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValuesFromDataLoader
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture
import petpro.deviceowners.mainschema.models.Device

// { "query": "{ searchDevices(params: { ids: [1, 2, 3] }) { id modelId ownerId model { id name kind { id name } producer { id title deviceModels { id name } } } } }" }
class DeviceQueryService : Query {
    @GraphQLDescription("Return list of Devices based on DeviceSearchParameters options")
    fun searchDevices(params: DeviceSearchParameters, dfe: DataFetchingEnvironment): CompletableFuture<List<Device?>> =
        dfe.getValuesFromDataLoader(DataLoaderFactory.DEFAULT_DEVICE_LOADER.name, params.ids)
}

data class DeviceSearchParameters(val ids: List<Int>)
