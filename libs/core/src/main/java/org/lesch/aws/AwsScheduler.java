package org.lesch.aws;

import org.lesch.Job;
import org.lesch.Scheduler;
import software.amazon.awssdk.services.scheduler.SchedulerClient;
import software.amazon.awssdk.services.scheduler.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AwsScheduler implements Scheduler {

    private final SchedulerClient client;
    private final String invokeTargetRoleArn;

    public AwsScheduler(SchedulerClient client, String invokeTargetRoleArn) {
        Objects.requireNonNull(client, "SchedulerClient cannot be null");
        Objects.requireNonNull(invokeTargetRoleArn, "Role ARN cannot be null");

        this.client = client;
        this.invokeTargetRoleArn = invokeTargetRoleArn;
    }

    @Override
    public String schedule(String name, Job job) {
        var target = Target.builder()
                .arn(job.lambda().id())
                .roleArn(invokeTargetRoleArn)
                .build();
        var ftw = FlexibleTimeWindow.builder().mode(FlexibleTimeWindowMode.OFF).build();
        var req = CreateScheduleRequest.builder()
                    .name(name)
                .target(target)
                .flexibleTimeWindow(ftw)
                .state(ScheduleState.ENABLED)
                .scheduleExpression(job.scheduleExpression());
        try {
            var res = client.createSchedule(req.build());
            return res.scheduleArn();
        } catch (ConflictException e) {
            var res = client.updateSchedule(u -> u
                    .name(name)
                    .target(target)
                    .flexibleTimeWindow(ftw)
                    .state(ScheduleState.ENABLED)
                    .scheduleExpression(job.scheduleExpression())
            );
            return res.scheduleArn();
        }
    }

    @Override
    public List<Job> list() {
        var req = ListSchedulesRequest.builder().build();
        var res = client.listSchedules(req);
        List<Job> jobs = new ArrayList<>();
        for (var s : res.schedules()) {
            jobs.add(new AwsJob(s.arn(), s.state(), new AwsLambda(s.target().arn())));
        }
        return jobs;
    }
}
