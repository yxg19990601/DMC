package com.ems.springcloud;

public class Test104 {

    public static void main(String[] args)throws Exception {
        // 计算一个byte有多少个1

        byte[] data = {1,2,8};
        // 00000111
        int count = 0;
        for (byte datum : data) {
            for (int i =0;i<8;i++) {
                count += (datum>>i)&0x1;
            }
        }



        System.out.println(count);

        /*Socket socket = new Socket("169.254.154.230",8008);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        byte[] request = new byte[]{0x68,0x04,0x07,0x00,0x00,0x00};
        outputStream.write(request);
        outputStream.flush();
        byte[] data = new byte[253];
        while (true) {
            inputStream.read(data);
            System.out.println(Arrays.toString(data));
        }
*/

    }
}
