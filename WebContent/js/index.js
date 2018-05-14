function start() {
    clear_area();
    $.ajax({
        type: "post",
        url: "user/query_barcodes",
        dataType: "json",
        data: {
            contents: $("#input_area").val(),
        },
        success: function (data) {
            if (data.code == 0) {
                var jsonData = data.data;
                //匹配到的
                var exist = jsonData.exist;
                $.each(exist, function (i, item) {
                    $("#exist_area").append(item + "\n");
                });
                //未匹配到的
                var not_exist = jsonData.not_exist;
                $.each(not_exist, function (i, item) {
                    $("#not_exist_area").append(item + "\n");
                });
            } else {
                alert(data.message);
            }
        },
        error: function () {
            alert("系统繁忙");
        }
    });
    similar();
}

function similar() {
    $.ajax({
        type: "post",
        url: "user/query_similar",
        dataType: "json",
        data: {
            contents: $("#input_area").val(),
        },
        success: function (data) {
            if (data.code == 0) {
                var jsonData = data.data;
                var fruit = jsonData.fruit;
                $.each(fruit, function (i, item) {
                    $("#fruit_area").append(item + "\n");
                });

                var electrical = jsonData.electrical;
                $.each(electrical, function (i, item) {
                    $("#electrical_area").append(item + "\n");
                });

                var drink = jsonData.drink;
                $.each(drink, function (i, item) {
                    $("#drink_area").append(item + "\n");
                });

                var grain_oil = jsonData.grain_oil;
                $.each(grain_oil, function (i, item) {
                    $("#grain_oil_area").append(item + "\n");
                });
            } else {
                alert(data.message);
            }
        },
        error: function () {
            alert("系统繁忙");
        }
    });
}

function clear_area() {
    clear_query();
    clear_similar();
}

function clear_query() {

    $("#exist_area").text("");
    $("#not_exist_area").text("");
}

function clear_similar() {
    $("#fruit_area").text("");
    $("#electrical_area").text("");
    $("#drink_area").text("");
    $("#grain_oil_area").text("");
}