package petpro.deviceowners.mainschema.dataloaders

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import petpro.deviceowners.core.HttpIdentifiedDataLoader
import petpro.deviceowners.mainschema.models.Owner

class OwnerDataLoader(client: HttpClient, override val dataLoaderName: String) : HttpIdentifiedDataLoader<Int, Owner?>(client) {
    override suspend fun search(ids: List<Int>): List<Owner?> {
        val resp = client.get("owners") {
            url {
                for (id in ids) { parameters.append("ids[]", id.toString()) }
            }
        }
        val owners: List<Owner?> = resp.body()
        return owners
    }
}
