package org.quetzaco.archives.util.encoder.password;

import com.primeton.tp.common.crypto.EOSCipherTool;
import com.primeton.tp.common.crypto.EOSDigestTool;

/**
 * @Description Created by dong on 2017/8/22.
 */
public class OAPasswordEncoder  {

  private EOSCipherTool cipher = new EOSCipherTool();
  private EOSDigestTool dt = new EOSDigestTool();
  public OAPasswordEncoder() {
  }

  public String encode(String rawPass) {
    String s = "";
    String tos = "";
    try{
      s = cipher.encrypt(rawPass);
      tos = dt.digest(s);
    }catch(Exception e){
      e.printStackTrace();
    }
    return tos;
  }

  public boolean isPasswordValid(String encPass, String rawPass) {
    String pass1 = "" + encPass;
    String pass2 = encode(rawPass);

    return pass1.equals(pass2);
  }
}
