/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ruyicai.charge.dna.v2.ca;

import cn.org.bjca.security.SecurityEngineDeal;


/** ��������֤������, ���ڷֹ�˾�ṩ��CA֤��ʵ��.
 * 
 * @author Administrator
 */
public class SZCA implements CA {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        try {
            //��ʼ��Ӧ��,����BJCAROOTĿ¼��Ӧ�������õ�֤��
            SecurityEngineDeal sedServer = SecurityEngineDeal.getInstance("SecXV3Default");
            SecurityEngineDeal sedClient = SecurityEngineDeal.getInstance("BJCASecXV2Default");

            sedServer.setSignMethod("RSA-SHA1");
            sedClient.setSignMethod("RSA-SHA1");
            sedServer.setEncryptMethod("T-DES");
            sedClient.setEncryptMethod("T-DES");

            //ԭ��
            String data = "BJCA���������������������������������������������������BJCA";
            System.out.println("ԭ��++++++" + data);

            String certServer = sedServer.getServerCertificate();
            System.out.println("sedServer.getServerCertificate:++++++" + certServer);
            System.out.println("sedServer.validateCert:++++++" + sedServer.validateCert(certServer));

            System.out.println("sedServer.getCertInfo:�汾++++++++" + sedServer.getCertInfo(certServer, 1));
            System.out.println("sedServer.getCertInfo:֤�����к�++++++++" + sedServer.getCertInfo(certServer, 2));
            System.out.println("sedServer.getCertInfo:֤����Ч����ʼ++++++++" + sedServer.getCertInfo(certServer, 11));
            System.out.println("sedServer.getCertInfo:֤����Ч�ڽ�ֹ++++++++" + sedServer.getCertInfo(certServer, 12));
            System.out.println("sedServer.getCertInfo:�û���֯��++++++++" + sedServer.getCertInfo(certServer, 14));
            System.out.println("sedServer.getCertInfo:�û�ͨ����++++++++" + sedServer.getCertInfo(certServer, 17));
            System.out.println("sedServer.getCertInfo:֤�鹫Կ++++++++" + sedServer.getCertInfo(certServer, 30));

            String certClient = sedClient.getServerCertificate();
            System.out.println("sedClient.getServerCertificate:++++++" + certClient);
            System.out.println("sedClient.validateCert:++++++" + sedClient.validateCert(certClient));

            System.out.println("sedClient.getCertInfo:�汾++++++++" + sedClient.getCertInfo(certClient, 1));
            System.out.println("sedClient.getCertInfo:֤�����к�++++++++" + sedClient.getCertInfo(certClient, 2));
            System.out.println("sedClient.getCertInfo:֤����Ч����ʼ++++++++" + sedClient.getCertInfo(certClient, 11));
            System.out.println("sedClient.getCertInfo:֤����Ч�ڽ�ֹ++++++++" + sedClient.getCertInfo(certClient, 12));
            System.out.println("sedClient.getCertInfo:�û���֯��++++++++" + sedClient.getCertInfo(certClient, 14));
            System.out.println("sedClient.getCertInfo:�û�ͨ����++++++++" + sedClient.getCertInfo(certClient, 17));
            System.out.println("sedClient.getCertInfo:֤�鹫Կ++++++++" + sedClient.getCertInfo(certClient, 30));


            String signdata = sedClient.signData(data);
            System.out.println("sedClient.signData:++++++" + signdata);
            System.out.println("sedClient.verifySignedData:+++++++" + sedServer.verifySignedData(certClient, data, signdata));

            String key = "1234567890ABCDEFG11111111";

