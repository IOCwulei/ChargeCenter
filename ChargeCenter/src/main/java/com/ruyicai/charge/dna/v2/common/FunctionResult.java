//Source file: E:\\PROJECT\\DNAPAY\\src\\dnapay\\common\\FunctionResult.java

package com.ruyicai.charge.dna.v2.common;

/**
 * �������ؽ����
 */
public class FunctionResult {

   /**
    * ��������
    * 1--�ɹ�
    * 0--ʧ��
    * 2--���ݿ�ʧ��
    * 3--����ʧ��
    * 4--����
    */
   private Integer returnType;

   /**
    * ����ֵ
    */
   private Object returnValue;

   
   /**
 * ҵ������
 */
private Object dataValue;
   
   /**
    * ע��
    */
   private String remark;

   /**
    */
   public FunctionResult() {

   }

   /**
    * Access method for the returnType property.
    *
    * @return   the current value of the returnType property
    */
   public Integer getReturnType() {
      return returnType;
   }

   /**
    * Sets the value of the returnType property.
    *
    * @param aReturnType the new value of the returnType property
    */
   public void setReturnType(Integer aReturnType) {
      returnType = aReturnType;
   }

   /**
    * Access method for the returnValue property.
    *
    * @return   the current value of the returnValue property
    */
   public Object getReturnValue() {
      return returnValue;
   }

   /**
    * Sets the value of the returnValue property.
    *
    * @param aReturnValue the new value of the returnValue property
    */
   public void setReturnValue(Object aReturnValue) {
      returnValue = aReturnValue;
   }

   /**
    * Access method for the remark property.
    *
    * @return   the current value of the remark property
    */
   public String getRemark() {
      return remark;
   }

   /**
    * Sets the value of the remark property.
    *
    * @param aRemark the new value of the remark property
    */
   public void setRemark(String aRemark) {
      remark = aRemark;
   }

    public String toString() {
        return returnType+"|"+returnValue+"|"+remark;
    }

	public Object getDataValue() {
		return dataValue;
	}

	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}
}
