package org.lesch.aws;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.arns.Arn;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class AwsSchedulerTest {

    SchedulerClient client = SchedulerClient.builder()
            .region(Region.EU_SOUTH_1)
            .build();
    Arn roleArn = Arn.fromString("arn:aws:iam::333687968997:role/event-bridge-invoke-lambda");

    @Test
    void schedule() throws ResourceAlreadyExists {
        AwsScheduler scheduler = new AwsScheduler(client, roleArn);

        String res = scheduler.schedule(
                "test-sdk-schedule",
                new AwsJob("test-job-sdk", AwsJob.Repeat.DAYS, 1, new Target("arn:aws:lambda:eu-south-1:333687968997:function:lambda-test")));

        assertNotNull(res);
    }

    @Test
    void schedules() {
        AwsScheduler scheduler = new AwsScheduler(client, roleArn);

        List<AwsJob> list = scheduler.schedules();

        assertNotNull(list);
    }
}