package com.yinhai.common.util;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import java.nio.charset.StandardCharsets;

/** xml工具类
 * @author yanbiao
 * @since 2019/11/15 15:54
 */
public class XmlUtil {
    /**
     * json转xml
     * @param json
     * @return xml字符串
     * @throws DocumentException
     */
    public static String json2Xml(String json) throws DocumentException{
        JSONObject object=JSONObject.fromObject(json);
        String sXml = "";
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(false);
        //设置根目录
        xmlSerializer.setRootName("body");
        String sContent = xmlSerializer.write(object, StandardCharsets.UTF_8.toString());
        Document docCon = DocumentHelper.parseText(sContent);
        sXml = docCon.getRootElement().asXML();
        return sXml;
    }

    /**
     * xml字符串转json对象
     * @param xml
     * @return json对象
     */
    public static JSONObject xml2Json(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();
        //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）
        String  json =  xmlSerializer.read(xml).toString();
        return JSONObject.fromObject(json);
    }

}
