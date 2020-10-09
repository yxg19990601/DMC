package com.ems.szy.handle;

import com.ems.szy.domain.Device;
import com.ems.szy.domain.Record;
import com.ems.szy.protocol.ColData;
import com.ems.szy.protocol.ProtocolMsg;
import com.ems.szy.protocol.constant.CmdType;
import com.ems.szy.syn.SyncManager;
import com.ems.szy.util.ByteArrayConveter;
import com.ems.szy.util.DataSourceUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.List;

public class SzyHandler extends SimpleChannelInboundHandler<ProtocolMsg> {
    public SzyHandler(){
        System.out.println("SzyHandler.SzyHandler");
    }


    private ChannelHandlerContext ctx ;
    private ChannelPromise promise ;
    private ProtocolMsg data;
    private Object lock = new Object();

    private Device device;

    public Object getLock() {
        return lock;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public ProtocolMsg getData() {
        return  this.data;
    }
    public void setDataNull() {
        this.data = null;
    }

    public ChannelPromise getPromise(){
        return this.promise;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        //这里执行客户端断开连接后的操作
        SyncManager.removeClient(this);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
        String ipAdd = socketAddress.getAddress().getHostAddress();
        SyncManager.putClient(ipAdd,this);
        this.ctx = ctx;
        this.device = DataSourceUtil.deviceMapper.selectByIp(ipAdd);
        System.out.println(device+"  上线了.....");
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolMsg msg) throws Exception {
        ProtocolMsg result = new ProtocolMsg();
        System.out.println(msg);
        //校验CRC
        byte[] crcData = msg.getCrcData();
        try{
            short crcCode = ByteArrayConveter.crc16_xmodem(crcData, 0, crcData.length);

            if (msg.getCmdType() == CmdType.DATA_RESULT) { //数据返回指令，除此命令之外，其他的数据都需要返回给客户端
                if (crcCode == msg.getCrcCode()) {
                    // 数据无误，确认
                    saveData(msg);
                    result.setCmdType(CmdType.DATA_RESULT_ACK);
                } else {
                    System.out.println("crc失败");
                    result.setCmdType(CmdType.DATA_RESEND);
                }
                ctx.writeAndFlush(result);
            } else if(msg.getCmdType() == CmdType.SET_DEVICE_TIME){
                if (promise != null && !promise.isDone()) {
                    this.data = msg;
                    promise.setSuccess();
                }
            } else {
                if (crcCode == msg.getCrcCode()) {
                } else {
                    System.out.println("crc失败");
                    result.setCmdType(CmdType.ERR_CODE);
                }

                if (promise != null && !promise.isDone()) {
                    this.data = msg;
                    promise.setSuccess();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setCmdType(CmdType.ERR_CODE);
        }

    }


    private int saveData(ProtocolMsg msg) {
        if (msg == null || device == null || msg.getResultData() == null) {
            return 0;
        }


        String functionId = (msg.getDataType() & 0xFF)+"";
        String meterId = msg.getRF_ID()+"";
        int count = 0;
        List<ColData> list = msg.getResultData();
        for (ColData colData : list) {
            Record record = new Record();
            record.setMeterId(meterId);
            record.setFunctionId(functionId);
            record.setValue(new BigDecimal(colData.getValue()));
            record.setTime(colData.getColData());
            record.setGatewayId(device.getGatewayId());
            record.setBuildingId(device.getBuildingId());
            count += DataSourceUtil.recordMapper.insert(record);

            record.setFunctionId("4001");
            record.setValue(new BigDecimal(colData.getSimulationVoltage()));
            count += DataSourceUtil.recordMapper.insert(record);

            record.setFunctionId("4002");
            record.setValue(new BigDecimal(colData.getBatteryVoltage()));
            count += DataSourceUtil.recordMapper.insert(record);
        }

        return count;
    }

    public ChannelPromise sendMessage(Object message) {
        if (ctx == null)
            throw new IllegalStateException();
        this.promise = ctx.writeAndFlush(message).channel().newPromise();
        return promise;
    }

}
