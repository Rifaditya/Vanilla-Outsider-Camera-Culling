// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.vanillaoutsider.culling.config;

import java.util.Locale;

public enum CullingLevel {
    LOW(
        "Low (Conservative)",
        25.0,
        7,
        0.4,
        false,
        false,
        false
    ),
    MEDIUM(
        "Medium (Balanced)",
        12.25,
        5,
        0.2,
        true,
        false,
        false
    ),
    HIGH(
        "High (Aggressive)",
        9.0,
        3,
        0.1,
        true,
        true,
        false
    ),
    SUPER(
        "Super (Extreme)",
        4.0,
        2,
        0.0,
        true,
        true,
        false
    );

    private final String displayName;
    private final double minDistanceSq;
    private final int samplePoints;
    private final double padding;
    private final boolean cullAllBlockEntities;
    private final boolean cullDecorativeEntities;
    private final boolean defaultCullEntitiesBehindEntities;

    CullingLevel(
        String displayName,
        double minDistanceSq,
        int samplePoints,
        double padding,
        boolean cullAllBlockEntities,
        boolean cullDecorativeEntities,
        boolean defaultCullEntitiesBehindEntities
    ) {
        this.displayName = displayName;
        this.minDistanceSq = minDistanceSq;
        this.samplePoints = samplePoints;
        this.padding = padding;
        this.cullAllBlockEntities = cullAllBlockEntities;
        this.cullDecorativeEntities = cullDecorativeEntities;
        this.defaultCullEntitiesBehindEntities = defaultCullEntitiesBehindEntities;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getMinDistanceSq() {
        return minDistanceSq;
    }

    public int getSamplePoints() {
        return samplePoints;
    }

    public double getPadding() {
        return padding;
    }

    public boolean shouldCullAllBlockEntities() {
        return cullAllBlockEntities;
    }

    public boolean shouldCullDecorativeEntities() {
        return cullDecorativeEntities;
    }

    public boolean isDefaultCullEntitiesBehindEntities() {
        return defaultCullEntitiesBehindEntities;
    }

    public static CullingLevel fromString(String name) {
        if (name == null) return MEDIUM;
        try {
            return CullingLevel.valueOf(name.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return MEDIUM;
        }
    }
}
