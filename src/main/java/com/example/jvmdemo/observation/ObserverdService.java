package com.example.jvmdemo.observation;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ObserverdService {

    private ObservationRegistry registry;

    public ObserverdService(final ObservationRegistry registryParam) {
        registry = registryParam;
    }

    // Example of using an annotation to observe methods
    // <user.name> will be used as a metric name
    // <getting-user-name> will be used as a span  name
    // <userType=userType2> will be set as a tag for both metric & span
    @Observed(name = "user.name",
            contextualName = "getting-user-name",
            lowCardinalityKeyValues = {"userType", "userType2"})
    String userName(String userId) {
        log.info("Getting user name for user with id <{}>", userId);
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "foo";
    }
}
