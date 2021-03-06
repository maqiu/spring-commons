package com.github.damianwajser.test;

import com.github.damianwajser.model.CacheInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import redis.embedded.RedisServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CacheTest {

	@Autowired
	private RedisProperties redisProperties;

	private RestTemplate restTemplate = new RestTemplate();

	private RedisServer redisServer;

	@LocalServerPort
	private int port;

	@After
	public void close() {
		redisServer.stop();
	}

	@Before
	public void setUp() {
		this.redisServer = new RedisServer(redisProperties.getPort());
		redisServer.start();
	}

	@Test
	public void getCacheOK() throws Exception {
		CacheInfo result = this.restTemplate
				.exchange("http://localhost:" + port + "/actuator/caches/cache-ttl1/detail", HttpMethod.GET, null, CacheInfo.class).getBody();

		CacheInfo info = new CacheInfo();
		info.setPrerfix("ms-test::cache-ttl1::");
		info.setTtl("2 seconds");
		Assert.assertEquals(info, result);
	}

}
