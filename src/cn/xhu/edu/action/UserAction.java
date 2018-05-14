package cn.xhu.edu.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.xhu.edu.domain.MyResponse;
import cn.xhu.edu.service.UserService;

@WebServlet(urlPatterns = "/user/*")
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	// 新增条码
	public void add_barcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MyResponse myResponse = new MyResponse();
		String content = request.getParameter("content");
		if (new UserService().addBarCode(content)) {
			myResponse.setCode(0);
			myResponse.setMessage("添加成功");
		} else {
			myResponse.setCode(1);
			myResponse.setMessage("该条形码已存�?");
		}
		response.getWriter().print(new Gson().toJson(myResponse));
	}

	// 匹配条码
	public void query_barcodes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MyResponse myResponse = new MyResponse();
		String contents = request.getParameter("contents");
		Map<String, List<String>> resultMap = new UserService().queryBarcodes(contents);
		myResponse.setCode(0);
		myResponse.setMessage("查询成功");
		myResponse.setData(resultMap);
		response.getWriter().print(new Gson().toJson(myResponse));
	}

	// 匹配相似条码
	public void query_similar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MyResponse myResponse = new MyResponse();
		String contents = request.getParameter("contents");
		Map<String, List<String>> resultMap = new UserService().querySimilarBarcodes(contents);
		myResponse.setCode(0);
		myResponse.setMessage("查询成功");
		myResponse.setData(resultMap);
		response.getWriter().print(new Gson().toJson(myResponse));
	}
}
