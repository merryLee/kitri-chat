package com.kitri.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import com.kitri.chat.util.ChatConstance;

public class ChatServer implements Runnable {

	ServerSocket ss;
	Vector<ChatClient> vc = new Vector<ChatClient>();
	
	public ChatServer() {
		try {
			ss = new ServerSocket(ChatConstance.PORT);
			System.out.println("Client 접속 대기중.....");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	client 접속 처리 Thread
	@Override
	public void run() {
		boolean flag = true;
		while(flag) {
			try {
				Socket s = ss.accept();
				System.out.println("Client 접속 성공 : " + s);
				new ChatClient(s).start();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	class ChatClient extends Thread {
		
		String name;//대화명.
		BufferedReader in;
		OutputStream out;
		Socket s;
		
		public ChatClient(Socket s) {
			this.s = s;
			try {
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		client의 메세지 처리 Thread
		@Override
		public void run() {
			boolean flag = true;
			while(flag) {
				try {
					String msg = in.readLine();//protocol|기능에따른형식..
					System.out.println("클라이언트가 서버에 보낸 메세지 : " + msg);
					StringTokenizer st = new StringTokenizer(msg, "||");
					int protocol = Integer.parseInt(st.nextToken());
					switch(protocol) {
						case ChatConstance.CS_CONNECT :{
//							100||대화명
							name = st.nextToken();//대화명
							
							int size = vc.size();//나를 제외한 모든사람.
							for (int i = 0; i < size; i++) {
								ChatClient cc = vc.get(i);
								cc.out.write((ChatConstance.SC_CONNECT + "||" + name + "\n").getBytes());
							}
							vc.add(this);
							size = vc.size();//나를 포함한 모든사람.
							for (int i = 0; i < size; i++) {
								ChatClient cc = vc.get(i);
								out.write((ChatConstance.SC_CONNECT + "||" + cc.name + "\n").getBytes());
							}
						}break;
						case ChatConstance.CS_ALL :{
//							200|안녕하세요
							String tmp = st.nextToken();//안녕하세요..
							broadcast(ChatConstance.SC_MESSAGE + "||" + name + "] " + tmp);//200||안효인] 안녕하세요
						}break;
						case ChatConstance.CS_TO :{
//							250||홍길동||머해??
							String to = st.nextToken();//귓속말받을사람 : 홍길동
							String tmp = st.nextToken();//귓속말 : 머해???
							int size = vc.size();
							for (int i = 0; i < size; i++) {
								ChatClient cc = vc.get(i);
								if(to.equals(cc.name)) {
									cc.unicast(ChatConstance.SC_MESSAGE + "||♡" + name + "♡ " + tmp);
									break;
								}
							}
						}break;
						case ChatConstance.CS_PAPER :{
							
						}break;
						case ChatConstance.CS_RENAME :{
							
						}break;
						case ChatConstance.CS_DISCONNECT :{
							
						}break;
					}
				} catch (IOException e) {
					e.printStackTrace();
					flag = false;
					break;
				}
			}
		}
		
		private void broadcast(String msg) throws IOException {
			int size = vc.size();
			for(int i=0;i<size;i++) {
				ChatClient cc = vc.get(i);
				cc.unicast(msg);
			}
		}
		
		private void unicast(String msg) throws IOException {
			out.write((msg + "\n").getBytes());
		}
	}
	
	public static void main(String[] args) {
//		ChatServer cs = new ChatServer();
//		Thread t = new Thread(cs);
//		t.start();
		new Thread(new ChatServer()).start();
	}
	
}
