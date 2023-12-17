package com.forex.homework.service.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDprovider {
    public UUID getUUUID() {
        return UUID.randomUUID();
    }
}
