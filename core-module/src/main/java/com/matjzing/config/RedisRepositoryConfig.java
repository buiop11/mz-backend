package com.matjzing.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.matjzing.dto.common.RedisCacheKeyGenerator;
import io.lettuce.core.ClientOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
@Profile("!local")
public class RedisRepositoryConfig implements CachingConfigurer {

	@Value("${spring.data.redis.host}")
	private String redisHost;

	@Value("${spring.data.redis.port}")
	private int redisPort;

	@Value("${spring.profiles.active}")
	private String profile;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
				.clientOptions(
						ClientOptions.builder()
								.autoReconnect(true)
								.pingBeforeActivateConnection(true)
								.build()
				).build();

		if ("prod".equals(profile)) {
			RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
			redisConfig.clusterNode(redisHost, redisPort);
			return new LettuceConnectionFactory(redisConfig, clientConfig);
		} else {
			RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
			redisConfig.setHostName(redisHost);
			redisConfig.setPort(redisPort);
			return new LettuceConnectionFactory(redisConfig, clientConfig);
		}
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		template.setKeySerializer(new StringRedisSerializer());
		template.setEnableDefaultSerializer(true);
		return template;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
		stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory());
		return stringRedisTemplate;
	}

	public static final ObjectMapper REDIS_OBJECT_MAPPER = new ObjectMapper()
			.findAndRegisterModules()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
			.activateDefaultTyping(new ObjectMapper().getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY)
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);

	@Bean
	public CacheManager cacheManager() {
		return getCacheManager(Duration.ofMinutes(30));
	}

	/**
	 * 커스텀 keyGenerator 생성
	 *
	 * @return Redis에 캐싱할 Key
	 */
	@Bean
	public KeyGenerator redisCacheKeyGenerator() {
		return new RedisCacheKeyGenerator();
	}

	@Bean(name = "cacheManager1seconds")
	public CacheManager cacheManager1seconds() {
		return this.getCacheManager(Duration.ofSeconds(1));
	}

	@Bean(name = "cacheManager5seconds")
	public CacheManager cacheManager5seconds() {
		return this.getCacheManager(Duration.ofSeconds(5));
	}

	@Bean(name = "cacheManager10seconds")
	public CacheManager cacheManager10seconds() {
		return this.getCacheManager(Duration.ofSeconds(10));
	}

	@Bean(name = "cacheManager30seconds")
	public CacheManager cacheManager30seconds() {
		return this.getCacheManager(Duration.ofSeconds(30));
	}

	@Bean(name = "cacheManager1minutes")
	public CacheManager cacheManager1minutes() {
		return this.getCacheManager(Duration.ofMinutes(1));
	}

	@Bean(name = "cacheManager5minutes")
	public CacheManager cacheManager5minutes() {
		return this.getCacheManager(Duration.ofMinutes(5));
	}

	@Bean(name = "cacheManager10minutes")
	public CacheManager cacheManager10minutes() {
		return this.getCacheManager(Duration.ofMinutes(10));
	}

	@Bean(name = "cacheManager30minutes")
	public CacheManager cacheManager30minutes() {
		return this.getCacheManager(Duration.ofMinutes(30));
	}

	@Bean(name = "cacheManager1Hour")
	public CacheManager cacheManager1Hour() {
		return this.getCacheManager(Duration.ofHours(1));
	}

	@Bean(name = "cacheManager5Hour")
	public CacheManager cacheManager5Hour() {
		return this.getCacheManager(Duration.ofHours(5));
	}

	@Bean(name = "cacheManager10Hour")
	public CacheManager cacheManager10Hour() {
		return this.getCacheManager(Duration.ofHours(10));
	}

	@Bean(name = "cacheManager30Hour")
	public CacheManager cacheManager30Hour() {
		return this.getCacheManager(Duration.ofHours(30));
	}

	@Bean(name = "cacheManager1Days")
	public CacheManager cacheManager1Days() {
		return this.getCacheManager(Duration.ofDays(1));
	}

	@Bean(name = "cacheManager5Days")
	public CacheManager cacheManager5Days() {
		return this.getCacheManager(Duration.ofDays(5));
	}

	@Bean(name = "cacheManager10Days")
	public CacheManager cacheManager10Days() {
		return this.getCacheManager(Duration.ofDays(10));
	}

	@Bean(name = "cacheManager30Days")
	public CacheManager cacheManager30Days() {
		return this.getCacheManager(Duration.ofDays(30));
	}

	/**
	 * CacheManager 생성용 함수
	 * @param duration <br>
	 * - 10초 캐싱 : Duration.ofSeconds(10)<br>
	 * - 10분 캐싱 : Duration.ofMinutes(10)<br>
	 * - 10일 캐싱 : Duration.ofDays(10)<br>
	 * @return
	 */
	private CacheManager getCacheManager(Duration duration) {
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
				.RedisCacheManagerBuilder
				.fromConnectionFactory(redisConnectionFactory());

		// 값은 json 문자열로 넣는다. @class 필드로 클래스 정보가 들어간다.
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair
								.fromSerializer(new GenericJackson2JsonRedisSerializer(REDIS_OBJECT_MAPPER)))
				.entryTtl(duration);

		builder.cacheDefaults(defaultConfig);
		return builder.build();
	}

}
