/**
 * 
 */
package com.ruyicai.charge.dna.v2.common;

/** DNA����, ʵ�ִ˽ӿڵĳ��򽫻ᱻͳһ����
 * @author lakey
 *
 */
public interface Service {
	/**��������
	 * @return ��������
	 */
	public String getName();
	
	/**�÷��������
	 * @return ��������
	 */
	public String getDescribtion();
	
	/**�Ƿ��Ѿ�����
	 * @return �Ƿ��Ѿ�����
	 */
	public boolean isStarted();
	
	/**��ʼ�����ݣ���������ǰ�ᱻ����
	 */
	public void init();
	
	/**�����������÷���״̬Ϊ�Ѿ�����
	 * ��������Ѿ��������˺��������κβ�����
	 */
	public void start();
	
	/**ֹͣ�������÷���״̬Ϊδ����
	 * �������δ�������˺��������κβ�����
	 */
	public void stop();
		
	/**�رշ����ͷ�������Դ��
	 * �������δֹͣ���˺����ȵ��� stop() ������Ȼ���ٹرա�
	 */
	public void close();
}
