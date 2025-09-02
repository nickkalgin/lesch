package org.lesch.aws;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventBridgeProvisionerTest {

    EventBridgeProvisioner ebp = new EventBridgeProvisioner();

    @Test
    void createSchedule() {
        ebp.createSchedule();
    }
}