package com.mittp.autotest.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.web.socket.CloseStatus;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;


/**
 * @author zhouyang
 * 2019-07-03 13:59
 */
public class WebSocketHandler extends AbstractWebSocketHandler {

    private static Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    private static Map<String, WebSocketSession> sessionMap;

    static {
        sessionMap = new ConcurrentHashMap<>();
    }

    /**
     * 发送消息
     */
    public void sendMsg(String message) throws Exception {
        if (sessionMap == null || sessionMap.size() <= 0) {
            return;
        }
        TextMessage textMessage = null;
        for (Map.Entry<String, WebSocketSession> sessionEntry : sessionMap.entrySet()) {
            textMessage = new TextMessage(message);
            this.handleMessage(sessionEntry.getValue(), textMessage);
        }

    }

    /**
     * 连接成功时，会触发UI上的onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getId(), session);
        logger.info("Session:{},连接成功", session.getId());

    }

    /**
     * 在UI在用Js调用webSocket.send()时，会调用该方法
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        session.sendMessage(message);
    }

    /**
     * 传输错误时执行
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.error("传输发生错误，通道被关闭，Session:{}", session.getId());
        sessionMap.remove(session.getId());
    }

    /**
     * 连接关闭时执行
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.error("通道关闭，Session:{}", session.getId());
        sessionMap.remove(session.getId());
    }


}
