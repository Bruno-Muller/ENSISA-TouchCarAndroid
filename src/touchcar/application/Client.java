package touchcar.application;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import car.can.CanMessage;

public class Client implements Runnable {

	private Controller controller;
	private Socket socket;
	private boolean terminating;

	public Client(String ip, int port, Controller controller) throws UnknownHostException, IOException {
				
		this.controller = controller;
		this.socket = new Socket(ip, port);
		this.terminating = false;
	}

	public void run() {
		try {
			byte bytes[] = new byte[6];
			while (this.socket.getInputStream().read(bytes,0,6) != -1) {
				this.controller.acceptCanMessage(new CanMessage(bytes));
			}

		} catch (IOException e) {
			if (!this.terminating)
				e.printStackTrace();
		}
	}

	public void close() throws IOException {
		this.terminating = true;
		this.socket.close();
	}

	public void sendCanMessage(CanMessage canMessage) {
		try {
			OutputStream outputStream = this.socket.getOutputStream();
			outputStream.write(canMessage.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
