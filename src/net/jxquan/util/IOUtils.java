package net.jxquan.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class IOUtils {
        /** 
         * 文件转化为字节数组 
         *  
         * @param file 
         * @return 
         */  
        public static byte[] getBytesFromFile(CommonsMultipartFile file) {  
            byte[] ret = null;  
            try {  
                if (file == null) {  
                    throw new RuntimeException("传进来的是一个空文件哦");  
                }  
                InputStream in = file.getInputStream();  
                ByteArrayOutputStream out = new ByteArrayOutputStream(4096);  
                byte[] b = new byte[4096];  
                int n;  
                while ((n = in.read(b)) != -1) {  
                    out.write(b, 0, n);  
                }  
                in.close();  
                out.close();  
                ret = out.toByteArray();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            return ret;  
        }  


}
