/* module 格物 is hidden */
var 取物 = 格物.取物;
var 置物 = 格物.置物;
var 列物之端 = 格物.列物之端;
var 識類 = 格物.識類;
var 引號 = "「」";
var _ans1 = 引號[1 - 1];
var 引起 = _ans1;
var _ans2 = 引號[2 - 1];
var 引迄 = _ans2;
var 位名 = [];
位名.push("〇", "一", "二", "三", "四", "五", "六", "七", "八", "九");
var 斬渾沌 = () => 0;
斬渾沌 = function(渾沌語) {
    var 諸咒 = [];
    諸咒.push("物", "言", "數", "爻", "列", "之", "也");
    var 渾沌碎 = [];
    var 讀 = 1;
    var 層 = 0;
    var 辭 = "";
    while (true) {
        if (讀 > 渾沌語.length) {
            break;
        };
        if (渾沌語[讀 - 1] == 引起) {
            if (層 != 0) {
                var _ans3 = 渾沌語[讀 - 1];
                var _ans4 = 辭 + _ans3;
                辭 = _ans4;
            };
            var _ans5 = 層 + 1;
            層 = _ans5;
        } else if (渾沌語[讀 - 1] == 引迄) {
            var _ans6 = 層 - 1;
            層 = _ans6;
            if (層 == 0) {
                渾沌碎.push(辭);
                辭 = "";
            } else {
                var _ans7 = 渾沌語[讀 - 1];
                var _ans8 = 辭 + _ans7;
                辭 = _ans8;
            };
        } else if (層 > 0) {
            var _ans9 = 渾沌語[讀 - 1];
            var _ans10 = 辭 + _ans9;
            辭 = _ans10;
        } else {
            for (var 咒 of 諸咒) {
                if (渾沌語[讀 - 1] == 咒) {
                    渾沌碎.push(咒);
                    break;
                };
            };
        };
        var _ans11 = 1 + 讀;
        讀 = _ans11;
    };
    return 渾沌碎;
};
var 食渾沌 = this.食渾沌 = () => 0;
食渾沌 = this.食渾沌 = function(渾沌語) {
    var _ans12 = 斬渾沌(渾沌語);
    var 渾沌碎 = _ans12;
    var 食數 = () => 0;
    var 食列 = () => 0;
    var 食物 = () => 0;
    var 食數 = () => 0;
    食數 = function(數名) {
        var 正負 = 1;
        var _ans13 = 數名[1 - 1];
        if (_ans13 == "負") {
            var _ans14 = 數名.slice(1);
            數名 = _ans14;
            正負 = -1;
        };
        var 整 = 0;
        var 小 = 0;
        var 讀 = 1;
        var 小長 = 1;
        var 小耶 = false;
        while (true) {
            if (讀 > 數名.length) {
                break;
            };
            if (讀 == "·") {
                小耶 = true;
            } else {
                var 位 = 1;
                while (true) {
                    if (位 > 位名.length) {
                        break;
                    };
                    if (位名[位 - 1] == 數名[讀 - 1]) {
                        break;
                    };
                    var _ans15 = 1 + 位;
                    位 = _ans15;
                };
                var _ans16 = 位 - 1;
                位 = _ans16;
                if (小耶) {
                    var _ans17 = 小 * 10;
                    var _ans18 = _ans17 + 位;
                    小 = _ans18;
                    var _ans19 = 小長 + 1;
                    小長 = _ans19;
                } else {
                    var _ans20 = 整 * 10;
                    var _ans21 = _ans20 + 位;
                    整 = _ans21;
                };
            };
            var _ans22 = 1 + 讀;
            讀 = _ans22;
        };
        for (var _rand1 = 0; _rand1 < 小長; _rand1++) {
            var _ans23 = 小 * 0.1;
            小 = _ans23;
        };
        var _ans24 = 整 + 小;
        var _ans25 = _ans24 * 正負;
        return _ans25;
    };
    var 食列 = () => 0;
    食列 = function(渾沌碎) {
        var 渾沌列 = [];
        var 讀 = 1;
        while (true) {
            if (讀 > 渾沌碎.length) {
                break;
            };
            var _ans26 = 渾沌碎[讀 - 1];
            var 類 = _ans26;
            if (類 == "數") {
                var _ans27 = 讀 + 1;
                var _ans28 = 渾沌碎[_ans27 - 1];
                var _ans29 = 食數(_ans28);
                渾沌列.push(_ans29);
                var _ans30 = 讀 + 2;
                讀 = _ans30;
            } else if (類 == "言") {
                var _ans31 = 讀 + 1;
                var _ans32 = 渾沌碎[_ans31 - 1];
                渾沌列.push(_ans32);
                var _ans33 = 讀 + 2;
                讀 = _ans33;
            } else if (類 == "爻") {
                var _ans34 = 讀 + 1;
                var _ans35 = 渾沌碎[_ans34 - 1];
                if (_ans35 == "陰") {
                    渾沌列.push(false);
                } else {
                    渾沌列.push(true);
                };
                var _ans36 = 讀 + 2;
                讀 = _ans36;
            } else {
                var 層 = 0;
                var _ans37 = 讀 + 1;
                var 次讀 = _ans37;
                var 句 = [];
                while (true) {
                    if (次讀 > 渾沌碎.length) {
                        break;
                    };
                    if (渾沌碎[次讀 - 1] == "物") {
                        var _ans38 = 層 + 1;
                        層 = _ans38;
                    } else if (渾沌碎[次讀 - 1] == "列") {
                        var _ans39 = 層 + 1;
                        層 = _ans39;
                    } else if (渾沌碎[次讀 - 1] == "也") {
                        if (層 == 0) {
                            if (類 == "物") {
                                var _ans40 = 食物(句);
                                渾沌列.push(_ans40);
                            } else {
                                var _ans41 = 食列(句);
                                渾沌列.push(_ans41);
                            };
                            break;
                        };
                        var _ans42 = 層 - 1;
                        層 = _ans42;
                    };
                    var _ans43 = 渾沌碎[次讀 - 1];
                    句.push(_ans43);
                    var _ans44 = 次讀 + 1;
                    次讀 = _ans44;
                };
                var _ans45 = 次讀 + 1;
                讀 = _ans45;
            };
        };
        return 渾沌列;
    };
    var 食物 = () => 0;
    食物 = function(渾沌碎) {
        var 渾沌物 = {};
        var 讀 = 2;
        while (true) {
            if (讀 > 渾沌碎.length) {
                break;
            };
            var _ans46 = 渾沌碎[讀 - 1];
            var 端 = _ans46;
            var _ans47 = 讀 + 1;
            var _ans48 = 渾沌碎[_ans47 - 1];
            var 類 = _ans48;
            if (類 == "數") {
                var _ans49 = 讀 + 2;
                var _ans50 = 渾沌碎[_ans49 - 1];
                var _ans51 = 食數(_ans50);
                var _ans52 = 置物(渾沌物)(端)(_ans51);
                var _ans53 = 讀 + 4;
                讀 = _ans53;
            } else if (類 == "言") {
                var _ans54 = 讀 + 2;
                var _ans55 = 渾沌碎[_ans54 - 1];
                var _ans56 = 置物(渾沌物)(端)(_ans55);
                var _ans57 = 讀 + 4;
                讀 = _ans57;
            } else if (類 == "爻") {
                var _ans58 = 讀 + 2;
                var _ans59 = 渾沌碎[_ans58 - 1];
                if (_ans59 == "陰") {
                    var _ans60 = 置物(渾沌物)(端)(false);
                } else {
                    var _ans61 = 置物(渾沌物)(端)(true);
                };
                var _ans62 = 讀 + 4;
                讀 = _ans62;
            } else {
                var 層 = 0;
                var _ans63 = 讀 + 2;
                var 次讀 = _ans63;
                var 句 = [];
                while (true) {
                    if (次讀 > 渾沌碎.length) {
                        break;
                    };
                    if (渾沌碎[次讀 - 1] == "物") {
                        var _ans64 = 層 + 1;
                        層 = _ans64;
                    } else if (渾沌碎[次讀 - 1] == "列") {
                        var _ans65 = 層 + 1;
                        層 = _ans65;
                    } else if (渾沌碎[次讀 - 1] == "也") {
                        if (層 == 0) {
                            if (類 == "物") {
                                var _ans66 = 食物(句);
                                var _ans67 = 置物(渾沌物)(端)(_ans66);
                            } else {
                                var _ans68 = 食列(句);
                                var _ans69 = 置物(渾沌物)(端)(_ans68);
                            };
                            break;
                        };
                        var _ans70 = 層 - 1;
                        層 = _ans70;
                    };
                    var _ans71 = 渾沌碎[次讀 - 1];
                    句.push(_ans71);
                    var _ans72 = 次讀 + 1;
                    次讀 = _ans72;
                };
                var _ans73 = 次讀 + 2;
                讀 = _ans73;
            };
        };
        return 渾沌物;
    };
    var _ans74 = 渾沌碎.slice(1);
    var _ans75 = 食物(_ans74);
    return _ans75;
};
var 包渾沌 = this.包渾沌 = () => 0;
包渾沌 = this.包渾沌 = function(渾沌物) {
    var 挪符 = "　";
    var 抬符 = "\n";
    var 包數 = () => 0;
    var 暗包渾沌 = () => 0;
    var 包列 = () => 0;
    var 包物 = () => 0;
    var 包數 = () => 0;
    包數 = function(甲) {
        var 正負 = "";
        if (甲 < 0) {
            var _ans76 = 甲 * -1;
            甲 = _ans76;
            正負 = "負";
        };
        var _ans77 = 甲 % 1;
        var 小數 = _ans77;
        var _ans78 = 甲 - 小數;
        var 整數 = _ans78;
        var 小 = "";
        var 整 = "";
        while (true) {
            if (整數 <= 0) {
                break;
            };
            var _ans79 = 整數 % 10;
            var 位 = _ans79;
            var _ans80 = 位 + 1;
            var _ans81 = 位名[_ans80 - 1];
            var _ans82 = _ans81 + 整;
            整 = _ans82;
            var _ans83 = 整數 - 位;
            var _ans84 = _ans83 / 10;
            整數 = _ans84;
        };
        while (true) {
            if (小數 <= 0) {
                break;
            };
            var _ans85 = 小數 * 10;
            小數 = _ans85;
            var _ans86 = 小數 % 1;
            var 位 = _ans86;
            var _ans87 = 位 + 1;
            var _ans88 = 位名[_ans87 - 1];
            var _ans89 = 小 + _ans88;
            小 = _ans89;
            var _ans90 = 小數 - 位;
            小數 = _ans90;
        };
        var _ans91 = 正負 + 整;
        整 = _ans91;
        var _ans92 = 小.length;
        if (_ans92) {
            var _ans93 = 整 + "·";
            var _ans94 = _ans93 + 小;
            return _ans94;
        };
        return 整;
    };
    var 暗包渾沌 = () => 0;
    暗包渾沌 = function(類) {
        return function(實) {
            return function(挪抬) {
                var 渾沌語 = "";
                if (類 == "物") {
                    var _ans95 = 渾沌語 + 抬符;
                    渾沌語 = _ans95;
                    var _ans96 = 實;
                    var _ans97 = 挪抬 + 1;
                    var _ans98 = 包物(_ans96)(_ans97);
                    var _ans99 = 渾沌語 + _ans98;
                    渾沌語 = _ans99;
                    for (var _rand2 = 0; _rand2 < 挪抬; _rand2++) {
                        var _ans100 = 渾沌語 + 挪符;
                        渾沌語 = _ans100;
                    };
                    var _ans101 = 渾沌語 + "也";
                    var _ans102 = _ans101 + 抬符;
                    渾沌語 = _ans102;
                } else if (類 == "列") {
                    var _ans103 = 渾沌語 + 抬符;
                    渾沌語 = _ans103;
                    var _ans104 = 實;
                    var _ans105 = 挪抬 + 1;
                    var _ans106 = 包列(_ans104)(_ans105);
                    var _ans107 = 渾沌語 + _ans106;
                    渾沌語 = _ans107;
                    for (var _rand3 = 0; _rand3 < 挪抬; _rand3++) {
                        var _ans108 = 渾沌語 + 挪符;
                        渾沌語 = _ans108;
                    };
                    var _ans109 = 渾沌語 + "也";
                    var _ans110 = _ans109 + 抬符;
                    渾沌語 = _ans110;
                } else if (類 == "言") {
                    var _ans111 = 渾沌語 + 引起;
                    var _ans112 = _ans111 + 實;
                    var _ans113 = _ans112 + 引迄;
                    var _ans114 = _ans113 + 抬符;
                    渾沌語 = _ans114;
                } else if (類 == "爻") {
                    var 爻名 = "陽";
                    var _ans115 = 實;
                    if (!(_ans115)) {
                        爻名 = "陰";
                    };
                    var _ans116 = 渾沌語 + 引起;
                    var _ans117 = _ans116 + 爻名;
                    var _ans118 = _ans117 + 引迄;
                    var _ans119 = _ans118 + 抬符;
                    渾沌語 = _ans119;
                } else if (類 == "數") {
                    var _ans120 = 包數(實);
                    var 數名 = _ans120;
                    var _ans121 = 渾沌語 + 引起;
                    var _ans122 = _ans121 + 數名;
                    var _ans123 = _ans122 + 引迄;
                    var _ans124 = _ans123 + 抬符;
                    渾沌語 = _ans124;
                };
                return 渾沌語;
            };
        };
    };
    var 包列 = () => 0;
    包列 = function(渾沌列) {
        return function(挪抬) {
            var 渾沌語 = "";
            for (var 實 of 渾沌列) {
                var _ans125 = 識類(實);
                var 類 = _ans125;
                for (var _rand4 = 0; _rand4 < 挪抬; _rand4++) {
                    var _ans126 = 渾沌語 + 挪符;
                    渾沌語 = _ans126;
                };
                var _ans127 = 渾沌語 + 類;
                渾沌語 = _ans127;
                var _ans128 = 暗包渾沌(類)(實)(挪抬);
                var _ans129 = 渾沌語 + _ans128;
                渾沌語 = _ans129;
            };
            return 渾沌語;
        };
    };
    var 包物 = () => 0;
    包物 = function(渾沌物) {
        return function(挪抬) {
            var 渾沌語 = "";
            var _ans130 = 列物之端(渾沌物);
            var 諸端 = _ans130;
            for (var 端 of 諸端) {
                var _ans131 = 取物(渾沌物)(端);
                var 實 = _ans131;
                var _ans132 = 識類(實);
                var 類 = _ans132;
                for (var _rand5 = 0; _rand5 < 挪抬; _rand5++) {
                    var _ans133 = 渾沌語 + 挪符;
                    渾沌語 = _ans133;
                };
                var _ans134 = 渾沌語 + "之";
                var _ans135 = _ans134 + 引起;
                var _ans136 = _ans135 + 端;
                var _ans137 = _ans136 + 引迄;
                var _ans138 = _ans137 + 類;
                渾沌語 = _ans138;
                var _ans139 = 暗包渾沌(類)(實)(挪抬);
                var _ans140 = 渾沌語 + _ans139;
                渾沌語 = _ans140;
            };
            return 渾沌語;
        };
    };
    var _ans141 = 包物(渾沌物)(1);
    var 餛飩語 = _ans141;
    var _ans142 = 抬符 + 餛飩語;
    var _ans143 = "物" + _ans142;
    var _ans144 = _ans143 + "也";
    return _ans144;
};