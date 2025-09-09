package org.lesch.aws;

import software.amazon.awssdk.arns.Arn;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.CreateFunctionRequest;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.model.ResourceConflictException;

import java.io.InputStream;
import java.util.Objects;

/**
 * Deploys JS lambda handler.
 */
public class AwsLambda {

    private final LambdaClient client;
    private final Arn executionRoleArn;

    public AwsLambda(LambdaClient client, Arn executionRoleArn) {
        Objects.requireNonNull(client, "LambdaClient cannot be null");
        Objects.requireNonNull(executionRoleArn, "Role Arn cannot be null");

        this.client = client;
        this.executionRoleArn = executionRoleArn;
    }

    public Target deploy(String name, InputStream code) throws ResourceAlreadyExists {
        var req = CreateFunctionRequest.builder()
                .functionName(name)
                .role(executionRoleArn.toString())
                .runtime("nodejs22.x")
                .handler(name + ".handler")
                .code(FunctionCode.builder().zipFile(SdkBytes.fromInputStream(code)).build())
                .build();
        try {
            var res = client.createFunction(req);
            return new Target(res.functionArn());
        } catch (ResourceConflictException e) {
            throw new ResourceAlreadyExists(e);
        }
    }
}
