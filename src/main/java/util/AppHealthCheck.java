package util;


import com.codahale.metrics.health.HealthCheck;

public class AppHealthCheck extends HealthCheck
{

    protected Result check() throws Exception
    {

        return Result.healthy();
    }

}