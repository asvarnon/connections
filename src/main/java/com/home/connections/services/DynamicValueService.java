package com.home.connections.services;

import com.home.connections.dto.DynamicValue;

public interface DynamicValueService {

    DynamicValue getDynamicValue(String category, String value);
}
