package org.lesch.app;

import org.lesch.app.cmd.SchedulesAdd;
import org.lesch.app.cmd.SchedulesList;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(mixinStandardHelpOptions = true, subcommands = {SchedulesList.class, SchedulesAdd.class})
public class Application {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }
}
