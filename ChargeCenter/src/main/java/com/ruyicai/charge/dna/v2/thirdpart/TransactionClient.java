package com.ruyicai.charge.dna.v2.thirdpart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.ruyicai.charge.dna.v2.common.Formatter;
import com.ruyicai.charge.dna.v2.common.SslConnection;
import com.ruyicai.charge.dna.v2.common.Strings;
import com.ruyicai.charge.dna.v2.common.ToolKit;
import com.ruyicai.charge.dna.v2.common.encrpt.MD5;
import com.ruyicai.charge.dna.v2.common.encrpt.RSA;
import com.ruyicai.charge.dna.v2.common.encrpt.TripleDes;
import com.ruyicai.charge.dna.v2.thirdpart.jaws.IOrderServerWSProxy;

/**
 * ��������֧���̻�����ͻ��ˣ�֧��WebService, XML,XML CA���ֽ��뷽ʽ��
 * ����˵������ա���������֧��ƽ̨�ӿڹ淶.doc��
 */
public final class TransactionClient {

    /** ����һ����������֧���ͻ���ʵ��, Ĭ����WebService����, ��������TransactionType�޸����ӷ�ʽ��
     * 
     * @param url - Url ��ַ��
     * @param nameSpace - WebService �����ռ�, �������ӷ�ʽ���
     */
    public TransactionClient(String url, String nameSpace) {

        this.url = url;
        this.nameSpace = "" + nameSpace;
        this.setTransactionType(TransactionType.WebService);
    }
    private TransactionType transactionType;
    private String serverCert = "";
    private String merchantNo;
    private String terminalNo;
    private String merchantPWD;
    private String nameSpace;
    private String url;
    private String serverEncoding = "GB2312";

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getServerEncoding() {
        return serverEncoding;
    }

    public void setServerEncoding(String serverEncoding) {
        this.serverEncoding = serverEncoding;
    }
    private int timeout = 120000;

    /** ��ȡ���׳�ʱʱ��,��λ:����
     * 
     * @return ���׳�ʱʱ��, Ĭ��120��
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * ���ý��׳�ʱʱ��,����, Ĭ��120��
     * @param timeout ���׳�ʱʱ��,��λ:��
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /** ��ȡ���������ӵ�ַ,Socket�ӿ�ʱ��IP.
     * 
     * @return ���������ӵ�ַ 
     */
    public String getUrl() {
        return url;
    }

    /** ���÷��������ӵ�ַ
     * 
     * @param url ���������ӵ�ַ
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /** ��ȡ���������ռ�, ֻ���WebService����; Socket�ӿ�ʱ��˿�.
     * 
     * @return ���������ռ�
     */
    public String getNameSapce() {
        return nameSpace;
    }

    /** ���÷��������ռ�, ֻ���WebService����; Socket�ӿ�ʱ��˿�.
     * 
     * @param nameSpace ���������ռ�
     */
    public void setNameSapce(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    /** ��ȡ������뷽ʽ, ��ʱ����WebService, XML, CA, SOCKET
     * 
     * @return ������뷽ʽ
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /** ���÷�����뷽ʽ
     * TransactionType.CA: ���ܱ������ʽ��BASE64(�汾��))|BASE64(RSA(���ļ�����Կ))| BASE64(3DES(����ԭ��))| BASE64(MD5(����ԭ��))
     * @param type ��ʱ����WebService, XML, CA
     */
    public void setTransactionType(TransactionType type) {
        this.transactionType = type;
    }

    /** ��ȡ�����CA֤�鹫Կ
     * 
     * @return �����CA֤�鹫Կ 
     */
    public String getServerCert() {
        return serverCert;
    }

    /** ���÷����CA֤�鹫Կ
     * 
     * @param cert �����CA֤�鹫Կ 
     */
    public void setServerCert(String cert) {
        this.serverCert = cert;
    }

    /** �����̻����, �̻��������������֧��ƽ̨���̻�ע��ɹ������ ��ʽ����:
     * ��01��	�̻����+���̻���ţ����磺"01"+"�̻����"+"|"+"���̻����", �ݲ�֧�֣�
     * ��02��	�̻���� (���磺"02"+""+"|"+"")
     *     
     * @return �̻����
     */
    public String getMerchantNo() {
        return merchantNo;
    }

    /** �����̻���ţ��̻��������������֧��ƽ̨���̻�ע��ɹ������
     * ��01��	�̻����+���̻���ţ����磺"01"+"�̻����"+"|"+"���̻����", �ݲ�֧�֣�
     * ��02��	�̻���� (���磺"02"+""+"|"+"")
     * 
     * @param merchantNo �̻����
     */
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    /** ��ȡ�̻���Կ�� �̻���Կ����������֧��ƽ̨���̻�ע��ɹ������
     * 
     * @return �̻���Կ
     */
    public String getMerchantPWD() {
        return merchantPWD;
    }

    /** �����̻���Կ���̻���Կ����������֧��ƽ̨���̻�ע��ɹ������
     * 
     * @param merchantPWD �̻���Կ
     */
    public void setMerchantPassWD(String merchantPWD) {
        this.merchantPWD = merchantPWD;
    }

    /** ��ȡ�̻��ն˱�ţ�����������֧��ƽ̨���̻�ע��ɹ������
     * 
     * @return �̻��ն˱��
     */
    public String getTerminalNo() {
        return terminalNo;
    }

    /** �����̻��ն˱�ţ�����������֧��ƽ̨���̻�ע��ɹ������
     * 
     * @param terminalNo �̻��ն˱��
     */
    public void setTerminalNo(String terminalNo) {
        this.terminalNo = terminalNo;
    }

    /** ���������Ƿ�����
     * 
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @param acqSsn ϵͳ���ٺ�
     * @return ����39��(RespCode)��Ӧ��, 0000��ʾ����
     * @throws Exception 
     */
    public PosMessage connectionTest(String acqSsn, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0800");
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        PosMessage resMessage = this.transact(request, encryptKey);

        return resMessage;
    }

    /** ���Ʋ�ѯ�������ֻ�������������֧�����û��������
     * @param acqSsn - ϵͳ���ٺ�
     * @param accountNum - �ֿ����ʺţ�ǰ��λ�������ͣ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
    
     * @param transData - ҵ�񽻻�����
     * @return  ����39��(RespCode)��Ӧ��, �ж�5.4����֧��48��(TransData)��Ҫ��д������.
     *           0000������������ϵͳ�����ο���5.4��48�������ṩ�ֿ�����Ϣ��
     *           T437�������û���ϵͳδ���׹����¿���5.4��48�����ṩ�ֿ�����Ϣ������������������֤���ţ�������������ʡ�У�����֤���������ͣ�
     *           T438������������ϵͳ���׹���δ����������Ŀ�������ʱ�����û�������Ҫ�������ѣ�
     *           T436���ÿ�����ʱ�����ޣ��ܾ����ף�
     *           T432���������������������п��ţ��ܾ����ף�
     *           T404��ϵͳ��֧�ֵ����п��ţ��ܾ����ס�
     *           ��4��(Amount)���ظ��û����ս��׽�����ޡ�
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @throws Exception ���ؽ��MACУ��ʧ��
     */
    public PosMessage bindQuery(String acqSsn, String accountNum, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0100");
        request.setAccountNum(accountNum);
        request.setProcessCode("300002");
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setTransData(transData);

        MD5 md5 = new MD5();
        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);

        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��MACУ��ʧ��");
        }

