package petpro.deviceowners.mainschema.dataloaders

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import petpro.deviceowners.core.HttpIdentifiedDataLoader
import petpro.deviceowners.mainschema.models.Device

class DeviceDataLoader(client: HttpClient, override val dataLoaderName: String) : HttpIdentifiedDataLoader<Int, Device?>(client) {
    override suspend fun search(ids: List<Int>): List<Device?> {
        val resp = client.get("devices") {
            url {
                for (id in ids) { parameters.append("ids[]", id.toString()) }
            }
        }
        val devices: List<Device?> = resp.body()
        return devices
    }
}
