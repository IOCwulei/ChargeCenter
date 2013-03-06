/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ruyicai.charge.dna.v2.common;

import com.ruyicai.charge.dna.v2.ca.CAProvider;
import com.ruyicai.charge.dna.v2.ca.RSAProvider;

/** ��ȫ�����ࡣ
 *
 * @author Administrator
 */
public class SecurityUtil {

    /** ��Կ���ܣ�˽Կǩ��
     * 
     * @param cert ֤�鹫Կ
     * @param value ��ǩ����������
     * @return ǩ�����ܺ�����
     * @throws Exception 
     */
    public static String caSign(String cert, String value) throws Exception {
        String result = CAProvider.sign(cert, value);
        return result;
    }

    /** ��Կ��ǩ��˽Կ����
     * 
     * @param value ǩ�����ܺ�����
     * @return ǩ������ǰ����
     * @throws Exception 
     */
    public static String caVerify(String value, boolean checkSign) throws Exception {
        String result = CAProvider.verify(value, checkSign);
        return result;
    }

    /** �Ա������ݽ��м���ǩ��
     *  ���ܱ������ʽ��BASE64(�汾��))|BASE64(RSA(���ļ�����Կ))| BASE64(3DES(����ԭ��))| BASE64(MD5(����ԭ��))
     * @param cert base64��Կ
     * @param src ����ԭ��
     * @param key ��̬��Կ�������Ϊ���Զ��������
     * @return ���ܱ������ʽ��BASE64(�汾��))|BASE64(RSA(���ļ�����Կ))| BASE64(3DES(����ԭ��))| BASE64(MD5(����ԭ��))
     * @throws Exception 
     */
    public static String rsaSign(String key, String src, String cert) throws Exception {
        String result = new RSAProvider().sign(key, src, cert);
        return result;
    }

    /** �Ա������ݽ��н�����ǩ
     *  ���ܱ������ʽ��BASE64(�汾��))|BASE64(RSA(���ļ�����Կ))| BASE64(3DES(����ԭ��))| BASE64(MD5(����ԭ��))
     * 
     * @param sign ���ܱ���
     * @param cert ��ǩ��Կ���汾Ϊ��RSA.3DES.MD5withRSA��ʱ��ǩʹ�ã���
     * @param key ������Կ, ���ļ�����ԿΪ��ʱʹ��
     * @return ����ԭ��
     * @throws Exception 
     */
    public static String rsaVerify(String key, String sign, String cert) throws Exception {
        String result = new RSAProvider().verify(key, sign);
        return result;
    }
}