            String certCopy = "MIIE9TCCA92gAwIBAgIKIAAAAAAAARJTczANBgkqhkiG9w0BAQUFADA6MQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTENMAsGA1UECwwEQkpDQTENMAsGA1UEAwwEQkpDQTAeFw0wOTEwMjExNjAwMDBaFw0xMTEwMDExNTU5NTlaMEgxCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMQ0wCwYDVQQLDARCSkNBMRswGQYDVQQDDBLmnI3liqHlmajor4HkuabkuIAwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAInLo4VW6RLWDkzxgn3gkYzvzPWOgG0JHVG1KWb18oGd+lo5RNOERn6rj6qmso7M1C5El5Zsu6jPjz0EL7/XmfUKhFhv7raii+MOlM89PFe/8ifd0e5FoW7733pGua3MDUGxySdD73rU9JHnr60FRTuyz2zus0vForyWZumznyS/AgMBAAGjggJxMIICbTAfBgNVHSMEGDAWgBTBzihoGF2OgzPxlaoIwz2KCJqddjAMBgNVHQ8EBQMDB/gAMCsGA1UdEAQkMCKADzIwMDkxMDIyMDAwMDAwWoEPMjAxMTEwMDEyMzU5NTlaMAkGA1UdEwQCMAAwgZkGA1UdHwSBkTCBjjBWoFSgUqRQME4xCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMQ0wCwYDVQQLDARCSkNBMQ0wCwYDVQQDDARCSkNBMRIwEAYDVQQDEwljYTJjcmwzNDkwNKAyoDCGLmh0dHA6Ly9sZGFwLmJqY2Eub3JnLmNuL2NybC9iamNhL2NhMmNybDM0OS5jcmwwEQYJYIZIAYb4QgEBBAQDAgD/MCoGC2CGSAFlAwIBMAkKBBtodHRwOi8vYmpjYS5vcmcuY24vYmpjYS5jcnQwGgYFKlYLBwkEEUpKMDExMDAwMTAwMDE1MTExMB0GCGCGSAGG+EQCBBFKSjAxMTAwMDEwMDAxNTExMTAbBggqVoZIAYEwAQQPMDExMDAwMTAwMDE1MTExMB4GBipWCwcBCAQUMUJASkowMTEwMDAxMDAwMTUxMTEwgbAGA1UdIASBqDCBpTA1BgkqgRwBxTiBFQEwKDAmBggrBgEFBQcCARYaaHR0cDovL3d3dy5iamNhLm9yZy5jbi9jcHMwNQYJKoEcAcU4gRUCMCgwJgYIKwYBBQUHAgEWGmh0dHA6Ly93d3cuYmpjYS5vcmcuY24vY3BzMDUGCSqBHAHFOIEVAzAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczANBgkqhkiG9w0BAQUFAAOCAQEAgjF3O4M0Ztt1ykvbpMLWtCMlJ4sXZh6ABh/XW3tC72wldX9KwJZIHG1lLYgumWHJdZjKL1AzayUntl7ifjkNEIMjU1oqajAAMXSPIPV84hgRCVCx54ue+udknVFQhO1dfAl5cdc4SU2rftIUmx8FG0BF9qwNwE7GdIx8cdYusdXFivYKcExypBQrRS284QOlB1a4GEoU1Pf7OvZ/86wzMRwic3DxM4iZjvJv+G4okR0w2HvRaYO7fsY4H2yBvjtiKoiuryA6pqHIRtcgQvpxOdlRmxZgL5x4Ss3aJcIxUSJ7SF6w/nF5ywD/VSZZ8/jo7/avISOEuqDmvkqDYn0Txw==";
            System.out.println("key++++++" + key);
            String keyEncrypt = sedClient.pubKeyEncrypt(certCopy, key);
            System.out.println("sedClient.pubKeyEncrypt++++++" + keyEncrypt);
            System.out.println("sedServer.priKeyDecrypt++++++" + sedServer.priKeyDecrypt(keyEncrypt));

            String encryptData = sedClient.encryptData(sedServer.base64Encode(key.getBytes()), data);
            System.out.println("sedClient.encryptData++++++" + encryptData);
            System.out.println("sedServer.decryptData++++++" + sedServer.decryptData(sedServer.base64Encode(key.getBytes()), encryptData));


