package petpro.deviceowners.mainschema

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValuesFromDataLoader
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture
import petpro.deviceowners.mainschema.models.DeviceModel

// { "query": "{ searchDeviceModels(params: { ids: [1, 2, 333, 3, 4, 22] }) { id name producerId kind { name } } }" }
// { "query": "{ searchDeviceModels(params: { ids: [1, 2] }) { id name producerId kind { id name } producer { id title deviceModels { id name } } } }" }
class DeviceModelQueryService : Query {
    @GraphQLDescription("Return list of Device Models based on DeviceModelSearchParameters")
    fun searchDeviceModels(params: DeviceModelSearchParameters, dfe: DataFetchingEnvironment): CompletableFuture<List<DeviceModel?>> =
        dfe.getValuesFromDataLoader(DataLoaderFactory.DEFAULT_DEVICE_MODEL_LOADER.name, params.ids)
}

data class DeviceModelSearchParameters(val ids: List<Int>)
