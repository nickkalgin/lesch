package org.lesch.app.cmd;

import org.lesch.aws.AwsScheduler;
import picocli.CommandLine;
import software.amazon.awssdk.arns.Arn;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.scheduler.SchedulerClient;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "list", mixinStandardHelpOptions = true)
public class SchedulesList implements Callable<Integer> {

    @CommandLine.Option(names = {"--invoke-role-arn"}, description = "Event Bridge role ARN to invoke Lambda")
    private String roleArn = "arn:aws:iam::333687968997:role/event-bridge-invoke-lambda";

    @CommandLine.Option(names = {"-r"}, description = "AWS Region")
    private String region;

    @Override
    public Integer call() {
        SchedulerClient sch = SchedulerClient.builder()
                .region(region == null ? Region.EU_SOUTH_1 : Region.of(region))
                .build();
        AwsScheduler scheduler = new AwsScheduler(sch, Arn.fromString(roleArn));
        scheduler.schedules().forEach(j -> System.out.println(j.toString()));
        return 0;
    }
}
