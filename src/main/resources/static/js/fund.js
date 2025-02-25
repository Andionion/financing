$(function () {
    $(".card").click(function () {
        const name = $(this).attr('data-name');
        window.location.href = '/financing/fund/trade/calculate/' + encodeURIComponent(name);
    });

    // 初始化 flatpickr 日期选择器
    flatpickr("#tradeDate", {
        dateFormat: "Y-m-d", // 设置日期格式为 YYYY-MM-DD
        defaultDate: new Date(), // 默认日期为当天
        locale: "zh", // 设置语言为中文
    });

    // 设置交易类型下拉框默认值
    $("#tradeType").val("purchase");

    // 表单提交事件
    $("#transactionForm").submit(function (event) {
        event.preventDefault();

        // 获取表单数据
        const fundCode = $("#fundCode").val();
        const belong = $("#belong").val();
        const tradeDate = $("#tradeDate").val(); // 直接获取 flatpickr 的值
        const amount = parseFloat($("#amount").val());
        const share = parseFloat($("#share").val());
        const tradeType = $("#tradeType").val(); // 获取下拉框选中的值

        // 校验交易类型是否为空
        if (!tradeType) {
            alert("请选择交易类型！");
            return;
        }

        // 格式化日期为 YYYYMMDD
        const formattedDate = formatDateToYYYYMMDD(tradeDate);

        // 构建请求体
        const requestBody = {
            fundCode: fundCode,
            belong: belong,
            list: [{
                tradeDate: formattedDate,
                amount: amount,
                share: share,
                tradeType: tradeType // 将交易类型加入请求体
            }]
        };

        // 发送 AJAX 请求
        $.ajax({
            url: '/financing/fund/trade/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(requestBody),
            success: function (response) {
                alert("交易记录提交成功！");
                $("#transactionForm")[0].reset(); // 重置表单
            },
            error: function (xhr, status, error) {
                alert("交易记录提交失败，请重试。");
            }
        });
    });

    // 格式化日期函数
    function formatDateToYYYYMMDD(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}${month}${day}`;
    }
});