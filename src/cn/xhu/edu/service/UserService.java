package cn.xhu.edu.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xhu.edu.consts.NameConsts;
import cn.xhu.edu.dao.BarCodeDao;
import cn.xhu.edu.domain.Barcode;
import cn.xhu.edu.util.EditDistanceUtil;

public class UserService {
    //添加条码
    public static boolean addBarCode(String content) {
        BarCodeDao dao = new BarCodeDao();
        System.out.println(dao.checkBarcode(content));
        if (!dao.checkBarcode(content)) {
            if (dao.addBarcode(content)) {
                return true;
            }
        }
        return false;
    }

    //查询条码
    public static Map<String, List<String>> queryBarcodes(String contents) {
        BarCodeDao dao = new BarCodeDao();
        List<String> existBarcodeList = new ArrayList<>();
        List<String> notExistBarcodeList = new ArrayList<>();
        Map<String, List<String>> resultMap = new HashMap<>();
        String[] contentArray = contents.split("\n");
        for (String content : contentArray) {
            if (dao.checkBarcode(content)) {
                existBarcodeList.add(content);
            } else {
                notExistBarcodeList.add(content);
            }
        }
        resultMap.put("exist", existBarcodeList);
        resultMap.put("not_exist", notExistBarcodeList);
        return resultMap;
    }

    //查询相似条码
    public static Map<String, List<String>> querySimilarBarcodes(String contents) {
        class Inner {
            void deal(List<String> dealList, String name) {
                for (Barcode barcode : listAllBarcodes()) {
                    if (EditDistanceUtil.calculate(barcode.getContent(), name) == 2) {
                        dealList.add(barcode.getContent());
                    }
                }
            }
        }
        BarCodeDao dao = new BarCodeDao();
        List<String> fruitList = new ArrayList<>();
        List<String> electricalList = new ArrayList<>();
        List<String> drinkList = new ArrayList<>();
        List<String> grainAndOilList = new ArrayList<>();
        Map<String, List<String>> resultMap = new HashMap<>();
        String[] contentArray = contents.split("\n");
        boolean isFruitDeal = false, isElectricalDeal = false, isDrinkDeal = false, isGrainAndOilDeal = false;
        for (String content : contentArray) {
            if (EditDistanceUtil.calculate(content, NameConsts.FRUIT) == 2) {
                if (!isFruitDeal) {
                    new Inner().deal(fruitList, NameConsts.FRUIT);
                    isFruitDeal = true;
                }
            } else if (EditDistanceUtil.calculate(content, NameConsts.ELECTRICAL) == 2) {
                if (!isElectricalDeal) {
                    new Inner().deal(electricalList, NameConsts.ELECTRICAL);
                    isElectricalDeal = true;
                }
            } else if (EditDistanceUtil.calculate(content, NameConsts.DRINK) == 2) {
                if (!isDrinkDeal) {
                    new Inner().deal(drinkList, NameConsts.DRINK);
                    isDrinkDeal = true;
                }
            } else if (EditDistanceUtil.calculate(content, NameConsts.GRAIN_AND_OIL) == 2) {
                if (!isGrainAndOilDeal) {
                    new Inner().deal(grainAndOilList, NameConsts.GRAIN_AND_OIL);
                    isGrainAndOilDeal = true;
                }
            }
        }
        resultMap.put("fruit", fruitList);
        resultMap.put("electrical", electricalList);
        resultMap.put("drink", drinkList);
        resultMap.put("grain_oil", grainAndOilList);
        return resultMap;
    }


    //查询所有条码
    public static List<Barcode> listAllBarcodes() {
        return new BarCodeDao().findAllBaecodes();
    }
}
