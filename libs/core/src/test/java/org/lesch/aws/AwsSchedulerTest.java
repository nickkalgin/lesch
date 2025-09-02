package org.lesch.aws;

import org.junit.jupiter.api.Test;
import org.lesch.Job;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AwsSchedulerTest {

    SchedulerClient client = SchedulerClient.builder()
            .region(Region.EU_SOUTH_1)
            .build();
    String roleArn = "arn:aws:iam::333687968997:role/event-bridge-invoke-lambda";

    @Test
    void schedule() {
        AwsScheduler scheduler = new AwsScheduler(client, roleArn);

        String res = scheduler.schedule("test-sdk-schedule", new AwsJob("test-job-sdk", Job.Repeat.DAYS, 1, new AwsLambda("arn:aws:lambda:eu-south-1:333687968997:function:lambda-test")));

        assertNotNull(res);
    }

    @Test
    void list() {
        AwsScheduler scheduler = new AwsScheduler(client, roleArn);

        List<Job> list = scheduler.list();

        assertNotNull(list);
    }
}