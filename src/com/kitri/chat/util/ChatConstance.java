package com.kitri.chat.util;

public class ChatConstance {

	public static final int PORT = 9876;
	
//	client >> server 전송시 protocol
	//접속 : 100|대화명
	public static final int CS_CONNECT = 100;
	//모든사람에게 메세지 : 200|메세지
	public static final int CS_ALL = 200;
	//특정사람에게 메세지(귓속말) : 250|귓속말대상자|메세지
	public static final int CS_TO = 250;
	//쪽지보내기 : 300|쪽지대상자|메세지
	public static final int CS_PAPER = 300;
	//대화명변경 : 400|변경할대화명
	public static final int CS_RENAME = 400;
	//접속종료 : 900|
	public static final int CS_DISCONNECT = 900;
	
//	server >> client 전송시 protocol
	//접속 : 100||접속자대화명
	public static final int SC_CONNECT = 100;
	//모든사람에게 메세지 : 
//		일반 >> 200||보낸사람대화명] 메세지 
//		귓속말 >> 200||♡보낸사람대화명♡ 메세지
	public static final int SC_MESSAGE = 200;
	//쪽지보내기
	public static final int SC_PAPER = 300;
	//대화명변경
	public static final int SC_RENAME = 400;
	//접속종료
	public static final int SC_DISCONNECT = 900;
	
}
