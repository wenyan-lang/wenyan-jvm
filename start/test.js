var 絕對 = Math.abs;
var 平方根 = Math.sqrt;
var 畫心 = () => 0;
畫心 = function(心語) {
    var _ans1 = 心語.length;
    var 長度 = _ans1;
    var 填充符 = "一";
    var 换行符 = "\n";
    var _ans2 = 13 / 10;
    var 乙 = _ans2;
    var _ans3 = -11 / 10;
    var 乙止 = _ans3;
    var _ans4 = 40 / 1000;
    var 甲步長 = _ans4;
    var _ans5 = 6 / 100;
    var 乙步長 = _ans5;
    var 輸出位置 = 1;
    var 果 = "";
    while (true) {
        if (乙 < 乙止) {
            break;
        };
        var _ans6 = -11 / 10;
        var 甲 = _ans6;
        var _ans7 = 11 / 10;
        var 甲止 = _ans7;
        var 本行 = "";
        while (true) {
            if (甲 > 甲止) {
                break;
            };
            var _ans8 = 絕對(甲);
            var 甲絕對 = _ans8;
            var _ans9 = 平方根(甲絕對);
            var 減數 = _ans9;
            var _ans10 = 乙 * 5;
            var _ans11 = _ans10 / 4;
            var 被減數 = _ans11;
            var _ans12 = 被減數 - 減數;
            var 差 = _ans12;
            var _ans13 = 差 * 差;
            var 加數 = _ans13;
            var _ans14 = 甲 * 甲;
            var _ans15 = _ans14 + 加數;
            var _ans16 = _ans15 - 1;
            var 函數值 = _ans16;
            if (函數值 <= 0) {
                var _ans17 = 心語[輸出位置 - 1];
                var 字 = _ans17;
                var _ans18 = 本行 + 字;
                本行 = _ans18;
                var _ans19 = 輸出位置 % 長度;
                var _ans20 = _ans19 + 1;
                輸出位置 = _ans20;
            } else {
                var _ans21 = 本行 + 填充符;
                本行 = _ans21;
            };
            var _ans22 = 甲 + 甲步長;
            甲 = _ans22;
        };
        var _ans23 = 乙 - 乙步長;
        乙 = _ans23;
        var _ans24 = 本行 + 换行符;
        本行 = _ans24;
        var _ans25 = 果 + 本行;
        果 = _ans25;
    };
    var _ans26 = 果;
    console.log(_ans26);
};
var _ans27 = 畫心("琉璃梳子撫青絲。畫心牽腸癡不癡。");