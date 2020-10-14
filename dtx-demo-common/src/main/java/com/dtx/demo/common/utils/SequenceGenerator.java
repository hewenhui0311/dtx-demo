package com.dtx.demo.common.utils;

import java.util.Random;

/**
 * 0|00000000000000000000000000000000000000000|0000|0000000000000000000
 * 符号位|时间戳（毫秒）|节点数|随机数
 * 符号位：1位
 * 时间戳：41位
 * 节点数：4位
 * 随机数：18位
 * @author hewenhui
 * @date 2018年4月11日
 */
public class SequenceGenerator {
	
	private final long nodeId;
	private final static long twepoch = 0L; // 基准时间
	private long sequence = 0L;
	private final static long nodeIdBits = 4L; // 机器标识位数
	public final static long maxNodeId = -1L ^ -1L << nodeIdBits; // 机器ID最大值
	private final static long sequenceBits = 18L; // 序列号识位数
	private final static long nodeIdShift = sequenceBits; // 机器ID偏左移18位
	private final static long timestampLeftShift = sequenceBits + nodeIdBits; // 时间毫秒左移22位
	public final static long sequenceMask = -1L ^ -1L << sequenceBits; // 序列号ID最大值
	private long lastTimestamp = -1L;
	
	public SequenceGenerator(final long nodeId) {
		super();
		if (nodeId > maxNodeId || nodeId < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxNodeId));
		}
		this.nodeId = nodeId;
	}
	
	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (timestamp < this.lastTimestamp) { //如果服务器时间有问题, 随机生成sequence。
			this.sequence = new Random().nextLong() & sequenceMask;
			return ((timestamp - twepoch << timestampLeftShift)) | (this.nodeId << nodeIdShift) | this.sequence;
//			throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
		}
		if (this.lastTimestamp == timestamp) { //如果上次生成时间和当前时间相同,在同一毫秒内
			 //sequence自增，因为sequence只有18bit，所以和sequenceMask相与一下，循环0-262143
			this.sequence = (this.sequence + 1) & sequenceMask;
			if (this.sequence == 0) {
				timestamp = this.tilNextMillis(this.lastTimestamp); //自旋等待到下一毫秒
			}
		} else { //如果大于上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
			this.sequence = 0;
		}
		this.lastTimestamp = timestamp;
		// 最后按照规则拼出ID。
        // 0|00000000000000000000000000000000000000000|0000|000000000000000000
		return ((timestamp - twepoch << timestampLeftShift)) | (this.nodeId << nodeIdShift) | this.sequence;
	}
	
	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}
	 
	private long timeGen() {
		return System.currentTimeMillis();
	}
}
