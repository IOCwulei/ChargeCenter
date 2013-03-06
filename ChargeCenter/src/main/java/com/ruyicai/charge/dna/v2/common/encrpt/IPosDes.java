package com.ruyicai.charge.dna.v2.common.encrpt;

/** POS�������㷨�ӿ�
 * 
 * @author Administrator
 */
public interface IPosDes {

	/**����PIK/MAK
	 * @param key
	 * @throws Exception
	 */
	public void setKeys(byte[] key) throws Exception;

	/** Pin����
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptPin(byte[] data) throws Exception;
	public byte[] encryptPIK() throws Exception;
	public byte[] encryptPIKCheck() throws Exception;

	/** Pin����
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptPin(byte[] data) throws Exception;

	/**Mac����
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptMac(byte[] data) throws Exception;
	public byte[] encryptMAK() throws Exception;
	public byte[] encryptMAKCheck() throws Exception;

	/** Mac����
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptMac(byte[] data) throws Exception;

	public void setMK(String data);

	public byte[] getMacKey();

	public void setMacKey(byte[] macKey);

	public byte[] getPinKey();

	public void setPinKey(byte[] pinKey);
}