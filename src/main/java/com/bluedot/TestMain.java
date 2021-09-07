package com.bluedot;


import com.bluedot.framework.simplespring.core.BeanContainer;
import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/4-19:05
 */
public class TestMain {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(5);
        list.add(3);
        list.add(2);
        System.out.println(list.get(0));
        list.remove(0);
        list.add(2);
        System.out.println(list.get(0));
    }
    /**
     *
     */
     private static void testDom(){
         Document doc = null;
         Map<String, Pair> xmlMap = null;
         //File file = new File("src/main/resources/service.xml");
         try {
             SAXReader reader = new SAXReader();
             xmlMap = new HashMap();
             doc = reader.read("src/main/resources/service.xml");
             Element service = doc.getRootElement();
             Iterator nodes = service.elementIterator();

             String number = null;
             String service1 = null;
             String method = null;

             while (nodes.hasNext()) {
                 Element node = (Element) nodes.next();
                 //取节点的值
                 number = (String) node.element("number").getData();
                 service1 = (String) node.element("service").getData();
                 method = (String) node.element("method").getData();
                 xmlMap.put(number, new Pair(service1, method));
             }
             System.out.println(xmlMap.size());

         } catch (DocumentException e) {
             // log.error("service.xml parse error",e);
             e.printStackTrace();
         }

     }
}
