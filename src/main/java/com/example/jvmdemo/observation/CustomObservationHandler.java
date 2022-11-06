package com.example.jvmdemo.observation;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;

public class CustomObservationHandler implements ObservationHandler {
    @Override
    public boolean supportsContext(Observation.Context context) {
        return false;
    }
}
