package petpro.deviceowners.mainschema

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.extensions.getValuesFromDataLoader
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import petpro.deviceowners.mainschema.dataloaders.DataLoaderFactory
import java.util.concurrent.CompletableFuture
import petpro.deviceowners.mainschema.models.Producer

// { "query": "{ searchProducers(params: { ids: [1, 2, 3, 4, 112233] }) { id title deviceModels { id name } } }" }
class ProducerQueryService : Query {
    @GraphQLDescription("Return list of Producers based on ProducerSearchParameters")
    fun searchProducers(params: ProducerSearchParameters, dfe: DataFetchingEnvironment): CompletableFuture<List<Producer?>> =
        dfe.getValuesFromDataLoader(DataLoaderFactory.DEFAULT_PRODUCER_LOADER.name, params.ids)
}

data class ProducerSearchParameters(val ids: List<Int>)
