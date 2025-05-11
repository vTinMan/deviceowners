package petpro.deviceowners.mainschema.dataloaders

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import petpro.deviceowners.core.HttpIdentifiedDataLoader
import petpro.deviceowners.mainschema.models.Device

class DeviceDataLoaderByOwner(client: HttpClient, override val dataLoaderName: String) : HttpIdentifiedDataLoader<Int, List<Device>>(client) {
    override suspend fun search(ids: List<Int>): List<List<Device>> {
        val resp = client.get("devices") {
            url {
                for (id in ids) { parameters.append("owner_id[]", id.toString()) }
            }
        }
        val deviceModels: List<List<Device>> = resp.body()
        return deviceModels
    }
}
