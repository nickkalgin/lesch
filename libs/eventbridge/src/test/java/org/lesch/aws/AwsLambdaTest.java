package org.lesch.aws;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.arns.Arn;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
class AwsLambdaTest {

    Arn roleArn = Arn.fromString("arn:aws:iam::333687968997:role/TestLambdaExecutionRole");

    LambdaClient client = LambdaClient.builder()
            .region(Region.EU_SOUTH_1)
            .build();

    @Test
    void deploy() throws ResourceAlreadyExists, IOException {
        var lambda = new AwsLambda(client, roleArn);
        var resource = this.getClass().getClassLoader().getResourceAsStream("handler.js");
        var out = new ByteArrayOutputStream();
        try (var zos = new ZipOutputStream(out)) {
            zos.putNextEntry(new ZipEntry("handler.js"));
            zos.write(resource.readAllBytes());
        }
        Target t = lambda.deploy("test-1234", new ByteArrayInputStream(out.toByteArray()));

        assertNotNull(t);
    }
}