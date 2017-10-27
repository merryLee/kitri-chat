package com.kitri.chat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.kitri.chat.util.ChatConstance;

public class ChatService implements ActionListener {

	Login login;

	public ChatService(Login login) {
		this.login = login;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == login.nameTf || obj == login.ok) {
			connectProcess();	
		} else if (obj == login.cancel) {
			System.exit(0);
		}

	}

	private void connectProcess() {
		String host = login.ipTf.getText().trim();
		try {
			Socket s = new Socket(host, ChatConstance.PORT);
			login.setVisible(false);
			login.chat.setVisible(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
