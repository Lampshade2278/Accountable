package com.accountable.core;

import java.util.HashMap;
import java.util.Map;

public class Settings {
    private Map<String, String> settings;

    public Settings() {
        this.settings = new HashMap<>();
    }

    public Settings(Map<String, String> settings) {
        this.settings = settings;
    }
}