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
			JOptionPane.showMessageDialog(login.chat, "�ӼӸ� ����ڸ� ���� �ϼ���.", "����ڹ̼���", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(to.equals(myid)) {
			JOptionPane.showMessageDialog(login.chat, "�ڽſ��� �ӼӸ���???", "����ڿ���", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String msg = login.chat.whomsend.getText().trim();
		if(msg.isEmpty())
			return;
		login.chat.whomsend.setText("");
		send(ChatConstance.CS_TO + "||" + to + "||" + msg);
		login.chat.area.append("��" + to + "�� " + msg + "\n");
		login.chat.area.setCaretPosition(login.chat.area.getDocument().getLength());
	}

	/*
	 * 1. data get (�޼���)
	 * 2. ������ �޼��� ���� (200||�޼���)
	 */
	private void globalSendProcess() {
		String msg = login.chat.globalsend.getText().trim();
		login.chat.globalsend.setText("");
		if(msg.isEmpty())
			return;
		send(ChatConstance.CS_ALL + "||" + msg);
	}

	/*
	 * 1. data get (����ip, ��ȭ��) : ��ȿ���˻�
	 * 2. ����ip�� �̿��Ͽ� socket����(���� ����).
	 * 3. �α���â �ݱ�, ä��â ����
	 * 4. in, out ����.
	 * 5. ������ ��ȭ�� ����. (100||��ȭ��)
	 * 6. �޼��� ó�� thread ����.
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
			send(ChatConstance.CS_CONNECT + "||" + myid);//100||��ȭ��
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
				String msg = in.readLine();//protocol|��ɿ���������..
				System.out.println("������ Ŭ���̾�Ʈ�� ���� �޼��� : " + msg);
				StringTokenizer st = new StringTokenizer(msg, "||");
				int protocol = Integer.parseInt(st.nextToken());
				switch(protocol) {
					case ChatConstance.SC_CONNECT :{
//						100||�����ڴ�ȭ��
						String tmp = st.nextToken();//�����ڴ�ȭ��
						login.chat.listData.add(tmp);
						login.chat.list.setListData(login.chat.listData);
						login.chat.area.append("[�˸�] " + tmp + "���� �����Ͽ����ϴ�.\n");
						login.chat.area.setCaretPosition(login.chat.area.getDocument().getLength());
					}break;
					case ChatConstance.SC_MESSAGE :{
//						200||��ȿ��] �ȳ��ϼ���.
						String tmp = st.nextToken();//��ȿ��] �ȳ��ϼ���.
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









