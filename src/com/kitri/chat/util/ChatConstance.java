package com.kitri.chat.util;

public class ChatConstance {

	public static final int PORT = 9876;
	
//	client >> server ���۽� protocol
	//���� : 100|��ȭ��
	public static final int CS_CONNECT = 100;
	//��������� �޼��� : 200|�޼���
	public static final int CS_ALL = 200;
	//Ư��������� �޼���(�ӼӸ�) : 250|�ӼӸ������|�޼���
	public static final int CS_TO = 250;
	//���������� : 300|���������|�޼���
	public static final int CS_PAPER = 300;
	//��ȭ���� : 400|�����Ҵ�ȭ��
	public static final int CS_RENAME = 400;
	//�������� : 900|
	public static final int CS_DISCONNECT = 900;
	
//	server >> client ���۽� protocol
	//���� : 100||�����ڴ�ȭ��
	public static final int SC_CONNECT = 100;
	//��������� �޼��� : 
//		�Ϲ� >> 200||���������ȭ��] �޼��� 
//		�ӼӸ� >> 200||�����������ȭ�� �޼���
	public static final int SC_MESSAGE = 200;
	//����������
	public static final int SC_PAPER = 300;
	//��ȭ����
	public static final int SC_RENAME = 400;
	//��������
	public static final int SC_DISCONNECT = 900;
	
}
