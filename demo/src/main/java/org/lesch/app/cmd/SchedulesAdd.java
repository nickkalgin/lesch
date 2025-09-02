package org.lesch.app.cmd;

import org.lesch.aws.*;
import picocli.CommandLine;
import software.amazon.awssdk.arns.Arn;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.scheduler.SchedulerClient;

import java.io.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "add", mixinStandardHelpOptions = true)
public class SchedulesAdd implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "A ZIP file to deploy.")
    private File file;

    @CommandLine.Option(names = {"-n"}, description = "Lambda name")
    private String lambdaName = "test-lambda";

    @CommandLine.Option(names = {"-s"}, description = "Schedule name")
    private String scheduleName = "test-schedule";

    @CommandLine.Option(names = {"-i"}, description = "Event Bridge role ARN to invoke Lambda")
    private String invokeRoleArn = "arn:aws:iam::333687968997:role/event-bridge-invoke-lambda";

    @CommandLine.Option(names = {"-e"}, description = "Lambda execution role ARN")
    private String execRoleArn = "arn:aws:iam::333687968997:role/TestLambdaExecutionRole";

    @CommandLine.Option(names = {"-r"}, description = "AWS Region")
    private String region;

    @CommandLine.Option(names = {"-k"}, description = "Repeat kind: once, minutes, hours, days")
    private String kind;

    @CommandLine.Option(names = {"-v"}, description = "Repeat rate: 1...")
    private int value;

    @Override
    public Integer call() throws ResourceAlreadyExists, IOException {
        LambdaClient lam = LambdaClient.builder()
                .region(region == null ? Region.EU_SOUTH_1 : Region.of(region))
                .build();

        SchedulerClient sch = SchedulerClient.builder()
                .region(region == null ? Region.EU_SOUTH_1 : Region.of(region))
                .build();

        var lambda = new AwsLambda(lam, Arn.fromString(execRoleArn));
        try (FileInputStream fis = new FileInputStream(file)) {
            Target t = lambda.deploy("test-1234", fis);
            AwsScheduler scheduler = new AwsScheduler(sch, Arn.fromString(invokeRoleArn));
            scheduler.schedule(scheduleName, new AwsJob(scheduleName, AwsJob.Repeat.valueOf(kind), value, t));
        }
        return 0;
    }
}