        return resMessage;
    }

    /** �ʻ���ѯ�����ؽ����ʺ�����������֧����ע�����
     * @param acqSsn - ϵͳ���ٺ�
     * @param accountNum - �ֿ����ʺţ�ǰ��λ�������ͣ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
    
     * @param transData - ҵ�񽻻�����
     * @return  ����39��(RespCode)��Ӧ��, �ж�5.4����֧��48��(TransData)��Ҫ��д������.
     *           0000������������ϵͳ�����ο���5.4��48�������ṩ�ֿ�����Ϣ��
     *           T437�������û���ϵͳδ���׹����¿���5.4��48�����ṩ�ֿ�����Ϣ������������������֤���ţ�������������ʡ�У�����֤���������ͣ�
     *           T438������������ϵͳ���׹���δ����������Ŀ�������ʱ�����û�������Ҫ�������ѣ�
     *           T436���ÿ�����ʱ�����ޣ��ܾ����ף�
     *           T432���������������������п��ţ��ܾ����ף�
     *           T404��ϵͳ��֧�ֵ����п��ţ��ܾ����ס�
     *           ��4��(Amount)���ظ��û����ս��׽�����ޡ�
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @throws Exception ���ؽ��MACУ��ʧ��
     */
    public PosMessage accountQuery(String acqSsn, String accountNum, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0100");
        request.setAccountNum(accountNum);
        request.setProcessCode("300002");
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setTransData(transData);

        MD5 md5 = new MD5();
        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);

        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��MACУ��ʧ��");
        }

        return resMessage;
    }

    /**
     * ������ѯ
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum - �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param orderNo ������ţ�ǰ��λ�������ͣ� ������ѯ�����ṩ��01��DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ��ѯ�ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception ���ؽ��MACУ��ʧ��
     */
    public PosMessage orderQuery(String acqSsn, String accountNum, String orderNo, boolean isPayNow, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0120");
        request.setAccountNum(accountNum);
        if (isPayNow) {
            request.setProcessCode("310000");
        } else {
            request.setProcessCode("310002");
        }

        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setOrderNo(orderNo);

        MD5 md5 = new MD5();
        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);
        if (resMessage != null && !resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��MACУ��ʧ��");
        }

        return resMessage;
    }

    /** �Ǽ�ʱ֧����������֧��
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum - �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param orderNo ������ţ�ǰ��λ�������ͣ� �̻��ύ�̻�������ţ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param transData
     *          ��������	C	C	���п���������(���û��������ѷ��ظ��̻�)
     *          ����֤������	C	C	���п�����֤������
     *          ������������ʡ��	C	C	ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�
     *          ����֤������	C	C	���п�����֤�����ͣ�����7.2˵��
     *          ��������	C	C+	310002:�Ǽ�ʱ������ѯ������������ 
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception ���ؽ��MACУ��ʧ��
     */
    public PosMessage orderPay(String acqSsn, String accountNum, String orderNo, String respCode, String pin, String reference, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0120");
        request.setAccountNum(accountNum);
        request.setProcessCode("310001");
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setOrderNo(orderNo);
        request.setReference(reference);
        request.setTransData(transData);
        request.setRespCode(respCode);
        request.setPin(pin);

        MD5 md5 = new MD5();
        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);
        if (resMessage != null && !resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��MACУ��ʧ��");
        }

        return resMessage;
    }

    /** �Ǽ�ʱ֧����������֧��
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum - �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param orderNo ������ţ�ǰ��λ�������ͣ� �̻��ύ�̻�������ţ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param transData
     *          ��������	C	C	���п���������(���û��������ѷ��ظ��̻�)
     *          ����֤������	C	C	���п�����֤������
     *          ������������ʡ��	C	C	ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�
     *          ����֤������	C	C	���п�����֤�����ͣ�����7.2˵��
     *          ��������	C	C+	310002:�Ǽ�ʱ������ѯ������������ 
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception ���ؽ��MACУ��ʧ��
     */
    public PosMessage orderVpc(PosMessage posMsg) throws Exception {

        VpcMessage request = TransactionUtil.toVpcMessage(posMsg);
        request.setMessageType("0500");

        VpcMessage resMessage = this.transactVPC(request);
//        if (resMessage != null && !resMessage.getMac().toUpperCase().equals(
//                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
//            throw new Exception("���ؽ��MACУ��ʧ��");
//        }

        return TransactionUtil.toXmlMessage(resMessage);
    }

    /** ����֧��
     *  �̻��ڵ���5.4����֧���ӿ�ǰ�����ȵ���5.2�ʻ���ѯ��������300002���ӿڣ� ����5.2�ʻ���ѯ��ѯ�����ȷ��д48��(TransData)���ݡ�
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum  �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param amount  �������
     * @param orderNo ������ţ� �̻��ύ�̻�������ţ�ǰ��λ�������ͣ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param description ��������, ���Ȳ�Ҫ����50���֣�ǰ��λ�������������ϳ����͡�
     *          ��00��	����
     *          ��01��	����
     *          ��02��	Ӣ��
     * @param remark ������ע
     * @param payNow �Ƿ�ʱ֧��
     * @param returnUrl ����֧������첽���ص�ַ��ǰ��λ������ַ���ͣ�ͬ����������ա�
     *                   �������ǿ�, �ֻ�֧�����ؽ��׽��������6.2�����õ�ַ, 
     *                   �̻��յ�����󷵻�ȷ����Ϣ������5.8����������ص�ַΪServlet��ַ��
     *                   ��ֱ�ӷ��ء�0000�������̻����ս���ɹ������践��ҳ��.
     *                   �κ�����ҳ�淵�ؾ���ʾ���ս���ɹ���
     *
     *           ��01��	Socket�첽���ؽ��յ�ַ(�ݲ�֧��) ���ݸ�ʽ��[IP|PORT]
     *           ��02��	Http�첽���ؽ��յ�ַ ���ݸ�ʽ��[URL]
     *           ��03��	WebService�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|NAMESPACE]
     *           ��04��	Httpͬ��/�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|URL]
     *           ��05��	CAǩ��XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *           ��06��	XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *      
     * @param transData �ṩҵ�񽻻����ݰ������п�������������֤����������������ʡ�У�����֤�����͵ȣ��ֶ�֮���ԡ�|���ָ�, �������ռ���
     *          1	��������            ���п���������
     *          2	����֤������        ���п�����֤������
     *          3	������������ʡ��    ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�
     *          4	����֤������        ���п�����֤�����ͣ�����7.2˵��
     *          5	��������������      ���Ʊ�˻���, ����������Զ��ŷָ�,���������˽϶�����,����������,��������˰���������,���������.
     *          6	�ֿ���IP��ַ        �ֿ��˵�½�̻���վ��IP��ַ
     *          7	����֤����ַ        ����֤����ַ����ȫ���ṩ,��ȡ���ֵ�����,�����ֵ��ؼ��ְ�����·/�ֵ�/��/��ͬ/��/��/��/��/��/��/ׯ/Ū/��/լ/ߗ/��/��/կ/��/��/԰/Ժ
     *          8	�������ֻ���	   �������ֻ���(�ֻ���ֵ����)
     *          9	��Ʒ���۵�	   ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�(�Ź�����)
     *          10	�������еǼ��ֻ���  (5.2�����֤,���ײο�����Ҫ��ı���)
     *          11	������ҵ�������    ���磺
     *                                          Apple:appid
     *                                          ����:�������к�|���к�
     *                                          ���ÿ�����:���ÿ���|����|֤����|֤������
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception
     */
    public PosMessage pay(String acqSsn, String accountNum, String pin,
            String amount, String orderNo, String reference, String description, String remark,
            boolean payNow, String returnUrl, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0200");
        request.setAccountNum(accountNum);
        request.setProcessCode(payNow ? "190000" : "190001");
        request.setAmount(amount);
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setOrderNo(orderNo);
        request.setPin(pin);
        request.setReference(reference);
        request.setCurCode("01");
        request.setDescription(description);
        request.setRemark(remark);
        request.setOrderState("01");
        request.setOrderType(payNow ? "00" : "01");
        request.setTransData(transData);
        request.setReturnAddress(returnUrl);

        MD5 md5 = new MD5();

        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);

        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        return resMessage;
    }

    /** �̻���һ��ͨ��������, TsNo����һ��ͨ�豸֧����ʶ��
     *  �̻��ڵ���5.4����֧���ӿ�ǰ�����ȵ���5.2�ʻ���ѯ��������300002���ӿڣ� ����5.2�ʻ���ѯ��ѯ�����ȷ��д48��(TransData)���ݡ�
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum  �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param amount  �������
     * @param orderNo ������ţ� �̻��ύ�̻�������ţ�ǰ��λ�������ͣ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param description ��������, ���Ȳ�Ҫ����50���֣�ǰ��λ�������������ϳ����͡�
     *          ��00��	����
     *          ��01��	����
     *          ��02��	Ӣ��
     * @param remark ������ע
     * @param payNow �Ƿ�ʱ֧��
     * @param returnUrl ����֧������첽���ص�ַ��ǰ��λ������ַ���ͣ�ͬ����������ա�
     *                   �������ǿ�, �ֻ�֧�����ؽ��׽��������6.2�����õ�ַ, 
     *                   �̻��յ�����󷵻�ȷ����Ϣ������5.8����������ص�ַΪServlet��ַ��
     *                   ��ֱ�ӷ��ء�0000�������̻����ս���ɹ������践��ҳ��.
     *                   �κ�����ҳ�淵�ؾ���ʾ���ս���ɹ���
     *
     *           ��01��	Socket�첽���ؽ��յ�ַ(�ݲ�֧��) ���ݸ�ʽ��[IP|PORT]
     *           ��02��	Http�첽���ؽ��յ�ַ ���ݸ�ʽ��[URL]
     *           ��03��	WebService�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|NAMESPACE]
     *           ��04��	Httpͬ��/�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|URL]
     *           ��05��	CAǩ��XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *           ��06��	XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     * @param VpcUrl һ��ͨ�豸��ַ��           
     * @param transData �ṩҵ�񽻻����ݰ������п�������������֤����������������ʡ�У�����֤�����͵ȣ��ֶ�֮���ԡ�|���ָ�, �������ռ���
     *          1	��������            ���п���������
     *          2	����֤������        ���п�����֤������
     *          3	������������ʡ��    ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�
     *          4	����֤������        ���п�����֤�����ͣ�����7.2˵��
     *          5	��������������      ���Ʊ�˻���, ����������Զ��ŷָ�,���������˽϶�����,����������,��������˰���������,���������.
     *          6	�ֿ���IP��ַ        �ֿ��˵�½�̻���վ��IP��ַ
     *          7	����֤����ַ        ����֤����ַ����ȫ���ṩ,��ȡ���ֵ�����,�����ֵ��ؼ��ְ�����·/�ֵ�/��/��ͬ/��/��/��/��/��/��/ׯ/Ū/��/լ/ߗ/��/��/կ/��/��/԰/Ժ
     *          8	�������ֻ���	   �������ֻ���(�ֻ���ֵ����)
     *          9	��Ʒ���۵�	   ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�(�Ź�����)
     *          10	�������еǼ��ֻ���  (5.2�����֤,���ײο�����Ҫ��ı���)
     *          11	������ҵ�������    ���磺
     *                                          Apple:appid
     *                                          ����:�������к�|���к�
     *                                          ���ÿ�����:���ÿ���|����|֤����|֤������
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception
     */
    public PosMessage payVPC(String acqSsn, String accountNum, String pin,
            String amount, String orderNo, String reference, String description, String remark,
            String returnUrl, String vpcUrl, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0200");
        request.setAccountNum(accountNum);
        request.setProcessCode("190001");
        request.setAmount(amount);
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setOrderNo(orderNo);
        request.setPin(pin);
        request.setReference(reference);
        request.setCurCode("01");
        request.setDescription(description);
        request.setRemark(remark);
        request.setOrderState("01");
        request.setOrderType("01");
        request.setTransData(transData);
        request.setReturnAddress(returnUrl);

        MD5 md5 = new MD5();

        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage pm = this.transact(request, encryptKey);

        if (!pm.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(pm) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        if (pm.getRespCode().equals("0000")) {

            VpcMessage vpcMsg = new VpcMessage();
            vpcMsg.setMessageType("0500"); //һ��ͨ
            String accType = accountNum.substring(0, 2);
            String[] acc = accountNum.substring(2).split("\\|");
            if (accType.equals("14")) {
                vpcMsg.setPhoneNum("9" + acc[0]);
                vpcMsg.setPrimaryAcctNum("02|" + acc[1] + "|");
                vpcMsg.setPinType("00");
            } else if (accType.equals("21")) {
                vpcMsg.setPhoneNum("9" + acc[0]);
                vpcMsg.setPrimaryAcctNum("01|" + acc[1] + "|");
                vpcMsg.setPinType("00");
            } else if (accType.equals("01")) {
                vpcMsg.setPrimaryAcctNum("02|" + acc[1] + "|");
                vpcMsg.setPinType("00");
            } else if (accType.equals("02")) {
                vpcMsg.setPrimaryAcctNum("01|" + acc[1] + "|");
                vpcMsg.setPinType("00");
            } else if (accType.equals("04")) {
                vpcMsg.setPhoneNum("9" + acc[0]);
                vpcMsg.setPinType("01");
            }

            vpcMsg.setAmount(pm.getAmount().replace(".", ""));
            vpcMsg.setCurCode("156");
            vpcMsg.setMerchantNo(pm.getMerchantNo().substring(2));
            vpcMsg.setTerminalNo(pm.getTerminalNo());
            vpcMsg.setTransDatetime(Formatter.MMddHHmmss(new Date()));
            vpcMsg.setOrderDesc(pm.getDescription());
            vpcMsg.setOrderExpireDate(Formatter.yyMMddHHmmss(new Date()));
            vpcMsg.setSysTraceID(pm.getAcqSsn());
            vpcMsg.setOrderNo(pm.getOrderNo());

            TransactionClient tmVPC = new TransactionClient(vpcUrl, "");
            tmVPC.setTransactionType(TransactionType.VPC);
            vpcMsg = tmVPC.transactVPC(vpcMsg);
            pm.setTsNo(vpcMsg.getProcessCode()); //֧����ʶ��
        }

        return pm;
    }

    /** ��������: ��ҵί��ͨ���ʽ����ͨ������
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum  �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param amount  �������
     * @param orderNo ������ţ� �̻��ύ�̻�������ţ�ǰ��λ�������ͣ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param description ��������, ���Ȳ�Ҫ����50���֣�ǰ��λ�������������ϳ����͡�
     *          ��00��	����
     *          ��01��	����
     *          ��02��	Ӣ��
     * @param remark ������ע
     * @param payNow �Ƿ�ʱ֧��
     * @param returnUrl ����֧������첽���ص�ַ��ǰ��λ������ַ���ͣ�ͬ����������ա�
     *                   �������ǿ�, �ֻ�֧�����ؽ��׽��������6.2�����õ�ַ, 
     *                   �̻��յ�����󷵻�ȷ����Ϣ������5.8����������ص�ַΪServlet��ַ��
     *                   ��ֱ�ӷ��ء�0000�������̻����ս���ɹ������践��ҳ��.
     *                   �κ�����ҳ�淵�ؾ���ʾ���ս���ɹ���
     *
     *           ��01��	Socket�첽���ؽ��յ�ַ(�ݲ�֧��) ���ݸ�ʽ��[IP|PORT]
     *           ��02��	Http�첽���ؽ��յ�ַ ���ݸ�ʽ��[URL]
     *           ��03��	WebService�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|NAMESPACE]
     *           ��04��	Httpͬ��/�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|URL]
     *           ��05��	CAǩ��XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *           ��06��	XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *      
     * @param transData �ṩҵ�񽻻����ݰ������п�������������֤����������������ʡ�У�����֤�����͵ȣ��ֶ�֮���ԡ�|���ָ�, �������ռ���
     *          1	��������            ���п���������
     *          2	����֤������        ���п�����֤������
     *          3	������������ʡ��    ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�
     *          4	����֤������        ���п�����֤�����ͣ�����7.2˵��
     *          5	��������������      ���Ʊ�˻���, ����������Զ��ŷָ�,���������˽϶�����,����������,��������˰���������,���������.
     *          6	�ֿ���IP��ַ        �ֿ��˵�½�̻���վ��IP��ַ
     *          7	����֤����ַ        ����֤����ַ����ȫ���ṩ,��ȡ���ֵ�����,�����ֵ��ؼ��ְ�����·/�ֵ�/��/��ͬ/��/��/��/��/��/��/ׯ/Ū/��/լ/ߗ/��/��/կ/��/��/԰/Ժ
     *          8	�������ֻ���	   �������ֻ���(�ֻ���ֵ����)
     *          9	��Ʒ���۵�	   ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�(�Ź�����)
     *          10	�������еǼ��ֻ���  (5.2�����֤,���ײο�����Ҫ��ı���)
     *          11	������ҵ�������    ���磺
     *                                          Apple:appid
     *                                          ����:�������к�|���к�
     *                                          ���ÿ�����:���ÿ���|����|֤����|֤������
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception
     */
    public PosMessage payAgent(String acqSsn, String accountNum, String pin,
            String amount, String orderNo, String reference, String description, String remark,
            boolean payNow, String returnUrl, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0200");
        request.setAccountNum(accountNum);
        request.setProcessCode("190003");
        request.setAmount(amount);
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setOrderNo(orderNo);
        request.setPin(pin);
        request.setReference(reference);
        request.setCurCode("01");
        request.setDescription(description);
        request.setRemark(remark);
        request.setOrderState("01");
        request.setOrderType(payNow ? "00" : "01");
        request.setTransData(transData);
        request.setReturnAddress(returnUrl);

        MD5 md5 = new MD5();

        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);

        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        return resMessage;
    }

    /** ���ս���: ��ҵί��ͨ���ʽ����ͨ���տ�
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum  �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param amount  �������
     * @param orderNo ������ţ� �̻��ύ�̻�������ţ�ǰ��λ�������ͣ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param description ��������, ���Ȳ�Ҫ����50���֣�ǰ��λ�������������ϳ����͡�
     *          ��00��	����
     *          ��01��	����
     *          ��02��	Ӣ��
     * @param remark ������ע
     * @param payNow �Ƿ�ʱ֧��
     * @param returnUrl ����֧������첽���ص�ַ��ǰ��λ������ַ���ͣ�ͬ����������ա�
     *                   �������ǿ�, �ֻ�֧�����ؽ��׽��������6.2�����õ�ַ, 
     *                   �̻��յ�����󷵻�ȷ����Ϣ������5.8����������ص�ַΪServlet��ַ��
     *                   ��ֱ�ӷ��ء�0000�������̻����ս���ɹ������践��ҳ��.
     *                   �κ�����ҳ�淵�ؾ���ʾ���ս���ɹ���
     *
     *           ��01��	Socket�첽���ؽ��յ�ַ(�ݲ�֧��) ���ݸ�ʽ��[IP|PORT]
     *           ��02��	Http�첽���ؽ��յ�ַ ���ݸ�ʽ��[URL]
     *           ��03��	WebService�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|NAMESPACE]
     *           ��04��	Httpͬ��/�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|URL]
     *           ��05��	CAǩ��XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *           ��06��	XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *      
     * @param transData �ṩҵ�񽻻����ݰ������п�������������֤����������������ʡ�У�����֤�����͵ȣ��ֶ�֮���ԡ�|���ָ�, �������ռ���
     *          1	��������            ���п���������
     *          2	����֤������        ���п�����֤������
     *          3	������������ʡ��    ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�
     *          4	����֤������        ���п�����֤�����ͣ�����7.2˵��
     *          5	��������������      ���Ʊ�˻���, ����������Զ��ŷָ�,���������˽϶�����,����������,��������˰���������,���������.
     *          6	�ֿ���IP��ַ        �ֿ��˵�½�̻���վ��IP��ַ
     *          7	����֤����ַ        ����֤����ַ����ȫ���ṩ,��ȡ���ֵ�����,�����ֵ��ؼ��ְ�����·/�ֵ�/��/��ͬ/��/��/��/��/��/��/ׯ/Ū/��/լ/ߗ/��/��/կ/��/��/԰/Ժ
     *          8	�������ֻ���	   �������ֻ���(�ֻ���ֵ����)
     *          9	��Ʒ���۵�	   ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�(�Ź�����)
     *          10	�������еǼ��ֻ���  (5.2�����֤,���ײο�����Ҫ��ı���)
     *          11	������ҵ�������    ���磺
     *                                          Apple:appid
     *                                          ����:�������к�|���к�
     *                                          ���ÿ�����:���ÿ���|����|֤����|֤������
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception
     */
    public PosMessage PayTrust(String acqSsn, String accountNum, String pin,
            String amount, String orderNo, String reference, String description, String remark,
            boolean payNow, String returnUrl, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0200");
        request.setAccountNum(accountNum);
        request.setProcessCode("190002");
        request.setAmount(amount);
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setOrderNo(orderNo);
        request.setPin(pin);
        request.setReference(reference);
        request.setCurCode("01");
        request.setDescription(description);
        request.setRemark(remark);
        request.setOrderState("01");
        request.setOrderType(payNow ? "00" : "01");
        request.setTransData(transData);
        request.setReturnAddress(returnUrl);

        MD5 md5 = new MD5();

        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);

        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        return resMessage;
    }

    /** ���ս���: ��ҵί��ͨ���ʽ����ͨ���տ�
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum  �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param amount  �������
     * @param orderNo ������ţ� �̻��ύ�̻�������ţ�ǰ��λ�������ͣ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param description ��������, ���Ȳ�Ҫ����50���֣�ǰ��λ�������������ϳ����͡�
     *          ��00��	����
     *          ��01��	����
     *          ��02��	Ӣ��
     * @param remark ������ע
     * @param payNow �Ƿ�ʱ֧��
     * @param returnUrl ����֧������첽���ص�ַ��ǰ��λ������ַ���ͣ�ͬ����������ա�
     *                   �������ǿ�, �ֻ�֧�����ؽ��׽��������6.2�����õ�ַ, 
     *                   �̻��յ�����󷵻�ȷ����Ϣ������5.8����������ص�ַΪServlet��ַ��
     *                   ��ֱ�ӷ��ء�0000�������̻����ս���ɹ������践��ҳ��.
     *                   �κ�����ҳ�淵�ؾ���ʾ���ս���ɹ���
     *
     *           ��01��	Socket�첽���ؽ��յ�ַ(�ݲ�֧��) ���ݸ�ʽ��[IP|PORT]
     *           ��02��	Http�첽���ؽ��յ�ַ ���ݸ�ʽ��[URL]
     *           ��03��	WebService�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|NAMESPACE]
     *           ��04��	Httpͬ��/�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|URL]
     *           ��05��	CAǩ��XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *           ��06��	XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *      
     * @param transData �ṩҵ�񽻻����ݰ������п�������������֤����������������ʡ�У�����֤�����͵ȣ��ֶ�֮���ԡ�|���ָ�, �������ռ���
     *          1	��������            ���п���������
     *          2	����֤������        ���п�����֤������
     *          3	������������ʡ��    ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�
     *          4	����֤������        ���п�����֤�����ͣ�����7.2˵��
     *          5	��������������      ���Ʊ�˻���, ����������Զ��ŷָ�,���������˽϶�����,����������,��������˰���������,���������.
     *          6	�ֿ���IP��ַ        �ֿ��˵�½�̻���վ��IP��ַ
     *          7	����֤����ַ        ����֤����ַ����ȫ���ṩ,��ȡ���ֵ�����,�����ֵ��ؼ��ְ�����·/�ֵ�/��/��ͬ/��/��/��/��/��/��/ׯ/Ū/��/լ/ߗ/��/��/կ/��/��/԰/Ժ
     *          8	�������ֻ���	   �������ֻ���(�ֻ���ֵ����)
     *          9	��Ʒ���۵�	   ʡ���Ѷ��ŷָ���硰�㶫ʡ�������С�, �������С�(�Ź�����)
     *          10	�������еǼ��ֻ���  (5.2�����֤,���ײο�����Ҫ��ı���)
     *          11	������ҵ�������    ���磺
     *                                          Apple:appid
     *                                          ����:�������к�|���к�
     *                                          ���ÿ�����:���ÿ���|����|֤����|֤������
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception
     */
    public PosMessage payCreditCard(String acqSsn, String accountNum, String pin,
            String amount, String orderNo, String reference, String description, String remark,
            String returnUrl, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0200");
        request.setAccountNum(accountNum);
        request.setProcessCode("190004");
        request.setAmount(amount);
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setOrderNo(orderNo);
        request.setPin(pin);
        request.setReference(reference);
        request.setCurCode("01");
        request.setDescription(description);
        request.setRemark(remark);
        request.setOrderState("01");
        request.setOrderType("00");
        request.setTransData(transData);
        request.setReturnAddress(returnUrl);

        MD5 md5 = new MD5();

        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);

        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        return resMessage;
    }

    /** �Ǽ�ʱ����֧��:����Ǽ�ʱ����(190001:�Ǽ�ʱ�µ�)��֧������,����������һ��ͨ����.
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum  �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     *          ��24��	�ֻ�����+���п���+���֤��
     *          ��31��	�ֻ�����+���ÿ���+���֤��
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param amount  �������
     * @param orderNo ������ţ� �̻��ύ�̻�������ţ�ǰ��λ�������ͣ�ͬһ���̻��������ֻ��֧���ɹ�һ�Σ����׷���ʱ��������֧������DNA�������
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param description ��������, ���Ȳ�Ҫ����50���֣�ǰ��λ�������������ϳ����͡�
     *          ��00��	����
     *          ��01��	����
     *          ��02��	Ӣ��
     * @param remark ������ע  
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ֧���ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception
     */
    public PosMessage payIvr(String acqSsn, String accountNum, String pin,
            String amount, String orderNo, String reference, String description, String remark, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0200");
        request.setAccountNum(accountNum);
        request.setProcessCode("310001");
        request.setAmount(amount);
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setMerchantNo(merchantNo);
        request.setTerminalNo(terminalNo);
        request.setOrderNo(orderNo);
        request.setPin(pin);
        request.setReference(reference);
        request.setCurCode("01");
        request.setDescription(description);
        request.setRemark(remark);
        request.setOrderState("01");
        request.setOrderType("00");

        MD5 md5 = new MD5();

        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);

        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        return resMessage;
    }

    /**
     * ��������������ֿ����ֻ���ɽ��׳�����Ȩ
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param accountNum  �ֿ����ʺţ���Ϊ�գ���ʱ֧�� 14��21��ʽ
     *          ��01��	��ǿ���
     *          ��02��	���ÿ���
     *          ��04��	�ֻ���
     *          ��05��	���֤��
     *          ��14��	�ֻ�����+���п���(������"14"+"�ֻ���"+"|"+"���п���")
     *          ��21��	�ֻ�����+���ÿ���(������"14"+"�ֻ���"+"|"+"���ÿ���")
     * 
     * @param pin �������룬�������ģʽ��Ҫ��������ģʽ��գ�����UPOP��Կ���ܣ� �㷨�����<<��������֧��ƽ̨�ӿڹ淶>>˵��
     * @param amount  �������
     * @param orderNo ������ţ� �̻��ύDNA�������/�̻�������ţ�ǰ��λ��������.
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param description ��������, ���Ȳ�Ҫ����50���֣�ǰ��λ�������������ϳ����͡�
     *          ��00��	����
     *          ��01��	����
     *          ��02��	Ӣ��
     * @param remark ����������ע
     * @param returnUrl ����֧������첽���ص�ַ��ǰ��λ������ַ���ͣ�ͬ����������ա�
     *                   �������ǿ�, �ֻ�֧�����ؽ��׽��������6.2�����õ�ַ, 
     *                   �̻��յ�����󷵻�ȷ����Ϣ������5.8����������ص�ַΪServlet��ַ��
     *                   ��ֱ�ӷ��ء�0000�������̻����ս���ɹ������践��ҳ��.
     *                   �κ�����ҳ�淵�ؾ���ʾ���ս���ɹ���
     *
     *           ��01��	Socket�첽���ؽ��յ�ַ(�ݲ�֧��) ���ݸ�ʽ��[IP|PORT]
     *           ��02��	Http�첽���ؽ��յ�ַ ���ݸ�ʽ��[URL]
     *           ��03��	WebService�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|NAMESPACE]
     *           ��04��	Httpͬ��/�첽���ؽ��յ�ַ,���ݸ�ʽ��[URL|URL]
     *           ��05��	CAǩ��XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     *           ��06��	XML�첽���ؽ��յ�ַ, ���ݸ�ʽ[URL]
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����     
     * @return RespCode 0000 ��ʾ�����ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception
     */
    public PosMessage refund(String acqSsn, String accountNum, String pin,
            String amount, String orderNo, String reference, String remark, String returnUrl, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0220");
        request.setAccountNum(accountNum);
        request.setProcessCode("290000");
        request.setAmount(amount);
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setOrderNo(orderNo);
        request.setPin(pin);
        request.setReference(reference);
        request.setRemark(remark);
        request.setCurCode("01");
        MD5 md5 = new MD5();

        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);
        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        return resMessage;
    }

    /**
     * �����ɷѳ��������貦��ֿ����ֻ���ɽ�����Ȩ
     * 
     * @param acqSsn ԭ����ϵͳ���ٺ�
     * @param transDatetime ԭ���״�������
     * @param orderNo �������, �̻��ύDNA�������/�̻�������ţ�ǰ��λ��������.
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param reference ϵͳ�ο��ţ� ԭֵ���ء�
     * @param remark ����������ע
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ�������׳ɹ�, ��������������ա���������֧��ƽ̨�������б�
     */
    public PosMessage quash(String acqSsn, String transDatetime, String orderNo, String remark, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0220");
        request.setProcessCode("290001");
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setOrderNo(orderNo);
        request.setCurCode("01");
        request.setRemark(remark);

        MD5 md5 = new MD5();
        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);
        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��У��ʧ��");
        }

        return resMessage;
    }

    /**
     * �����˻�����,��������֮���ȫ���˻�����������֮�ڲ����˻����룬�����˻������뵱�տ��Ե�¼�����ֻ���֤֧������ȡ�����룬����ϵͳ���Զ�����
     * 
     * @param acqSsn ϵͳ���ٺ�
     * @param orderNo ������ţ� �̻��ύDNA�������/�̻�������ţ�ǰ��λ��������.
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param transData �ֿ��˿�������+��|��+�ֿ��˿�������, ���Ϊ�գ�DNA�Զ���ȡϵͳ��¼��Ϣ.
     * @param remark �������ʱ�ע
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ����ɹ�, ��������������ա���������֧��ƽ̨�������б�
     */
    public PosMessage adjustApply(String acqSsn, String orderNo, String remark, String transData, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0220");
        request.setProcessCode("290003");
        request.setTransDatetime(Formatter.MMddHHmmss(new java.util.Date()));
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setOrderNo(orderNo);
        request.setCurCode("01");
        request.setTransData(transData);

        MD5 md5 = new MD5();
        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);
        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��MACУ��ʧ��");
        }

        return resMessage;
    }

    /** �����˻���ѯ
     * 
     * @param acqSsn ����ʱ��ϵͳ���ٺ�
     * @param transDateTime ����ʱ�Ĵ������ں�ʱ��
     * @param orderNo ������ţ� �̻��ύDNA�������/�̻�������ţ�ǰ��λ��������.
     *                ��01��	�����ֻ�֧��(DNA)�������
     *                ��02��	�̻�������ţ���ҳ�����µ���WEB��
     *                ��03��	�̻�������ţ��ͷ��绰�µ���CallCenter��
     *                ��04��	�̻�������ţ��绰�����µ���IVR��
     *                ��05��	�̻�������ţ��ֻ������µ���WAP��
     * 
     *                ��12��	�̻�������ţ�WEB��+��|��+DNA�������
     *                ��13��	�̻�������ţ�CallCenter��+��|��+DNA�������
     *                ��14��	�̻�������ţ�IVR��+��|��+DNA�������
     *                ��15��	�̻�������ţ�WAP��+��|��+DNA�������
     * 
     * @param transData �ֿ��˿�������+��|��+�ֿ��˿�������, ���Ϊ�գ�DNA�Զ���ȡϵͳ��¼��Ϣ.
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return RespCode 0000 ��ʾ��ѯ�ɹ�, ��������������ա���������֧��ƽ̨�������б�
     * @throws Exception 
     */
    public PosMessage adjustQuery(String acqSsn, String transDateTime, String orderNo, String encryptKey) throws Exception {

        PosMessage request = new PosMessage("0220");
        request.setProcessCode("290004");
        request.setTransDatetime(transDateTime);
        request.setLtime(Formatter.HHmmss(new java.util.Date()));
        request.setLdate(Formatter.yyyyMMdd(new java.util.Date()).substring(4));
        request.setAcqSsn(acqSsn);
        request.setTerminalNo(terminalNo);
        request.setMerchantNo(merchantNo);
        request.setOrderNo(orderNo);
        request.setCurCode("01");

        MD5 md5 = new MD5();
        request.setMac(md5.getMD5ofStr(TransactionUtil.getMacString(request) + " " + merchantPWD));

        PosMessage resMessage = this.transact(request, encryptKey);
        if (!resMessage.getMac().toUpperCase().equals(
                md5.getMD5ofStr(TransactionUtil.getMacString(resMessage) + " " + merchantPWD))) {
            throw new Exception("���ؽ��MACУ��ʧ��");
        }

        return resMessage;
    }

    /** ���״���, ����TransactionType������Ӧ�ķ���ӿ�,�����ش�����.
     * 
     * @param request �������, �����<<��������֧��ƽ̨�ӿڹ淶>>, 4.1���ײ���˵��.
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������, ����CA�ӿڷ��ͽ��ױ���ʱ����, ���ر���ʱ����
     * @return �����<<��������֧��ƽ̨�ӿڹ淶>>, ��5�½��ױ���˵��.
     * @throws Exception 
     */
    public PosMessage transact(PosMessage request, String encryptKey) throws Exception {
        if (this.getTransactionType().equals(TransactionType.CA)) {
            return transactCA(request, encryptKey);
        } else if (this.getTransactionType().equals(TransactionType.XML)) {
            return transactXML(request);
        } else if (this.getTransactionType().equals(TransactionType.SOCKET)) {
            return transactSocketXML(request);
        } else {
            return transactWS(request);
        }
    }

    /** ��������֧��WebService���������׽ӿ����󷽷�
     * @param request ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @return PosMessage ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @throws Exception 
     */
    public PosMessage transactWS(PosMessage request) throws Exception {
        ToolKit.writeLog(TransactionClient.class.getName(), "transactWS.send",
                url + "|" + nameSpace + "|" + timeout + "|" + request.toString());

        IOrderServerWSProxy Client = null;

        try {

            Client = new IOrderServerWSProxy(url, nameSpace);

            com.ruyicai.charge.dna.v2.thirdpart.jaws.PosMessage result = Client.transact(TransactionUtil.translate(request));

            PosMessage payResult = TransactionUtil.translate(result);

            ToolKit.writeLog(TransactionClient.class.getName(), "transactWS.result",
                    payResult.toString());

            return payResult;

        } catch (Exception e) {

            ToolKit.writeLog(TransactionClient.class.getName(), "transactWS", e);
            throw e;
        }
    }

    /** ��������֧��XML CAǩ�����������׽ӿ����󷽷�
     * @param request ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������
     * @return PosMessage ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @throws Exception 
     */
    public PosMessage transactCA(PosMessage request, String encryptKey) throws Exception {
        ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.send",
                url + "|" + nameSpace + "|" + timeout + "|" + request.toString());

        try {
            HttpURLConnection connect = null;
            if (!url.contains("https:")) {
                URL urlConnect = new URL(url);
                connect = (HttpURLConnection) urlConnect.openConnection();
            } else {
                SslConnection urlConnect = new SslConnection();
                connect = (HttpURLConnection) urlConnect.openConnection(url);
            }

            connect.setReadTimeout(timeout);
            connect.setConnectTimeout(timeout);
            connect.setRequestMethod("POST");
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestProperty("content-type", "text/html;charset=utf-8");

            String xml = TransactionUtil.posMessageToXml(request);
            ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.xml", xml);
            com.ruyicai.charge.dna.v2.ca.RSAProvider rsa = new com.ruyicai.charge.dna.v2.ca.RSAProvider();
            xml = rsa.sign(encryptKey, xml, this.getServerCert());
            xml = Strings.padLeft(xml.length() + "", 6) + xml;
            ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.sign", xml);

            connect.connect();
            BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());

            out.write(xml.getBytes("UTF-8"));
            out.flush();
            out.close();

            BufferedInputStream in = new BufferedInputStream(connect.getInputStream());
            byte[] bts = new byte[10000];
            int totalLen = 0, len = 0;
            while ((len = in.read(bts, totalLen, 1000)) != -1) {
                totalLen += len;
                ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.result", "" + len);
            }

            String result = Strings.toString(new String(bts, "UTF-8"));
            ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.result", result);
            result = rsa.verify(encryptKey, result.substring(6));
            ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.verify", result);
            return TransactionUtil.xmlToPosMessage(result);

        } catch (Exception e) {

            ToolKit.writeLog(TransactionClient.class.getName(), "transactCA", e);
            throw e;
        }
    }

    /** ��������֧��XML CAǩ�����������׽ӿ����󷽷�
     * @param request ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @param encryptKey ������Կ,24λ,ÿ�ʽ����������
     * @return PosMessage ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @throws Exception 
     */
    public VpcMessage transactVPC(VpcMessage request) throws Exception {
        ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.send",
                url + "|" + nameSpace + "|" + timeout + "|" + request.toString());

        try {
            HttpURLConnection connect = null;
            if (!url.contains("https:")) {
                URL urlConnect = new URL(url);
                connect = (HttpURLConnection) urlConnect.openConnection();
            } else {
                SslConnection urlConnect = new SslConnection();
                connect = (HttpURLConnection) urlConnect.openConnection(url);
            }

            connect.setReadTimeout(timeout);
            connect.setConnectTimeout(timeout);
            connect.setRequestMethod("POST");
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestProperty("content-type", "text/html;charset="+this.getServerEncoding());

            String xml = TransactionUtil.toXml(request);
            ToolKit.writeLog(TransactionClient.class.getName(), "transactVPC.xml", xml);

            String key = Strings.random(32);

            xml = Formatter.base64Encode(TripleDes.encrypt(key.substring(0, 24).getBytes(this.getServerEncoding()), xml.getBytes(this.getServerEncoding())));
            key = Formatter.base64Encode(RSA.encrypt64(key.getBytes(this.getServerEncoding()), ToolKit.getPropertyFromFile("VPC_CERT_PUB_64")));//.encryptByPrivateKey(key.getBytes("utf-8"), ToolKit.getPropertyFromFile("VPC_CERT_PFX"),
            xml = xml + "&" + key;
            ToolKit.writeLog(this.getClass().getName(), "encrypt", xml.toString());

            connect.connect();
            BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());

            out.write(xml.getBytes(this.getServerEncoding()));
            out.flush();
            out.close();

            BufferedInputStream in = new BufferedInputStream(connect.getInputStream());
            byte[] bts = new byte[10000];
            int totalLen = 0, len = 0;
            while ((len = in.read(bts, totalLen, 1000)) != -1) {
                totalLen += len;
                ToolKit.writeLog(TransactionClient.class.getName(), "transactVPC.result", "" + len);
            }

            String result = Strings.toString(new String(bts, this.getServerEncoding()));
            ToolKit.writeLog(TransactionClient.class.getName(), "transactVPC.result", result);
            String[] encryptValues = result.split("&");
            byte[] keyBt = null;
            if(request.getMessageType().equals("0300")){
                   keyBt = RSA.decrypt(Formatter.base64Decode(encryptValues[1]),
                    ToolKit.getPropertyFromFile("VPC_CERT_PFX"),
                    ToolKit.getPropertyFromFile("VPC_CERT_PFX_PASSWD"));
            } else {
                    keyBt = RSA.decryptByPublicKey(Formatter.base64Decode(encryptValues[1]), ToolKit.getPropertyFromFile("VPC_CERT_PUB_64"));
            }
            byte[] key24 = new byte[24];
            ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.key", new String(keyBt));
            ToolKit.writeLog(TransactionClient.class.getName(), "transactCA.keyBt", keyBt.length + "");
            System.arraycopy(keyBt, 0, key24, 0, 24);

            byte[] srcBt = TripleDes.decrypt(key24, Formatter.base64Decode(encryptValues[0]));
            String strMsg = new String(srcBt, this.getServerEncoding());
            ToolKit.writeLog(this.getClass().getName(), "decrypt", strMsg);

            return TransactionUtil.toVpcMessage(strMsg);

        } catch (Exception e) {

            ToolKit.writeLog(TransactionClient.class.getName(), "transactVPC", e);
            throw e;
        }
    }

    /** ��������֧��XML���������׽ӿ����󷽷�
     * @param request ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @return PosMessage ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @throws Exception 
     */
    public PosMessage transactXML(PosMessage request) throws Exception {
        ToolKit.writeLog(TransactionClient.class.getName(), "transactXml.send",
                url + "|" + nameSpace + "|" + timeout + "|" + request.toString());

        try {
            HttpURLConnection connect = null;
            if (!url.contains("https:")) {
                URL urlConnect = new URL(url);
                connect = (HttpURLConnection) urlConnect.openConnection();
            } else {
                SslConnection urlConnect = new SslConnection();
                connect = (HttpURLConnection) urlConnect.openConnection(url);
            }

            connect.setReadTimeout(this.getTimeout());
            connect.setConnectTimeout(timeout);
            connect.setRequestMethod("POST");
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestProperty("content-type", "text/html;charset=utf-8");

            String xml = TransactionUtil.posMessageToXml(request);
            ToolKit.writeLog(TransactionClient.class.getName(), "transactXml.xml", xml);
            connect.connect();
            BufferedOutputStream out = new BufferedOutputStream(connect.getOutputStream());
            out.write(xml.getBytes("UTF-8"));
            out.flush();
            out.close();

            BufferedInputStream in = new BufferedInputStream(connect.getInputStream());
            byte[] bts = new byte[5000];
            int totalLen = 0, len = 0;
            while ((len = in.read(bts, totalLen, 1000)) != -1) {
                totalLen += len;
                ToolKit.writeLog(TransactionClient.class.getName(), "transactWeb.result", "" + len);
            }

            String result = Strings.toString(new String(bts, "UTF-8"));
            ToolKit.writeLog(TransactionClient.class.getName(), "transactWeb.result", result);
            return TransactionUtil.xmlToPosMessage(result);

        } catch (Exception e) {
            ToolKit.writeLog(TransactionClient.class.getName(), "transactXml", e);
            throw e;
        }
    }

    /** ��������֧��XML������SOCKET���׽ӿ����󷽷�
     * @param request ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @return PosMessage ����ա���������֧��ƽ̨�ӿڹ淶.doc��
     * @throws Exception 
     */
    public PosMessage transactSocketXML(PosMessage request) throws Exception {
        ToolKit.writeLog(TransactionClient.class.getName(), "transactSocketXML.send",
                url + "|" + nameSpace + "|" + timeout + "|" + request.toString());

        try {
            SocketClient client = new SocketClient(url, Integer.valueOf(nameSpace));
            client.getMSocket().setSoTimeout(timeout);

            client.Send(request);
            PosMessage result = client.ReceiveSocketMessage();
            client.Close();
            return result;

        } catch (Exception e) {
            ToolKit.writeLog(TransactionClient.class.getName(), "transactSocketXML", e);
            throw e;
        }
    }
}
