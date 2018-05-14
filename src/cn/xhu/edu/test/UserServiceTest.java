package cn.xhu.edu.test;

import org.junit.Test;

import com.google.gson.Gson;

import cn.xhu.edu.consts.NameConsts;
import cn.xhu.edu.dao.BarCodeDao;
import cn.xhu.edu.service.UserService;
import cn.xhu.edu.util.EditDistanceUtil;

public class UserServiceTest {
	UserService service = new UserService();
	BarCodeDao dao = new BarCodeDao();

	@Test
	public void test1() {
		System.out.println(new Gson().toJson(new UserService().listAllBarcodes()));
	}

	@Test
	public void testCheck() {
		System.out.println(dao.checkBarcode("4569012851220"));
	}

	@Test
	public void test2() {
		String contents = "4569012851220\n4569012851221\n4569012851223\njd";
		String[] contentArray = contents.split(" ");
		for (String content : contentArray) {
			System.out.println(content);
		}
	}

	@Test
	public void test3() {
		String contents = "4569012851220\n4569012851221\n4569012851223\njd";
		System.out.println(new Gson().toJson(service.queryBarcodes(contents)));
	}

	@Test
	public void test4() {
		String contents = "4569012851256\n3278453199199\n3278453199197\nnjd";
		String[] contentArray = contents.split("\n");
		for (String content : contentArray) {
			System.out.println(content);
			System.out.println(EditDistanceUtil.calculate(content, NameConsts.DRINK));
		}
		System.out.println(new Gson().toJson(service.querySimilarBarcodes(contents)));
	}
}
