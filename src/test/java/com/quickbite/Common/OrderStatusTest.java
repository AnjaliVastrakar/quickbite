package com.quickbite.Common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OrderStatusTest {

    @Test
    void validTransitionsAreAllowed() {
        assertTrue(OrderStatus.canTransition("PLACED", "PREPARING"));
        assertTrue(OrderStatus.canTransition("PREPARING", "OUT_FOR_DELIVERY"));
        assertTrue(OrderStatus.canTransition("OUT_FOR_DELIVERY", "DELIVERED"));
        assertTrue(OrderStatus.canTransition("PLACED", "CANCELLED"));
    }

    @Test
    void invalidJumpsAreRejected() {
        assertFalse(OrderStatus.canTransition("PLACED", "DELIVERED"));
        assertFalse(OrderStatus.canTransition("DELIVERED", "PREPARING"));
        assertFalse(OrderStatus.canTransition("CANCELLED", "PLACED"));
        assertFalse(OrderStatus.canTransition("PLACED", "UNKNOWN"));
    }
}
