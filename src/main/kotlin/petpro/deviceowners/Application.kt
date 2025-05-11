package petpro.deviceowners

import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.defaultGraphQLStatusPages
import com.expediagroup.graphql.server.ktor.graphQLGetRoute
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.routing
import petpro.deviceowners.core.DataLoaderClientFactory
import petpro.deviceowners.mainschema.*
import petpro.deviceowners.mainschema.dataloaders.*

fun Application.mainSchemaModule() {
    val deviceServiceUrl = environment.config.propertyOrNull("device_service.url")?.getString() ?: "http://localhost/api/v1"
    val client = DataLoaderClientFactory.build(deviceServiceUrl)

    install(GraphQL) {
        schema {
            packages = listOf("petpro.deviceowners")
            queries = listOf(
                HelloService(),
                ProducerQueryService(),
                DeviceModelQueryService(),
                OwnerQueryService(),
                DeviceQueryService(),
            )
        }
        engine {
            dataLoaderRegistryFactory = KotlinDataLoaderRegistryFactory(
                DataLoaderFactory.DEFAULT_PRODUCER_LOADER.build(client),
                DataLoaderFactory.DEFAULT_DEVICE_MODEL_LOADER.build(client),
                DataLoaderFactory.DEVICE_MODEL_LOADER_BY_PRODUCER.build(client),
                DataLoaderFactory.DEFAULT_OWNER_LOADER.build(client),
                DataLoaderFactory.DEFAULT_DEVICE_LOADER.build(client),
                DataLoaderFactory.DEVICE_LOADER_BY_OWNER.build(client)
            )
        }
    }
    install(StatusPages) {
        defaultGraphQLStatusPages()
    }
    routing {
        graphQLGetRoute()
        graphQLPostRoute()
    }
}
