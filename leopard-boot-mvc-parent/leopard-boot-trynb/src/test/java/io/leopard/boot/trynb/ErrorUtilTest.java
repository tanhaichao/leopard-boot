package io.leopard.boot.trynb;

import org.junit.Assert;
import org.junit.Test;

public class ErrorUtilTest {

	@Test
	public void fillterDebugInfo() {
		String str = "[x[[aa]xx]";
		String ddd = "[x[[aa]xx]";

		str = "你好[ddd]aa";
		String msg = "你好[" + str + "]ddd,你[" + ddd + "]好";
		String msg2 = "你好2[" + str + "]ddd,你2[" + ddd + "]好";
		String msg3 = "你dddddd好2[" + str + "]ddd,你2[" + ddd + "]好";
		// 你[abc]]好[[a[[[addd]]a]]啊

		// 你[abc]]好啊
		Assert.assertEquals("你好啊", ErrorUtil.fillterDebugInfo("你[abc]好啊"));
		Assert.assertEquals("你好啊", ErrorUtil.fillterDebugInfo("你[[abc]]好啊"));
		Assert.assertEquals("你好啊", ErrorUtil.fillterDebugInfo("你[[abc]]好啊"));
		Assert.assertEquals("解析错误消息出错", ErrorUtil.fillterDebugInfo("你[[abc]好啊"));
		Assert.assertEquals("解析错误消息出错", ErrorUtil.fillterDebugInfo("你[abc]]好啊"));
		Assert.assertEquals("解析错误消息出错", ErrorUtil.fillterDebugInfo("你[abc]]好[a]啊"));
		Assert.assertEquals("解析错误消息出错", ErrorUtil.fillterDebugInfo("你[abc]]好[a]]啊"));
		Assert.assertEquals("解析错误消息出错", ErrorUtil.fillterDebugInfo("你[abc]]好[[a[[[addd]]a]]啊"));

		Assert.assertEquals("解析错误消息出错", ErrorUtil.fillterDebugInfo("你[]]好[abc]]好[[a[[[addd]]a]]啊"));
		Assert.assertEquals("你好好啊", ErrorUtil.fillterDebugInfo("你好好啊"));
	}

}