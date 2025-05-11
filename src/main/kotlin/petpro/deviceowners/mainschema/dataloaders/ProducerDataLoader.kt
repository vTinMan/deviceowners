package petpro.deviceowners.mainschema.dataloaders

import petpro.deviceowners.mainschema.models.Producer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import petpro.deviceowners.core.HttpIdentifiedDataLoader

class ProducerDataLoader(client: HttpClient, override val dataLoaderName: String) : HttpIdentifiedDataLoader<Int, Producer?>(client) {
    override suspend fun search(ids: List<Int>): List<Producer?> {
        val resp = client.get("producers") {
            url {
                for (id in ids) { parameters.append("ids[]", id.toString()) }
            }
        }
        val producers: List<Producer?> = resp.body()
        return producers
    }
}
