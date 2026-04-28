package com.matjzing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.matjzing.dto.common.enumeration.RedisCd;
import com.matjzing.dto.common.CacheCom02;
import com.matjzing.dto.servicecheck.FrontServiceCheckInsertRequest;
import com.matjzing.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FrontServiceCheckService {

    @Value("${redis.timeout.com02}")
    private Long com02Timeout;

    @Value("${spring.profiles.active}")
    private String profile;

    private final RedisUtil redisUtil;

    public void insertServiceCheck(FrontServiceCheckInsertRequest req, String pw) {
        String password = RestUtil.getSwaggerPassword(profile);
        if (StringUtils.hasText(pw) && StringUtils.hasText(password) && password.equals(pw)) {
            // 시스템 점검 등록처리
            redisUtil.setValue(RedisCd.COM002.toString(), req, Duration.ofHours(com02Timeout));
        }
    }

    public CacheCom02 getServiceCheckData() {
        return redisUtil.getValue(RedisCd.COM002.name(), CacheCom02.class);
    }

    public boolean isServiceCheck() {
        CacheCom02 serviceCheckData = getServiceCheckData();
        LocalDateTime now = LocalDateTime.now();
        return !ObjectUtils.isEmpty(serviceCheckData)
                && !ObjectUtils.isEmpty(serviceCheckData.getCheckBeginDt())
                && !ObjectUtils.isEmpty(serviceCheckData.getCheckEndDt())
                && serviceCheckData.getCheckBeginDt().isBefore(now)
                && serviceCheckData.getCheckEndDt().isAfter(now);
    }

}
