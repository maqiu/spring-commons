package com.github.damianwajser.test;

import com.github.damianwajser.configuration.RedisConfiguration;
import com.github.damianwajser.serializer.CustomJdkKeyPrefixRedisSerializer;
import com.github.damianwajser.serializer.CustomJdkRedisSerializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import redis.embedded.RedisServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SerializerTest {


	@Test
	public void serializePrefixOK() throws Exception {
		CustomJdkKeyPrefixRedisSerializer serializer = new CustomJdkKeyPrefixRedisSerializer("ms-test");
		byte[] serial = serializer.serialize("hola");
		Assert.assertEquals("hola", serializer.deserialize(serial));
	}

	@Test
	public void serializeWithoutPrefixOK() throws Exception {
		CustomJdkRedisSerializer serializer = new CustomJdkRedisSerializer();
		byte[] serial = serializer.serialize("hola");
		Assert.assertEquals("hola", serializer.deserialize(serial));
	}
}