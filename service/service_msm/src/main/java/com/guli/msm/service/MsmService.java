package com.guli.msm.service;

import java.util.Map;

public interface MsmService {
    boolean sendCode(String phone, Map<String, Object> param);
}
