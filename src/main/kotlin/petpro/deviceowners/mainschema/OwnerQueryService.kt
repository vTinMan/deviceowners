package petpro.deviceowners.mainschema

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValuesFromDataLoader
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture
import petpro.deviceowners.mainschema.models.Owner

// { "query": "{ searchOwners(params: { ids: [1, 2, 3] }) { id name devices { id modelId } } }" }
// { "query": "{ searchOwners(params: { ids: [1, 2, 3] }) { id name devices { id modelId ownerId model { id name kind { id name } producer { id title deviceModels { id name } } } } } }" }
class OwnerQueryService : Query {
    @GraphQLDescription("Return list of Owners based on OwnerSearchParameters")
    fun searchOwners(params: OwnerSearchParameters, dfe: DataFetchingEnvironment): CompletableFuture<List<Owner?>> =
        dfe.getValuesFromDataLoader(DataLoaderFactory.DEFAULT_OWNER_LOADER.name, params.ids)
}

data class OwnerSearchParameters(val ids: List<Int>)
