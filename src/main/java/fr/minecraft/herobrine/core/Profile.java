package fr.minecraft.herobrine.core;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public enum Profile {
    DEV("dev"),
    PROD("prod");

    private final String name;

    Profile(String name) {
        this.name = name;
    }

    public static Profile fromString(@NotNull String name) {
        Objects.requireNonNull(name);

        for (Profile prof : values()) {
            if (name.equalsIgnoreCase(prof.toString())) {
                return prof;
            }
        }

        return PROD;
    }

    @Override
    public String toString() {
        return name;
    }
}
