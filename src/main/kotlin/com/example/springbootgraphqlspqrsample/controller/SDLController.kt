package com.example.springbootgraphqlspqrsample.controller

import graphql.schema.GraphQLSchema
import graphql.schema.idl.SchemaPrinter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sdl")
class SDLController(private val graphQLSchema: GraphQLSchema) {

    @GetMapping(produces = ["text/plain"])
    fun getSDL(): String {
        return SchemaPrinter(
            SchemaPrinter.Options.defaultOptions()
                .includeDirectives(false)
                .descriptionsAsHashComments(true)
        ).print(graphQLSchema)
    }
}
