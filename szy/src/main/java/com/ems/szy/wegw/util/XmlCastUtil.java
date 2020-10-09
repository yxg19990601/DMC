package com.ems.szy.wegw.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

public class XmlCastUtil {

    private XmlCastUtil() {}
    /**
     *  对象转换xml
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());    // 获取上下文对象
            Marshaller marshaller = context.createMarshaller(); // 根据上下文获取marshaller对象
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  // 设置编码字符集
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化XML输出，有分行和缩进
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(obj, baos);
            String xmlObj = new String(baos.toByteArray());         // 生成XML字符串
            return xmlObj;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return  null;
    }


    /**
     *xml转换成对象
     *
     *createdbycaizhon2018-05-24v1.0
     */
    @SuppressWarnings("unchecked")
    public static  <T> T convertToBean(Class<T> clazz,String xmlStr)throws Exception{
        JAXBContext context=JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller=context.createUnmarshaller();
        StringReader sr=new StringReader(xmlStr);
        return (T)unmarshaller.unmarshal(sr);
    }






}
