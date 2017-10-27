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
			System.out.println("Client ���� �����......");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// client ���� ó�� Thread
	@Override
	public void run() {
		boolean flag = true;
		while (flag) {
			try {
				Socket s = ss.accept(); // Ŭ���̾�Ʈ�� ���ö����� ���̰ɸ�.
				System.out.println("Client ���� ����!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class ChatClient extends Thread { // �̳�Ŭ����
		String name; // Ŭ���̾�Ʈ�� ��ȭ��
		BufferedReader in;
		OutputStream out;

		// client�� �޼��� ó�� Thread
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
