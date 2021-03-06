package io.leopard.httpnb;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Httpnb {

	static {
		setAllowRestrictedHeaders(true);
	}

	/**
	 * 打开Http Header安全限制</br>
	 * 
	 * @param allowRestrictedHeaders true
	 */
	public static void setAllowRestrictedHeaders(boolean allowRestrictedHeaders) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", allowRestrictedHeaders + "");
	}

	public static String doDelete(String url) {
		return doDelete(url, -1);
	}

	public static String doDelete(String url, long timeout) {
		HttpHeader header = new HttpHeaderDeleteImpl(timeout);
		try {
			HttpURLConnection conn = header.openConnection(url);
			return execute(conn, null);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	public static String doGet(String url) {
		return doGet(url, -1);
	}

	public static String doGet(String url, long timeout) {
		return doGet(url, timeout, null);
	}

	public static String doGet(String url, long timeout, String charsetName) {
		HttpHeader header = new HttpHeaderGetImpl(timeout);
		try {
			HttpURLConnection conn = header.openConnection(url);
			return execute(conn, charsetName);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	public static String doGet(String url, Proxy proxy, long timeout) {
		return doGet(url, proxy, timeout, null);
	}

	public static String doGet(String url, Proxy proxy, long timeout, Map<String, Object> params) {
		return doGet(url, proxy, timeout, params, null);
	}

	public static String doGet(String url, Proxy proxy, Map<String, Object> params) {
		return doGet(url, proxy, -1, params, null);
	}

	public static String doGet(String url, Proxy proxy, long timeout, Map<String, Object> params, String charsetName) {
		HttpHeader header = new HttpHeaderGetImpl(timeout);
		header.setProxy(proxy);
		if (params != null) {
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				header.addParam(new Param(entry.getKey(), entry.getValue()));
			}
		}
		try {
			HttpURLConnection conn = header.openConnection(url);
			return execute(conn, charsetName);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	public static InputStream doGetForInputStream(String url, long timeout) throws IOException {
		return doGetForInputStream(url, null, timeout);
	}

	public static InputStream doGetForInputStream(String url, Proxy proxy, long timeout) throws IOException {
		HttpHeader header = new HttpHeaderGetImpl(timeout);
		header.setProxy(proxy);
		HttpURLConnection conn = header.openConnection(url);
		InputStream input = conn.getInputStream();
		// byte[] bytes = IOUtils.toByteArray(input);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = input.read(data, 0, 1024)) != -1) {
			output.write(data, 0, count);
		}
		data = null;

		input.close();
		conn.disconnect();
		return new ByteArrayInputStream(output.toByteArray());
	}

	public static String doPost(String url, Param... params) {
		return doPost(url, -1, params);
	}

	public static String doPost(String url, long timeout, Param... params) {
		HttpHeader header = new HttpHeaderPostImpl(timeout);
		for (Param param : params) {
			header.addParam(param);
		}
		try {
			HttpURLConnection conn = header.openConnection(url);
			return execute(conn, null);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	public static String doPost(String url, Map<String, Object> map) {
		return doPost(url, null, map);
	}

	public static String doPost(String url, Proxy proxy, Map<String, Object> map) {
		return doPost(url, proxy, -1, map);
	}

	public static String doPost(String url, long timeout, Map<String, Object> map) {
		return doPost(url, null, timeout, map);
	}

	public static String doPost(String url, Proxy proxy, long timeout, Map<String, Object> map) {
		return doPost(url, proxy, timeout, map, null);
	}

	public static String doPost(String url, Proxy proxy, Map<String, Object> map, String body) {
		return doPost(url, proxy, -1, map, body);
	}

	public static String doPost(String url, Proxy proxy, long timeout, Map<String, Object> params, String body) {
		HttpHeader header = new HttpHeaderPostImpl(timeout);
		header.setProxy(proxy);
		if (params != null) {
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				header.addParam(new Param(entry.getKey(), entry.getValue()));
			}
		}

		try {
			HttpURLConnection conn = header.openConnection(url);
			if (body != null) {
				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
				osw.write(body);
				osw.flush();
				osw.close();
				os.close();
			}
			return execute(conn, null);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	public static InputStream doPostForInputStream(String url, Proxy proxy, Map<String, Object> params, String body) throws IOException {
		return doPostForInputStream(url, proxy, -1, params, body);
	}

	public static InputStream doPostForInputStream(String url, Proxy proxy, long timeout, Map<String, Object> params, String body) throws IOException {

		HttpHeader header = new HttpHeaderPostImpl(timeout);
		header.setProxy(proxy);
		if (params != null) {
			Iterator<Entry<String, Object>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				header.addParam(new Param(entry.getKey(), entry.getValue()));
			}
		}

		HttpURLConnection conn = header.openConnection(url);
		if (body != null) {
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(body);
			osw.flush();
			osw.close();
			os.close();
		}
		InputStream input = conn.getInputStream();
		// byte[] bytes = IOUtils.toByteArray(input);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = input.read(data, 0, 1024)) != -1) {
			output.write(data, 0, count);
		}
		data = null;

		input.close();
		conn.disconnect();
		return new ByteArrayInputStream(output.toByteArray());
	}

	public static String execute(String url, HttpHeader header) {
		try {
			HttpURLConnection conn = header.openConnection(url);
			return execute(conn, null);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}

	public static String execute(String url, HttpHeader header, String body) {
		try {
			HttpURLConnection conn = header.openConnection(url);
			if (body != null) {
				OutputStream os = conn.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
				osw.write(body);
				osw.flush();
				osw.close();
				os.close();
			}
			return execute(conn, null);
		}
		catch (IOException e) {
			throw new HttpException(e, header);
		}
	}
	// public static String doUpload(String url, String path) {
	// HttpHeader header = new HttpHeaderPostImpl(-1);
	// try {
	// HttpURLConnection conn = header.openConnection(url);
	// upload(path, conn);
	// return execute(conn);
	// }
	// catch (IOException e) {
	// throw new HttpException(e, header);
	// }
	// }
	//
	// public static void upload(String path, HttpURLConnection conn) throws IOException {
	// conn.setRequestProperty("Connection", "Keep-Alive");
	// conn.setRequestProperty("Charset", "UTF-8");
	// conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
	//
	// String end = "\r\n";
	// String twoHyphens = "--";
	// String boundary = "*****";
	//
	// /* 设置DataOutputStream */
	// DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
	// ds.writeBytes(twoHyphens + boundary + end);
	// ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\"" + path + "\"\r\n");
	// ds.writeBytes(end);
	// /* 取得文件的FileInputStream */
	// FileInputStream fStream = new FileInputStream(path);
	// /* 设置每次写入1024bytes */
	// int bufferSize = 1024;
	// byte[] buffer = new byte[bufferSize];
	// int length = -1;
	// /* 从文件读取数据至缓冲区 */
	// while ((length = fStream.read(buffer)) != -1) {
	// /* 将资料写入DataOutputStream中 */
	// ds.write(buffer, 0, length);
	// }
	// ds.writeBytes(end);
	// ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	// /* close streams */
	// fStream.close();
	// ds.flush();
	// }

	// public static String execute(HttpURLConnection conn) throws IOException {
	// return execute(conn, null);
	// }

	public static String execute(HttpURLConnection conn, String charsetName) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader reader;
		if (charsetName == null) {
			reader = new InputStreamReader(conn.getInputStream());
		}
		else {
			reader = new InputStreamReader(conn.getInputStream(), charsetName);
		}
		BufferedReader in = new BufferedReader(reader);
		String str;
		while ((str = in.readLine()) != null) {
			if (sb.length() > 0) {
				sb.append('\n');
			}
			sb.append(str);
		}
		in.close();
		conn.disconnect();
		return sb.toString();
	}

	public static Proxy newHttpProxy(String hostAndPort) {
		if (hostAndPort == null || hostAndPort.length() == 0) {
			return null;
		}
		return new Proxy(Proxy.Type.HTTP, Httpnb.newInetSocketAddress(hostAndPort));
	}

	/**
	 * 
	 * 
	 * @param hostAndPort 格式 IP:port
	 * @return
	 */
	public static InetSocketAddress newInetSocketAddress(String hostAndPort) {
		if (hostAndPort == null || hostAndPort.length() == 0) {
			throw new IllegalArgumentException("hostAndPort格式不正确");
		}
		String[] strs = hostAndPort.split(":");
		if (strs.length != 2) {
			throw new IllegalArgumentException("hostAndPort格式不正确");
		}
		String host = strs[0].trim();
		int port = Integer.parseInt(strs[1].trim());
		return new InetSocketAddress(host, port);
	}
}
