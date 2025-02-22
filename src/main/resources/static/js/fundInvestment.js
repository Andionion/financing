$(function () {
    $(".btn-purchase-fund").click(function () {
        const belong = this.getAttribute('data-belong');
        console.log("belong:", belong);
        window.location.href = '/financing/fund/investment/view/bond/calculate/' + belong;
    });
});