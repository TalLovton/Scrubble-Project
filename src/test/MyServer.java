
package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private boolean stopFlag;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
    }

    public void start() {
        this.stopFlag = false;
        new Thread(()->this.runServer()).start();
    }

    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(this.port);
            server.setSoTimeout(1000);
            while(!stopFlag){
                try {
                    Socket tmpClient = server.accept();
                    this.ch.handleClient(tmpClient.getInputStream(), tmpClient.getOutputStream());
                    this.ch.close();
                    tmpClient.close();
                }
                catch (SocketTimeoutException var3) {}

            }
            server.close();
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void close() {
        this.stopFlag = true;
    }
}
