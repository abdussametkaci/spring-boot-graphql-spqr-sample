package com.example.springbootgraphqlspqrsample.configuration

import io.leangen.graphql.metadata.messages.MessageBundle
import io.leangen.graphql.metadata.strategy.type.DefaultTypeInfoGenerator
import org.springframework.context.annotation.Configuration
import java.lang.reflect.AnnotatedType

@Configuration
class InputTypeNameGeneratorConfiguration : DefaultTypeInfoGenerator() {

    override fun generateInputTypeName(type: AnnotatedType?, messageBundle: MessageBundle?): String {
        return this.generateTypeName(type, messageBundle)
    }
}

/*
The DefaultTypeInfoGenerator#generateInputTypeName method has been overridden to prevent the input suffix from being added.
Original Code: return generateTypeName(type, messageBundle) + INPUT_SUFFIX;
Also, SCALAR_SUFFIX is added for scalar. If you don't want this to happen, you need to override DefaultTypeInfoGenerator#generateScalarTypeName.
*/
