package com.spacecraft.domain.spacecraft;

import java.util.UUID;

// TODO: Use uuids, allow empty constructor as well as parsing strings

public record SpacecraftId(String id) {

        public SpacecraftId() {
            this(UUID.randomUUID().toString());
        }    

        @Override
        public String toString() {
            return id;
        }
    }
