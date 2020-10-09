package com.ems.szy.syn;

import com.ems.szy.handle.SzyHandler;
import com.ems.szy.protocol.ProtocolMsg;
import io.netty.channel.ChannelPromise;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class SyncManager {


    private SyncManager() {}
    // 存贮所有的客户端
    private static Map<String, SzyHandler> clientList = new HashMap<>();

    // 获取某一个ip
    public static  SzyHandler getClient(String ipAddr) {
        return clientList.get(ipAddr);
    }

    // 添加客户端
    public static SzyHandler putClient(String ip, SzyHandler client) {
       return clientList.put(ip,client);
    }

    // 删除不在线的客户端
    public static void removeClient(SzyHandler client) {
        InetSocketAddress socketAddress = (InetSocketAddress)
                client.getCtx().channel().remoteAddress();
        String ipAdd = socketAddress.getAddress().getHostAddress();
        clientList.remove(ipAdd);
    }

    // 查询
    public static  Map<String, SzyHandler>  getClientList() {
        return clientList;
    }




    public static ProtocolMsg sendCmd(String ip,ProtocolMsg msg) {
        SzyHandler client = SyncManager.getClient(ip);
        ProtocolMsg data = new ProtocolMsg();
        if (client == null) {
            return  null;
        }
        synchronized (client.getLock()) {
            try {
                ChannelPromise promise = client.getPromise();
                if (promise != null) { // 上一次命令没有处理完成 ......
                    if ( !promise.isDone()) {
                        promise.await(20000);
                    }
                }

                ChannelPromise channelPromise = client.sendMessage(msg);
                channelPromise.await(3000);
                data = client.getData();
                client.setDataNull();
                if (!channelPromise.isDone()) {
                    channelPromise.setSuccess();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }






}
