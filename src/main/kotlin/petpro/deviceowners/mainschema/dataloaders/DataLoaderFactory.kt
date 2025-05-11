package petpro.deviceowners.mainschema.dataloaders

import io.ktor.client.*
import petpro.deviceowners.core.HttpIdentifiedDataLoader
import petpro.deviceowners.mainschema.models.Device
import petpro.deviceowners.mainschema.models.DeviceModel
import petpro.deviceowners.mainschema.models.Owner
import petpro.deviceowners.mainschema.models.Producer

enum class DataLoaderFactory {
    DEFAULT_OWNER_LOADER {
        override fun build(client: HttpClient): HttpIdentifiedDataLoader<Int, Owner?> =
            OwnerDataLoader(client, name)
    },
    DEFAULT_PRODUCER_LOADER {
        override fun build(client: HttpClient): HttpIdentifiedDataLoader<Int, Producer?> =
            ProducerDataLoader(client, name)
    },
    DEFAULT_DEVICE_LOADER {
        override fun build(client: HttpClient): HttpIdentifiedDataLoader<Int, Device?> =
            DeviceDataLoader(client, name)
    },
    DEVICE_LOADER_BY_OWNER {
        override fun build(client: HttpClient): HttpIdentifiedDataLoader<Int, List<Device>> =
            DeviceDataLoaderByOwner(client, name)
    },
    DEFAULT_DEVICE_MODEL_LOADER {
        override fun build(client: HttpClient): HttpIdentifiedDataLoader<Int, DeviceModel?> =
            DeviceModelDataLoader(client, name)
    },
    DEVICE_MODEL_LOADER_BY_PRODUCER {
        override fun build(client: HttpClient): HttpIdentifiedDataLoader<out Number, *> =
            DeviceModelDataLoaderByProducer(client, name)

    };

    abstract fun build(client: HttpClient): HttpIdentifiedDataLoader<out Number, *>
}
