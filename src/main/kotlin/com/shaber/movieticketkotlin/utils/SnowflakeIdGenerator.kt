package com.shaber.movieticketkotlin.utils

/**
 * 雪花算法ID生成器
 */
class SnowflakeIdGenerator(
    private val dataCenterId: Long,  // 数据中心ID
    private val workerId: Long       // 工作节点ID
) {
    // 开始时间戳 (2023-01-01)
    private val startTimestamp = 1672531200000L

    // 各部分占用位数
    private val dataCenterIdBits = 5L
    private val workerIdBits = 5L
    private val sequenceBits = 12L

    // 最大值
    private val maxWorkerId = -1L xor (-1L shl workerIdBits.toInt())
    private val maxDataCenterId = -1L xor (-1L shl dataCenterIdBits.toInt())
    private val maxSequence = -1L xor (-1L shl sequenceBits.toInt())

    // 位移计算
    private val workerIdShift = sequenceBits
    private val dataCenterIdShift = sequenceBits + workerIdBits
    private val timestampShift = sequenceBits + workerIdBits + dataCenterIdBits

    private var sequence = 0L
    private var lastTimestamp = -1L

    init {
        require(dataCenterId in 0..maxDataCenterId) { "数据中心ID必须在0-${maxDataCenterId}之间" }
        require(workerId in 0..maxWorkerId) { "工作节点ID必须在0-${maxWorkerId}之间" }
    }

    @Synchronized
    fun nextId(): String {
        var timestamp = timeGen()

        // 时钟回退检查
        if (timestamp < lastTimestamp) {
            throw RuntimeException("时钟回退，拒绝生成ID")
        }

        // 同一毫秒内序列号递增
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) and maxSequence
            // 同一毫秒内序列号溢出
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0L
        }

        lastTimestamp = timestamp

        // 生成ID
        val id = ((timestamp - startTimestamp) shl timestampShift.toInt()) or
                (dataCenterId shl dataCenterIdShift.toInt()) or
                (workerId shl workerIdShift.toInt()) or
                sequence

        return id.toString()
    }

    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = timeGen()
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen()
        }
        return timestamp
    }

    private fun timeGen(): Long = System.currentTimeMillis()
}
