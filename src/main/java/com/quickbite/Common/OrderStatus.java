package com.quickbite.Common;

import java.util.Map;
import java.util.Set;

/**
 * Real-world order state machine.
 * Prevents invalid jumps like PLACED -> DELIVERED.
 */
public final class OrderStatus {

    public static final String PLACED = "PLACED";
    public static final String PREPARING = "PREPARING";
    public static final String OUT_FOR_DELIVERY = "OUT_FOR_DELIVERY";
    public static final String DELIVERED = "DELIVERED";
    public static final String CANCELLED = "CANCELLED";

    private static final Set<String> ALL = Set.of(
            PLACED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED);

    private static final Map<String, Set<String>> ALLOWED = Map.of(
            PLACED, Set.of(PREPARING, CANCELLED),
            PREPARING, Set.of(OUT_FOR_DELIVERY, CANCELLED),
            OUT_FOR_DELIVERY, Set.of(DELIVERED, CANCELLED),
            DELIVERED, Set.of(),
            CANCELLED, Set.of());

    private OrderStatus() {
    }

    public static boolean isValid(String status) {
        return status != null && ALL.contains(status);
    }

    public static boolean canTransition(String from, String to) {
        if (!isValid(from) || !isValid(to)) {
            return false;
        }
        if (from.equals(to)) {
            return true;
        }
        return ALLOWED.getOrDefault(from, Set.of()).contains(to);
    }
}
