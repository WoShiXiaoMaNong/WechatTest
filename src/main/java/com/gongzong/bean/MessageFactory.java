package com.gongzong.bean;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class MessageFactory {
	private static final Logger logger = Logger.getLogger(MessageFactory.class);
	
	
	
	public static WechatMessage buildMessageBean(String xml ) {
		WechatMessage message = WechatMessage.EMPTY_MESSAGE;
		logger.info("Start to build message bean!");
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			message = convertToWechatMessage(root);
			logger.info("build message end.Message is " + message.convertToXml());
		} catch (DocumentException e) {
			logger.error(e.getMessage() + "\r\n error when parsText ,xml is: " + xml);
		}
		return message;
	}
	
	
	private static WechatMessage convertToWechatMessage(Element root) {
		WechatMessage message =  WechatMessage.EMPTY_MESSAGE;
		logger.info("Start to convert message.");
		Element elementMsgType = root.element("MsgType");
		logger.info("message type is " + elementMsgType.getText());
		
		//*************暂时先用if 判断实现。后期修改成配置文件。
		if(WechatMessage.MESSAGE_TYPE_TEXT.equals(elementMsgType.getText())) {
			message = new TextMessage(root);
		}else if(WechatMessage.MESSAGE_TYPE_IMAGE.equals(elementMsgType.getText())) {
			message = new ImageMessage(root);
		}
		logger.info("Convert message end.");
		return message;
	}
}
 