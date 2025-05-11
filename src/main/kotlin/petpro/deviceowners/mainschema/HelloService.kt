package petpro.deviceowners.mainschema

import com.expediagroup.graphql.server.operations.Query

class HelloService : Query {
    // GET ?query={hello}
    // POST { "query": "{ hello }" }
    fun hello() = "World!"

    // POST { "query": "{ hello2(name: \"World\") }" }
    fun hello2(name: String) = name
}
