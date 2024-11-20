package com.sharkxkd.ticket.entity;

import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import com.sharkxkd.ticket.config.BloomFilterProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class RedisBloomFilter {
 
	/**
	 * 	二进制位大小（多少位）
	 */
	private Long numBits ;
	/**
	 * 	hash函数个数
	 */
	private Integer numHashFunctions ;
	
	private Funnel<CharSequence> funnel = Funnels.stringFunnel(Charset.forName("UTF-8")) ;
 
	@Resource
	private StringRedisTemplate stringRedisTemplate ;
	@Resource
	private BloomFilterProperties prop ;
	
	@PostConstruct
	public void initRedisBloomFilter() {
		this.numBits = optimalNumOfBits(prop.getExpectedInsertions(), prop.getFpp()) ;
		this.numHashFunctions = optimalNumOfHashFunctions(prop.getExpectedInsertions(), numBits) ;
	}
	
	/**
	 * 最佳的位数（二进制位数）
	 * @param expectedInsertions 预期插入的数量
	 * @param fpp 错误率大于0，小于1.0
	 * @return long
	 */
	public long optimalNumOfBits(long expectedInsertions, double fpp) {
		if (fpp == 0) {
			fpp = Double.MIN_VALUE;
		}
		return (long) (-expectedInsertions * Math.log(fpp) / (Math.log(2) * Math.log(2)));
	}
	
	/**
	 * 最佳的Hash函数个数
	 * @param expectedInsertions 预期插入的数量
	 * @param numBits 根据optimalNumOfBits方法计算的最佳二进制位数
	 * @return int
	 */
	public static int optimalNumOfHashFunctions(long expectedInsertions, long numBits) {
		return Math.max(1, (int) Math.round((double) numBits / expectedInsertions * Math.log(2)));
	}

	/**
	 * 将数据存入对应的bitmap里
	 * @param key	redis对应的bitMap的key
	 * @param value	待判断的值
	 * @return		判断结果
	 */
	public boolean put(String key, String value) {
		byte[] bytes = Hashing.murmur3_128().hashObject(value, funnel).asBytes();
		long hash1 = lowerEight(bytes);
		long hash2 = upperEight(bytes);
 
		boolean bitsChanged = false;
		long combinedHash = hash1;
		for (int i = 0; i < numHashFunctions; i++) {
			long bit = (combinedHash & Long.MAX_VALUE) % numBits ;
			// 这里设置对应的bit为1
			stringRedisTemplate.opsForValue().setBit(key, bit, true) ;
			combinedHash += hash2;
		}
		return bitsChanged;
	}

	/**
	 * 判断数据是否存在
	 * @param key	redis对应的bitMap的key
	 * @param value	待判断的值
	 * @return		判断结果
	 */
	public boolean mightContain(String key, String value) {
		byte[] bytes = Hashing.murmur3_128().hashObject(value, funnel).asBytes();
		long hash1 = lowerEight(bytes);
		long hash2 = upperEight(bytes);
 
		long combinedHash = hash1;
		for (int i = 0; i < numHashFunctions; i++) {
			long bit = (combinedHash & Long.MAX_VALUE) % numBits ;
			// 这里判断redis中对应位是否为1
			if (!stringRedisTemplate.opsForValue().getBit(key, bit)) {
				return false;
			}
			combinedHash += hash2;
		}
		return true;
	}
 
	private long lowerEight(byte[] bytes) {
		return Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
	}
 
	private long upperEight(byte[] bytes) {
		return Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
	}
 
}