/**
 * 上下：加
 */
$(".control-top").click(function () {
    let y;
    if (parseFloat($("#y").val()) < parseFloat(1.00)) {
        y = parseFloat($("#y").val()) + parseFloat(0.1);
    } else {
        y = parseFloat(1.00);
    }
    $("#y").val(y.toFixed(1));
    ajax();
})

/**
 * 上下：减
 */
$(".control-bottom").click(function () {
    let y;
    if (parseFloat($("#y").val()) > parseFloat(-1.00)) {
        y = parseFloat($("#y").val()) - parseFloat(0.1);
    } else {
        y = parseFloat(-1.00);
    }
    $("#y").val(y.toFixed(1));
    ajax();
})


/**
 * 左右：加
 */
$(".control-right").click(function () {
    let x;
    if (parseFloat($("#x").val()) < parseFloat(1.00)) {
        x = parseFloat($("#x").val()) + parseFloat(0.1);
    } else {
        x = parseFloat(1.00);
    }
    $("#x").val(x.toFixed(1));
    ajax();
})

/**
 * 左右：减
 */
$(".control-left").click(function () {
    let x;
    if (parseFloat($("#x").val()) > parseFloat(-1.00)) {
        x = parseFloat($("#x").val()) - parseFloat(0.1);
    } else {
        x = parseFloat(-1.00);
    }
    $("#x").val(x.toFixed(1));
    ajax();
})


/**
 * 大小：加
 */
$(".max").click(function () {
    let z;
    if (parseFloat($("#z").val()) < parseFloat(1.00)) {
        z = parseFloat($("#z").val()) + parseFloat(0.1);
    } else {
        z = parseFloat(1.00);
    }
    $("#z").val(z.toFixed(1))
    ajax();
})

/**
 * 大小：减
 */
$(".min").click(function () {
    let z;
    if (parseFloat($("#z").val()) > parseFloat(-1.00)) {
        z = parseFloat($("#z").val()) - parseFloat(0.1);
    } else {
        z = parseFloat(-1.00);
    }
    $("#z").val(z.toFixed(1));
    ajax();
})

/**
 * OK
 */
$(".ok").click(function () {
    ajax();
})

/**
 * 后台传参
 */
function ajax() {
    $.ajax({
        type: 'post',
        url: '/onvif/adjust',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data()),
        success: function (res) {
            console.log(res)
        }
        , error: function () {
            alert("操作失败")
        }
    });
}

/**
 * 获取数据
 */
var data = function () {
    var data = {
        y: $("#y").val(),
        z: $("#z").val(),
        x: $("#x").val()
    }
    return data;
}