            String signData = sedClient.signDataPkcs7(data);
            System.out.println("sedClient.ignDataPkcs7++++++" + signData);
            System.out.println("sedServer.verifySignedDataPkcs7++++++" + sedServer.verifySignedDataPkcs7(signData));
            System.out.println("sedServer.getP7SignDataInfo:ԭ��++++++" + sedServer.getP7SignDataInfo(signData, 1));
            String certData = sedServer.getP7SignDataInfo(signData, 2);
            System.out.println("sedServer.getP7SignDataInfo:ǩ����֤��++++++" + sedServer.getP7SignDataInfo(signData, 2));
            System.out.println("sedServer.signData:ǩ��ֵ++++++" + sedServer.getP7SignDataInfo(signData, 3));

            String envelopData = sedClient.encodeP7SignAndEnvelopData(certServer, data);
            System.out.println("sedClient.encodeP7SignAndEnvelopData++++++" + envelopData);
            System.out.println("sedServer.decodeP7SignAndEnvelopData++++++" + sedServer.decodeP7SignAndEnvelopData(envelopData));



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    SecurityEngineDeal engine = null;

    public SZCA(String instance) throws Exception {
        engine = SecurityEngineDeal.getInstance(instance);
        engine.setSignMethod("RSA-SHA1");
        engine.setEncryptMethod("T-DES");
    }

    public SZCA() throws Exception {
        engine = SecurityEngineDeal.getInstance("SecXV3Default");
        engine.setSignMethod("RSA-SHA1");
        engine.setEncryptMethod("T-DES");
    }

    /** ���ַ������ݽ�������ǩ����ǩ����ʽΪPkcs7
     * 
     * @param instance
     * @throws Exception 
     */
    public String sign(String cert, String src) throws Exception {
        String certClient = engine.getServerCertificate();
        String key = engine.genRandom(24);
        String keyEncrypt = engine.pubKeyEncrypt(cert, key);
        String srcEncrypt = engine.encryptData(key, src);
        String srcSign = engine.signData(srcEncrypt);
        String tData = certClient + "&" + keyEncrypt + "&" + srcEncrypt + "&" + srcSign;
        return tData;
    }

    /** ��ǩ���������ݽ�����ǩ�����ܡ�
     * 
     * @param sign ǩ�����ܵ�����
     * @return ǩ�����ܵ�ԭ��
     * @throws Exception 
     */
    public String verify(String sign, boolean checkSign) throws Exception {
        String[] values = sign.split("&");
        String cert = values[0];
        String key = engine.priKeyDecrypt(values[1]);
        String src = engine.decryptData(key, values[2]);
        if (checkSign) {
            if (!engine.verifySignedData(cert, values[3], values[4])) {
                throw new Exception("fail to verifySignedData");
            }
        }
        return src;
    }

    /** ��ȡ֤����Ϣ
     * 
     * @param sign Base64�����X.509����֤��
     * @param Type type	����
    1	֤��汾
    2	֤�����к�
    8	֤�鷢����ͨ����
    11	֤����Ч����ʼ
    12	֤����Ч�ڽ�ֹ
    13	�û�������
    14	�û���֯��
    15	�û�������
    16	�û�ʡ����
    17	�û�ͨ����
    18	�û�������
    19	�û�EMAIL��ַ
    20	֤��䷢��DN
    21	֤�����⣨DN��
    23	�û�������(������C)
    24	�û���֯��(������O)
    25	�û�������(������OU)
    26	�û�ʡ����(������S)
    27	�û�ͨ����(������CN)
    28	�û�������(������L)
    29	�û�EMAIL��ַ(������E)
    30	֤�鹫Կ��base64��    
     * @return CertInfo
     */
    public String getCertInfo(String sign, int type)
            throws Exception {

        return engine.getCertInfo(sign, type);

    }

    /** ��ȡ������֤��
     * 
     * @return
     * @throws Exception 
     */
    public String getServerCert() throws Exception {
        return engine.getServerCertificate();
    }
}
