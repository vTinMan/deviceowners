package petpro.deviceowners.mainschema.dataloaders

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import petpro.deviceowners.mainschema.models.DeviceModel
import petpro.deviceowners.core.HttpIdentifiedDataLoader

class DeviceModelDataLoader(client: HttpClient, override val dataLoaderName: String) : HttpIdentifiedDataLoader<Int, DeviceModel?>(client) {
    override suspend fun search(ids: List<Int>): List<DeviceModel?> {
        val resp = client.get("device_models") {
            url {
                for (id in ids) { parameters.append("ids[]", id.toString()) }
            }
        }
        val deviceModels: List<DeviceModel?> = resp.body()
        return deviceModels
    }
}
