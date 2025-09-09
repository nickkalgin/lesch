# Lambda Executions Scheduler

## Requirements

1. The AWS account exists and the default profile is configured in `$HOME/.aws/credentials`.
2. Following IAM roles and policies are configured to provide the access to invoke Lambda.

event-bridge-invoke-lambda:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": {
                "Service": "scheduler.amazonaws.com"
            },
            "Action": "sts:AssumeRole"
        }
    ]
}
```

TestLambdaExecutionRole:

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": {
                "Service": "lambda.amazonaws.com"
            },
            "Action": "sts:AssumeRole"
        }
    ]
}
```
## Usage

1. To list all the schedules:

```shell
./demo list
```

2. To add a new schedule:


```shell
./demo add handler.zip -n my-lambda -k minutes -v 20 -s my-schedule -i 'arn:aws:iam::333687968997:role/event-bridge-invoke-lambda' -e 'arn:aws:iam::333687968997:role/TestLambdaExecutionRole'
```
