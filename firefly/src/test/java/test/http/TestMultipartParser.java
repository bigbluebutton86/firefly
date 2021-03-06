package test.http;

import static org.hamcrest.Matchers.is;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Part;

import org.junit.Assert;
import org.junit.Test;

import com.firefly.server.ServerBootstrap;
import com.firefly.server.http.MultipartFormData;
import com.firefly.server.http.MultipartFormDataParser;
import com.firefly.server.http.PartImpl;

public class TestMultipartParser {

	@Test
	public void testMultipartParser() throws Throwable {
		PartImpl.tempdir = new File(ServerBootstrap.class.getResource("/page/template/_firefly_tmpdir").toURI()).getAbsolutePath();
		File testFile = new File(ServerBootstrap.class.getResource("/testMutilpart.txt").toURI());
		
		final BufferedInputStream in = new BufferedInputStream(new FileInputStream(testFile));
		Collection<Part> col = null;
		try {
			col = MultipartFormDataParser.parse(new ServletInputStream() {
			
				@Override
				public int read() throws IOException {
					return in.read();
				}
				
				@Override
				public int available() throws IOException {
					return in.available();
				}
	
				@Override
				public void close() throws IOException {
					in.close();
				}
	
				public int read(byte[] b, int off, int len) throws IOException {
					return in.read(b, off, len);
				}
			}, "multipart/form-data; boundary=----WebKitFormBoundaryo6OWJFZoG8w62LBM", "UTF-8");
		} finally {
			in.close();
		}
		
		MultipartFormData m = new MultipartFormData(col);
		try {
			Assert.assertThat(col.size(), is(4));
			Assert.assertThat(m.getPart("name").getName(), is("name"));
			Assert.assertThat(m.getPart("num").getSize(), is(9L));
			Assert.assertThat(m.getPart("content1").getSize(), is(19L));
			Assert.assertThat(m.getPart("content2").getSize(), is(20L));
			System.out.println(m.getPart("content1").getSize());
			System.out.println(m.getPart("content2").getSize());
		} finally {
			m.close();
		}
	}
	
	public static void main(String[] args) throws Throwable {
		new TestMultipartParser().testMultipartParser();
	}
}
