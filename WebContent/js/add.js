function add_barcode() {
    $.ajax({
        type: "post",
        url: "user/add_barcode",
        dataType: "json",
        data: {
            content: $("#input_add").val(),
        },
        success(data) {
            if (data.code == 0) {
                alert("添加成功");
            } else {
                alert(data.message);
            }
        },
        error() {
            alert("系统繁忙");
        }
    });
}