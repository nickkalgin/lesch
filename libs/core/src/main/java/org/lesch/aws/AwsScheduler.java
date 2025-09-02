package org.lesch.aws;

import software.amazon.awssdk.arns.Arn;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Schedules a job to execute as Lambda and reads all the schedules.
 */
public class AwsScheduler {

    private final SchedulerClient client;
    private final Arn invokeTargetRoleArn;

    public AwsScheduler(SchedulerClient client, Arn invokeTargetRoleArn) {
        Objects.requireNonNull(client, "SchedulerClient cannot be null");
        Objects.requireNonNull(invokeTargetRoleArn, "Role ARN cannot be null");

        this.client = client;
        this.invokeTargetRoleArn = invokeTargetRoleArn;
    }

    public String schedule(String name, AwsJob job) throws ResourceAlreadyExists {
        var target = software.amazon.awssdk.services.scheduler.model.Target.builder()
                .arn(job.target().id())
                .roleArn(invokeTargetRoleArn.toString())
                .build();
        var ftw = FlexibleTimeWindow.builder().mode(FlexibleTimeWindowMode.OFF).build();
        var req = CreateScheduleRequest.builder()
                    .name(name)
                .target(target)
                .flexibleTimeWindow(ftw)
                .state(ScheduleState.ENABLED)
                .scheduleExpression(job.scheduleExpression())
                .build();
        try {
            var res = client.createSchedule(req);
            return res.scheduleArn();
        } catch (ConflictException e) {
            throw new ResourceAlreadyExists(e);
        }
    }

    public List<AwsJob> schedules() {
        var req = ListSchedulesRequest.builder().build();
        var res = client.listSchedules(req);
        List<AwsJob> jobs = new ArrayList<>();
        for (var s : res.schedules()) {
            jobs.add(new AwsJob(s.arn(), s.state(), new Target(s.target().arn())));
        }
        return jobs;
    }
}
