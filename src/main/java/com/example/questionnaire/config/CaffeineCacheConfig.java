package com.example.questionnaire.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;


//這整個class是教緩存 暫存
@EnableCaching
@Configuration
public class CaffeineCacheConfig {
	
	@Bean
	public CacheManager cacheManger() {
		CaffeineCacheManager cacheManger = new CaffeineCacheManager();
		cacheManger.setCaffeine(Caffeine.newBuilder()
//				設定過期時間 1.最後一次寫入 或  2.訪問後就固定一段時間
				.expireAfterAccess(600,TimeUnit.SECONDS)
//				初始化緩存空間大小
				.initialCapacity(100)
				//緩存最大筆數
				.maximumSize(500));
		return cacheManger;
	}

}
