package com.example.questionnaire.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;


//�o���class�O�нw�s �Ȧs
@EnableCaching
@Configuration
public class CaffeineCacheConfig {
	
	@Bean
	public CacheManager cacheManger() {
		CaffeineCacheManager cacheManger = new CaffeineCacheManager();
		cacheManger.setCaffeine(Caffeine.newBuilder()
//				�]�w�L���ɶ� 1.�̫�@���g�J ��  2.�X�ݫ�N�T�w�@�q�ɶ�
				.expireAfterAccess(600,TimeUnit.SECONDS)
//				��l�ƽw�s�Ŷ��j�p
				.initialCapacity(100)
				//�w�s�̤j����
				.maximumSize(500));
		return cacheManger;
	}

}
