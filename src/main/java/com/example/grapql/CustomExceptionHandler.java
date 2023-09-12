package com.example.grapql;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        return switch (ex) {
            case ObjectNotFoundException e -> GraphqlErrorBuilder.newError(env)
                    .errorType(ErrorClassification.errorClassification("500357"))
                    .message("object not found")
                    .build();
            default -> null;
        };
    }
}
