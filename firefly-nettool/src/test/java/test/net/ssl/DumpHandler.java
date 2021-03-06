package test.net.ssl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import test.net.tcp.SendFileHandler;

import com.firefly.net.Handler;
import com.firefly.net.Session;
import com.firefly.net.tcp.ssl.SSLContextFactory;
import com.firefly.net.tcp.ssl.SSLSession;

public class DumpHandler implements Handler {
	private SSLContext sslContext;
	
	public DumpHandler() throws Throwable {
		sslContext = SSLContextFactory.getSSLContext();
	}

	@Override
	public void sessionOpened(Session session) throws Throwable {
		session.setAttribute("_secure", new SSLSession(sslContext, session));

	}

	@Override
	public void sessionClosed(Session session) throws Throwable {
		System.out.println("session close: " + session.getSessionId());
	}

	@Override
	public void messageRecieved(Session session, Object message)
			throws Throwable {
//		StringBuilder s = new StringBuilder(20000);
//		s.append("hello world!\r\n ----");
//		for (int i = 0; i < 20000; i++) {
//			s.append("c");
//		}
//		s.append("----");
//		session.encode(s.toString());
		
		
		File file = new File(SendFileHandler.class.getResource("/index.html").toURI());
		session.setAttribute("Content-Length", file.length());
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		session.encode(raf.getChannel());
	}

	@Override
	public void exceptionCaught(Session session, Throwable t) throws Throwable {
		t.printStackTrace();
	}
	
	public static void main(String[] args) throws Throwable {
		FileInputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			in = new FileInputStream(new File("/Users/qiupengtao", "fireflykeys"));
			out = new ByteArrayOutputStream();
			
			byte[] buf = new byte[1024];
			
			for (int i = 0; (i = in.read(buf)) != -1;) {
				byte[] temp = new byte[i];
				System.arraycopy(buf, 0, temp, 0, i);
				out.write(temp);
			}
			
			byte[] ret = out.toByteArray();
//			System.out.println(ret.length);
			System.out.println(Arrays.toString(ret));
			
		} finally {
			in.close();
			out.close();
		}
	}

}
