package com.kitri.chat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.kitri.chat.util.ChatConstance;

public class ChatService implements Runnable, ActionListener {

	Login login;
	String myid;
	BufferedReader in;
	OutputStream out;
	
	public ChatService(Login login) {
		this.login = login;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == login.nameTf || obj == login.ok) {
			connectProcess();
		} else if(obj == login.cancel) {
			System.exit(0);
		} else if(obj == login.chat.globalsend) {
			globalSendProcess();
		} else if(obj == login.chat.whomsend) {
			whomSendProcess();
		} else if(obj == login.chat.paper) {
			
		} else if(obj == login.chat.rename) {
			
		} else if(obj == login.chat.exit) {
			
		}
	}

	private void whomSendProcess() {
		String to = login.chat.whom.getText();
		if(to.isEmpty()) {
			JOptionPane.showMessageDialog(login.chat, "귓속말 대상자를 선택 하세요.", "대상자미선택", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(to.equals(myid)) {
			JOptionPane.showMessageDialog(login.chat, "자신에게 귓속말을???", "대상자오류", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String msg = login.chat.whomsend.getText().trim();
		if(msg.isEmpty())
			return;
		login.chat.whomsend.setText("");
		send(ChatConstance.CS_TO + "||" + to + "||" + msg);
		login.chat.area.append("♥" + to + "♥ " + msg + "\n");
		login.chat.area.setCaretPosition(login.chat.area.getDocument().getLength());
	}

	/*
	 * 1. data get (메세지)
	 * 2. 서버에 메세지 전송 (200||메세지)
	 */
	private void globalSendProcess() {
		String msg = login.chat.globalsend.getText().trim();
		login.chat.globalsend.setText("");
		if(msg.isEmpty())
			return;
		send(ChatConstance.CS_ALL + "||" + msg);
	}

	/*
	 * 1. data get (서버ip, 대화명) : 유효성검사
	 * 2. 서버ip를 이용하여 socket생성(서버 접속).
	 * 3. 로그인창 닫기, 채팅창 열기
	 * 4. in, out 생성.
	 * 5. 서버에 대화명 전송. (100||대화명)
	 * 6. 메세지 처리 thread 시작.
	 */
	private void connectProcess() {
		myid = login.nameTf.getText().trim();
		if(myid.isEmpty())
			return;
		String host = login.ipTf.getText().trim();
		try {
			Socket s = new Socket(host, ChatConstance.PORT);
			login.setVisible(false);
			login.chat.setVisible(true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = s.getOutputStream();
			send(ChatConstance.CS_CONNECT + "||" + myid);//100||대화명
			new Thread(this).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void send(String msg) {
		try {
			out.write((msg + "\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		boolean flag = true;
		while(flag) {
			try {
				String msg = in.readLine();//protocol|기능에따른형식..
				System.out.println("서버가 클라이언트에 보낸 메세지 : " + msg);
				StringTokenizer st = new StringTokenizer(msg, "||");
				int protocol = Integer.parseInt(st.nextToken());
				switch(protocol) {
					case ChatConstance.SC_CONNECT :{
//						100||접속자대화명
						String tmp = st.nextToken();//접속자대화명
						login.chat.listData.add(tmp);
						login.chat.list.setListData(login.chat.listData);
						login.chat.area.append("[알림] " + tmp + "님이 접속하였습니다.\n");
						login.chat.area.setCaretPosition(login.chat.area.getDocument().getLength());
					}break;
					case ChatConstance.SC_MESSAGE :{
//						200||안효인] 안녕하세요.
						String tmp = st.nextToken();//안효인] 안녕하세요.
						login.chat.area.append(tmp + "\n");
						login.chat.area.setCaretPosition(login.chat.area.getDocument().getLength());
					}break;
					case ChatConstance.SC_PAPER :{
						
					}break;
					case ChatConstance.SC_RENAME :{
						
					}break;
					case ChatConstance.SC_DISCONNECT :{
						
					}break;
				}
			} catch (IOException e) {
				e.printStackTrace();
				flag = false;
				break;
			}
		}
	}

}









