package org.lesch.aws;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.FunctionCode;
import software.amazon.awssdk.services.lambda.waiters.LambdaWaiter;

public class EventBridgeProvisioner {

    void createSchedule() {
        try (EventBridgeClient client = EventBridgeClient.builder().region(Region.EU_SOUTH_1).build()) {
            PutRuleRequest ruleRequest = PutRuleRequest.builder()
                    .description("Created by using the AWS SDK for Java v2")
                    .name("test123")
                    .eventPattern("""
                            {
                              "source": ["aws.s3"],
                              "detail-type": ["Object Created"],
                              "detail": {
                                "bucket": {
                                  "name": [ "test-event-bridge-integration-with-s3" ]
                                }
                              }
                            }
                            """)
                    .build();
            PutRuleResponse putRuleResponse = client.putRule(ruleRequest);
            System.out.println(putRuleResponse);

            PutTargetsRequest targetsRequest = PutTargetsRequest.builder()
                    .eventBusName("default")
                    .rule("test123")
                    .targets(Target.builder()
                            .id("lambda-test")
                            .arn("arn:aws:lambda:eu-south-1:333687968997:function:lambda-test")
                            .build())
                    .build();
            PutTargetsResponse putTargetsResponse = client.putTargets(targetsRequest);
            System.out.println(putTargetsResponse);
        } catch (EventBridgeException e) {
            System.out.println(e.getMessage());
        }
    }
}
