/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ruyicai.charge.dna.v2.ca;

import com.ruyicai.charge.dna.v2.common.ToolKit;


/** CA����֤��������, ϵͳ����Ĭ��ʹ�ñ�������֤����֤����CA֤��.
 *  ������֤�鹫Կ��ͨ��systemsetting.properties�ļ���SERVER_CERT������ȡ.
 *  �����̻�֤���ͨ��systemsetting.properties�ļ���BJCA_INSTANCE_NAMEָ������.
 *  �̻�֤�������������λ����C:/BJCAROOT(windows), /usr/BJCAROOT(linux)Ŀ¼��BJCA_ServerConfig.xml�ļ���
 *
 * @author Administrator
 */
public class CAProvider {
    public static void main(String[] args) throws Exception {
        String signData = CAProvider.sign(CAProvider.getCA().getServerCert(), "d����dddd");
        System.out.println(signData);
         String Data = CAProvider.verify(signData, true);
          System.out.println(Data);
        
        
    }
    
    private static CA cfca = null;

    /** �����̻�֤��, ͨ��systemsetting.properties�ļ���BJCA_INSTANCE_NAMEָ������.
     * 
     * @return CA֤��ʵ����
     */
    public static CA getCA() {
        if (cfca == null) {
            try {
                cfca = new SZCA(ToolKit.getPropertyFromFile("BJCA_INSTANCE_NAME"));
            } catch (Exception ex) {
                ToolKit.writeLog(CAProvider.class.getName(), "getCA", ex);
            }
        }
        return cfca;
    }

    /** CA����֤��ǩ������
     * 
     * @param cert ������֤�鹫Կ
     * @param value ��Ҫǩ�����ܵ�����
     * @return ǩ�����ܵ�����
     * @throws Exception 
     */
    public static String sign(String cert, String value) throws Exception {
        return getCA().sign(cert, value);
    }

    /** ��ǩ���������ݽ�����ǩ��
     * 
     * @param sign ǩ�����ܵ�����
     * @return ǩ�����ܵ�ԭ��
     * @throws Exception 
     */
    public static String verify(String value, boolean checkSign) throws Exception {
        return getCA().verify(value, checkSign);
    }
}

