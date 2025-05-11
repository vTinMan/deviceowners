package petpro.deviceowners.mainschema.models

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import kotlinx.serialization.Serializable

@GraphQLDescription("Contains Kind data including name")
@Serializable
data class DeviceKind(
    val id: Int,
    val name: String,
)