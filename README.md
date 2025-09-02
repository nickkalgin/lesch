# Lambda Executions Scheduler

## Requirements

The AWS admin account exists and the default profile is configured in `$HOME/.aws/credentials`.

## Usage

Download a distributions.zip from https://github.com/nickkalgin/lesch/packages/2633513 and extract.

1. To list all the schedules:

```shell
./demo list
```

2. To add a new schedule:


```shell
./demo add handler.zip -n my-lambda -k minutes -v 20 -s my-schedule -i 'arn:aws:iam::333687968997:role/event-bridge-invoke-lambda' -e 'arn:aws:iam::333687968997:role/TestLambdaExecutionRole'
```
