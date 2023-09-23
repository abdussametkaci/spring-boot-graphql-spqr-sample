package com.example.springbootgraphqlspqrsample.configuration

import com.fasterxml.jackson.module.kotlin.isKotlinClass
import graphql.schema.GraphQLArgument
import graphql.schema.GraphQLFieldDefinition
import graphql.schema.GraphQLInputObjectField
import graphql.schema.GraphQLInputType
import graphql.schema.GraphQLList
import graphql.schema.GraphQLNonNull
import graphql.schema.GraphQLOutputType
import graphql.schema.GraphQLType
import io.leangen.graphql.ExtensionProvider
import io.leangen.graphql.GeneratorConfiguration
import io.leangen.graphql.generator.BuildContext
import io.leangen.graphql.generator.OperationMapper
import io.leangen.graphql.generator.mapping.SchemaTransformer
import io.leangen.graphql.metadata.InputField
import io.leangen.graphql.metadata.Operation
import io.leangen.graphql.metadata.OperationArgument
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import kotlin.reflect.KType
import kotlin.reflect.full.instanceParameter
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.jvm.kotlinFunction
import kotlin.reflect.jvm.kotlinProperty
/*
The configuration was written to generate a schema with Kotlin nullable fields compatible with graphql non-null fields.
If you do not want to use this configuration, you can use the @GraphQLNonNull annotation.
*/

@Configuration
class SchemaTransformerConfiguration {

    @Bean
    fun schemaTransformerExtensionProvider(): ExtensionProvider<GeneratorConfiguration, SchemaTransformer> {
        return ExtensionProvider<GeneratorConfiguration, SchemaTransformer> { _, _ ->
            val transformers: MutableList<SchemaTransformer> = ArrayList()

            transformers.add(object : SchemaTransformer {
                override fun transformField(
                    field: GraphQLFieldDefinition,
                    operation: Operation,
                    operationMapper: OperationMapper,
                    buildContext: BuildContext
                ): GraphQLFieldDefinition {
                    val kotlinFunction = (operation.typedElement.elements.first() as? Method)?.kotlinFunction
                    if (kotlinFunction?.instanceParameter?.type?.jvmErasure?.java?.isKotlinClass() == true) {
                        return field.transform { it.type(buildType(field.type, kotlinFunction.returnType) as GraphQLOutputType) }
                    } else {
                        val kotlinProperty = (operation.typedElement.elements.first() as? Field)?.kotlinProperty
                        if (kotlinProperty?.instanceParameter?.type?.jvmErasure?.java?.isKotlinClass() == true) {
                            return field.transform { it.type(buildType(field.type, kotlinProperty.returnType) as GraphQLOutputType) }
                        }
                    }

                    return super.transformField(field, operation, operationMapper, buildContext)
                }
            })

            transformers.add(object : SchemaTransformer {
                override fun transformArgument(
                    argument: GraphQLArgument,
                    operationArgument: OperationArgument,
                    operationMapper: OperationMapper,
                    buildContext: BuildContext
                ): GraphQLArgument {
                    val kotlinFunction = ((operationArgument.typedElement.element as? Parameter)?.declaringExecutable as? Method)?.kotlinFunction
                    if (kotlinFunction?.instanceParameter?.type?.jvmErasure?.java?.isKotlinClass() == true)
                        for (arg in kotlinFunction.parameters)
                            if (arg.name == operationArgument.name) {
                                return argument.transform { builder -> builder.type(buildType(argument.type, arg.type) as GraphQLInputType) }
                            }
                    return super.transformArgument(argument, operationArgument, operationMapper, buildContext)
                }
            })

            transformers.add(object : SchemaTransformer {
                override fun transformInputField(
                    field: GraphQLInputObjectField,
                    inputField: InputField,
                    operationMapper: OperationMapper,
                    buildContext: BuildContext
                ): GraphQLInputObjectField {
                    val kotlinFunction = (inputField.typedElement.elements.first() as? Parameter)
                        ?.declaringExecutable?.declaringClass?.constructors?.first()?.kotlinFunction
                    if (kotlinFunction != null && kotlinFunction.returnType::class.java.isKotlinClass()) {
                        val types = kotlinFunction.parameters.associateBy { it.name }
                        return field.transform { builder -> builder.type(buildType(field.type, types[field.name]?.type) as GraphQLInputType) }
                    }
                    return super.transformInputField(field, inputField, operationMapper, buildContext)
                }
            })

            transformers
        }
    }
}

fun buildType(type: GraphQLType, kType: KType?, depth: Int = 0, isNonNulls: MutableList<Boolean> = mutableListOf(false)): GraphQLType {
    return when (type) {
        is GraphQLList -> {
            if (depth == 0) isNonNulls[0] = kType?.isMarkedNullable != true
            else isNonNulls.add(kType?.isMarkedNullable != true)

            buildType(type.wrappedType, kType?.arguments?.first()?.type, depth = depth + 1, isNonNulls)
        }
        else -> {
            if (depth == 0) isNonNulls[0] = kType?.isMarkedNullable != true && type !is GraphQLNonNull
            else isNonNulls.add(kType?.isMarkedNullable != true)

            var builtType = type
            var counter = depth + 1
            while (counter > 0) {
                builtType = if (isNonNulls[counter - 1]) GraphQLNonNull(builtType) else builtType
                builtType = if (depth - counter < depth - 1) GraphQLList(builtType) else builtType
                counter -= 1
            }
            builtType
        }
    }
}
