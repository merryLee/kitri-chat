package com.kitri.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import com.kitri.chat.util.ChatConstance;

public class ChatServer implements Runnable {

	ServerSocket ss;
	Vector<ChatClient> vc = new Vector<ChatClient>();

	public ChatServer() {
		try {
			ss = new ServerSocket(ChatConstance.PORT);
			System.out.println("Client 접속 대기중......");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// client 접속 처리 Thread
	@Override
	public void run() {
		boolean flag = true;
		while (flag) {
			try {
				Socket s = ss.accept(); // 클라이언트가 들어올때까지 락이걸림.
				System.out.println("Client 접속 성공!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class ChatClient extends Thread { // 이너클래스
		String name; // 클라이언트의 대화명
		BufferedReader in;
		OutputStream out;

		// client의 메세지 처리 Thread
		@Override
		public void run() {

		}
	}

	public static void main(String[] args) {
		// ChatServer cs = new ChatServer();
		// Thread t = new Thread(cs);
		// t.start();
		new Thread(new ChatServer()).start();
	}
}
