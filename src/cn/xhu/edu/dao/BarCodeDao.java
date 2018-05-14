package cn.xhu.edu.dao;



import java.sql.SQLException;
import java.util.List;

import cn.xhu.edu.domain.Barcode;

public class BarCodeDao extends BaseDao {
    public boolean checkBarcode(String content) {
        String sql = "select count(*) as count from tb_barcode where content=?";
        int count;
        try {
            count = super.count(sql, new Object[]{content});
        } catch (SQLException e) {
            throw new RuntimeException("根据content查询条形码失�?");
        }
        return count == 1;
    }

    public List<Barcode> findAllBaecodes() {
        String sql = "select id,content from tb_barcode";
        List<Barcode> barcodeList;
        try {
            barcodeList = super.query(sql, null, Barcode.class);
        } catch (Exception e) {
            throw new RuntimeException("查询�?有条形码失败");
        }
        return barcodeList;
    }

    public boolean addBarcode(String content) {
        int count;
        String sql = "insert into tb_barcode values(null,?)";
        try {
            count = super.update(sql, new Object[]{content});
        } catch (SQLException e) {
            throw new RuntimeException("添加条形码失�?");
        }
        return count == 1;
    }
}
