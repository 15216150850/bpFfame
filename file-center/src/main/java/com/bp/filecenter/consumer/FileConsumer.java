package com.bp.filecenter.consumer;


import com.bp.common.interface_.FileQueue;
import com.bp.filecenter.service.FileStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 从mq队列消费文件uuid数据
 * @author xcj
 *
 */
@Component
@RabbitListener(queues = FileQueue.FILE_QUEUE) // 监听队列
public class FileConsumer {

	private static final Logger logger = LoggerFactory.getLogger(FileConsumer.class);

	@Resource
	private FileStoreService fileStoreService;

	/**
	 * 文件uuid
	 * 
	 * @param uuid
	 */
	@RabbitHandler
	public void fileHandler(String uuid) {
		try {
			logger.info("修改文件:"+uuid);
			fileStoreService.updateFileStatus(uuid);
		} catch (Exception e) {
			logger.error("mq修改文件为有效状态异常:", uuid, e);
		}

	}
}
