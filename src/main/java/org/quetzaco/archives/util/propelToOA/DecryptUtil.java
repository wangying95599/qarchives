package org.quetzaco.archives.util.propelToOA;
import org.quetzaco.archives.model.ScheduleToOa;
import org.quetzaco.archives.util.config.PropelProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.regex.Pattern;

// import com.eos.system.annotation.Bizlet;


/**
 * @author Administrator
 * @version 1.0
 * @date 2014-12-5
 * @class_displayName DecryptUtil
 */

public class DecryptUtil {

	// @Bizlet("解密")
    public static String decrypt(String key,String message) throws Exception {      
               
            byte[] bytesrc =convertHexString(message);         
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");          
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));         
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");         
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);         
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));      
                      
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);            
                
            byte[] retByte = cipher.doFinal(bytesrc);           
            return new String(retByte);       
    }
    
    public static byte[] convertHexString(String ss)       
    {       
       byte digest[] = new byte[ss.length() / 2];       
       for(int i = 0; i < digest.length; i++)       
       {       
          String byteString = ss.substring(2 * i, 2 * i + 2);       
          int byteValue = Integer.parseInt(byteString, 16);       
          digest[i] = (byte)byteValue;       
       }       
        
       return digest;       
    }
    
	public static void main(String[] args) throws Exception {      
        String key = "1AD42E6F";      
        String value="E4C5CF9EDBE9A437";      
         
        String b=java.net.URLDecoder.decode(DecryptUtil.decrypt(key,value), "utf-8") ;      
        System.out.println("?????????:"+b);      
   }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


}
