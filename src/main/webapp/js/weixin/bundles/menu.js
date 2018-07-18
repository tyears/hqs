function newArray(n) {
    for (var i = [], t = 0; t < n; t++)i[t] = {value: t};
    return i
}
function getbyteLenth(n) {
    for (var i = 0, r, t = 0; t < n.length; t++)r = n.charAt(t), i += /^[\u0000-\u00ff]$/.test(r) ? 1 : 2;
    return i
}
function MenuFormValidate() {
    $("#MenuForm").validate({
        rules: {name: {required: !0}, value: {required: !0}, appid: {required: !0}, pagepath: {required: !0}},
        messages: {name: {required: "请输入名称"}, value: {required: $("#txtMenuButtonValue").attr("placeholder")}
                    ,appid: {required: "请输入小程序的appid"}, pagepath: {required: "请输入小程序的页面路径"}}
    })
}
$(function () {
    var n = {
        Type: ko.observable(1),
        TokenType: ko.observable(1),
        AccessToken: ko.observable(window.token),
        AppId: ko.observable(),
        AppSecret: ko.observable(),
        AgentId: ko.observable(),
        CorpId: ko.observable(),
        PermanentCode: ko.observable(),
        SuiteId: ko.observable(),
        SuiteSecret: ko.observable(),
        SuiteTicket: ko.observable(),
        Menus: ko.observable(),
        MenusReset: function () {
            var t = JSON.stringify(n.Menus());
            n.Menus(undefined);
            n.Menus(JSON.parse(t));
            MenuFormValidate()
        },
        MenuIndex: ko.observable(),
        isEditMenu: ko.observable(!1),
        isSaveMenu: ko.observable(!0),
        BottonIndex: ko.observable(-1),
        SubBottonIndex: ko.observable(-1),
        Menu: ko.observable(),
        CopyMenu: ko.observable(),
        Copy: function () {
            if (n.Menu() != undefined) {
                var t = JSON.stringify(n.Menu());
                n.CopyMenu(JSON.parse(t));
                n.Menu(undefined)
            }
        },
        Paste: function () {
            if (n.CopyMenu() != undefined) {
                var t = JSON.parse(JSON.stringify(n.CopyMenu()));
                (n.SubBottonIndex() === -1 || t.sub_button == undefined) && (n.isEditMenu() || n.MenuIndex() == undefined) || delete t.sub_button;
                n.Menu(t);
                MenuFormValidate()
            }
        },
        Up: function () {
            var t = n.BottonIndex(), r = n.SubBottonIndex(), i = r - 1;
            n.Menus().button[t].sub_button[r] = n.Menus().button[t].sub_button[i];
            n.Menus().button[t].sub_button[i] = n.Menu();
            n.MenusReset();
            n.SubBottonIndex(i)
        },
        Down: function () {
            var t = n.BottonIndex(), r = n.SubBottonIndex(), i = r + 1;
            n.Menus().button[t].sub_button[r] = n.Menus().button[t].sub_button[i];
            n.Menus().button[t].sub_button[i] = n.Menu();
            n.MenusReset();
            n.SubBottonIndex(i)
        },
        Left: function () {
            var i = n.BottonIndex(), r = n.SubBottonIndex(), t;
            r === -1 && (t = i - 1, n.Menus().button[i] = n.Menus().button[t], n.Menus().button[t] = n.Menu(), n.MenusReset(), n.BottonIndex(t))
        },
        Right: function () {
            var i = n.BottonIndex(), r = n.SubBottonIndex(), t;
            r === -1 && (t = i + 1, n.Menus().button[i] = n.Menus().button[t], n.Menus().button[t] = n.Menu(), n.MenusReset(), n.BottonIndex(t))
        },
        EditMenu: function (t, i, r) {
            n.BottonIndex(i);
            n.SubBottonIndex(r);
            n.isEditMenu(!0);
            var u = JSON.stringify(t);
            n.Menu(JSON.parse(u));
            MenuFormValidate()
        },
        AddMenu: function (t) {
            n.BottonIndex(-1);
            n.SubBottonIndex(-1);
            n.isEditMenu(!1);
            n.MenuIndex(t);
            n.Menu({type: "view", name: "", value: "",appid: "",pagepath: ""});
            MenuFormValidate()
        },
        DeleteMenu: function () {
            $(n.Menus().button).each(function (t, i) {
                t === n.BottonIndex() && n.SubBottonIndex() === -1 && n.Menus().button.splice(t, 1);
                i.sub_button instanceof Array && $(i.sub_button).each(function (r) {
                    t === n.BottonIndex() && r === n.SubBottonIndex() && i.sub_button.splice(r, 1)
                })
            });
            n.Menu(undefined);
            n.MenuIndex(undefined);
            n.BottonIndex(-1);
            n.SubBottonIndex(-1);
            n.MenusReset()
        },
        CancelMenuSave: function () {
            n.Menu(undefined);
            n.MenuIndex(undefined);
            n.BottonIndex(-1);
            n.SubBottonIndex(-1)
        },
        MenuSave: function () {
            if ($("#MenuForm").data("validator").form() && n.NameErrorMessage() == undefined && n.ValueErrorMessage() == undefined) {
                if (n.isEditMenu()) {
                    var t = n.BottonIndex(), i = n.SubBottonIndex();
                    i === -1 ? n.Menus().button[t] = n.Menu() : n.Menus().button[t].sub_button[i] = n.Menu()
                } else n.MenuIndex() != undefined ? (n.Menus().button[n.MenuIndex()].sub_button == undefined && (n.Menus().button[n.MenuIndex()].sub_button = []), n.Menus().button[n.MenuIndex()].sub_button.unshift(n.Menu())) : n.Menus().button.push(n.Menu());
                n.Menu(undefined);
                n.MenuIndex(undefined);
                n.BottonIndex(-1);
                n.SubBottonIndex(-1);
                n.MenusReset()
            }
        },
        EditMenus: function (t) {
            var r;
            if (t == undefined) r = {}, r.button = [], n.Menus(r); else {
                var u = n.AppId(), f = n.AppSecret(), e = n.AccessToken(), o = n.Type(), i = n.TokenType(), s = n.CorpId(), h = n.PermanentCode(), c = n.AgentId(), l = n.SuiteId(), a = n.SuiteSecret(), v = n.SuiteTicket();
                if (o === "1" && i === "2") {
                    if (u == undefined || $.trim(u).length === 0) {
                        alert("请输入AppId");
                        return
                    }
                    if (f == undefined || $.trim(f).length === 0) {
                        alert("请输入AppSecret");
                        return
                    }
                } else if (o === "2" && i === "2") {
                    if (s == undefined || $.trim(s).length === 0) {
                        alert("请输入CorpId");
                        return
                    }
                    if (h == undefined || $.trim(h).length === 0) {
                        alert("请输入永久授权码");
                        return
                    }
                    if (c == undefined || $.trim(c).length === 0) {
                        alert("请输入AgentId");
                        return
                    }
                    if (l == undefined || $.trim(l).length === 0) {
                        alert("请输入SuiteId");
                        return
                    }
                    if (a == undefined || $.trim(a).length === 0) {
                        alert("请输入SuiteSecret");
                        return
                    }
                    if (v == undefined || $.trim(v).length === 0) {
                        alert("请输入SuiteTicket");
                        return
                    }
                } else if (i === "1" && (e == undefined || $.trim(e).length === 0)) {
                    alert("请输入AccessToken");
                    return
                }
                $("#btnQueryMenu").button("查询中...");
                $.ajax({
                    url: "",
                    datatype: "JSON",
                    type: "POST",
                    async: !0,
                    data: JSON.stringify({
                        appId: u,
                        appSecret: f,
                        accessToken: e,
                        type: o,
                        tokenType: i,
                        corpId: s,
                        permanentCode: h,
                        agentId: c,
                        suiteId: l,
                        suiteSecret: a,
                        suiteTicket: v
                    }),
                    contentType: "application/json; charset=UTF-8",
                    success: function (t) {
                        if ($("#btnQueryMenu").button("reset"), t.Success) {
                            var r = t.Data, i = JSON.parse(r).menu;
                            $(i.button).each(function (n, t) {
                                t.type === "view" ? (t.value = t.url, delete t.url) : (t.value = t.key, delete t.key);
                                t.type == undefined && (t.type = "view", t.value = "");
                                t.sub_button instanceof Array && $(t.sub_button).each(function (n, t) {
                                    t.type === "view" ? (t.value = t.url, delete t.url) : (t.value = t.key, delete t.key)
                                })
                            });
                            n.Menu(undefined);
                            n.MenuIndex(undefined);
                            n.BottonIndex(-1);
                            n.SubBottonIndex(-1);
                            n.Menus(undefined);
                            n.Menus(i)
                        } else alert(t.Messages)
                    },
                    error: function (n, t, i) {
                        $("#btnQueryMenu").button("reset");
                        console.error(i)
                    }
                })
            }
        },
        SaveMenus: function () {
            var c = n.NewMenu(), i = n.AppId(), r = n.AppSecret(), u = n.AccessToken(), f = n.Type(), t = n.TokenType(), e = n.AgentId(), o = n.SuiteId(), s = n.SuiteSecret(), h = n.SuiteTicket();
            if (f === "1" && t === "2") {
                if (i == undefined || $.trim(i).length === 0) {
                    alert("请输入AppId");
                    return
                }
                if (r == undefined || $.trim(r).length === 0) {
                    alert("请输入AppSecret");
                    return
                }
            } else if (f === "2" && t === "2") {
                if (e == undefined || $.trim(e).length === 0) {
                    alert("请输入AgentId");
                    return
                }
                if (o == undefined || $.trim(o).length === 0) {
                    alert("请输入SuiteId");
                    return
                }
                if (s == undefined || $.trim(s).length === 0) {
                    alert("请输入SuiteSecret");
                    return
                }
                if (h == undefined || $.trim(h).length === 0) {
                    alert("请输入SuiteTicket");
                    return
                }
            } else if (t === "1" && (u == undefined || $.trim(u).length === 0)) {
                alert("请输入AccessToken");
                return
            }
            $("#btnSubmitMenu").button("发布中...");
            $.ajax({
                url: "weiXin/saveMenu.htm",
                dataType: "JSON",
                type: "POST",
                async: !0,
                data:{
                    "appId": i,
                    "appSecret": r,
                    "accessToken": u,
                    "tokenType": t,
                    "menu": JSON.stringify(c)
                },
                success: function (n) {
                    $("#btnSubmitMenu").button("reset");
                    alert(n.msg)
                },
                error: function (n) {
                    $("#btnSubmitMenu").button("reset");
                    alert(n.msg)
                }
            })
        }
    };
    n.IsUp = ko.dependentObservable(function () {
        return this.SubBottonIndex() > 0
    }, n);
    n.IsDown = ko.dependentObservable(function () {
        return this.Menus() == undefined ? !1 : this.BottonIndex() >= 0 && this.SubBottonIndex() >= 0 && this.SubBottonIndex() < this.Menus().button[this.BottonIndex()].sub_button.length - 1
    }, n);
    n.IsLeft = ko.dependentObservable(function () {
        return this.BottonIndex() > 0 && this.SubBottonIndex() === -1
    }, n);
    n.IsRight = ko.dependentObservable(function () {
        return this.Menus() == undefined ? !1 : this.BottonIndex() >= 0 && this.BottonIndex() < this.Menus().button.length - 1 && this.SubBottonIndex() === -1
    }, n);
    n.SelectedCss = ko.dependentObservable(function () {
        var n = this.BottonIndex(), t = this.SubBottonIndex();
        $("div.list-group-item").removeClass("list-group-item-selected");
        n >= 0 && (t >= 0 ? $("div.list-group-item[bottonindex=" + n + "][subbottonindex=" + t + "]").addClass("list-group-item-selected") : $("div.list-group-item[bottonindex=" + n + "]").addClass("list-group-item-selected"))
    }, n);
    n.NameErrorMessage = ko.observable();
    n.EventNameErrorMessage = function (t, i) {
        var r, f, u, e, o;
        $(i.currentTarget).trigger("change");
        n.Menu() != undefined ? (r = !1, n.isEditMenu() ? (f = n.SubBottonIndex(), r = f === -1 ? !0 : !1) : r = n.MenuIndex() != undefined ? !1 : !0, u = 0, u = r ? 16 : 60, e = n.Menu().name, o = getbyteLenth(e), o > u ? (n.NameErrorMessage("已超出最大长度"), $(i.currentTarget).popover({
            trigger: "manual",
            animation: !1
        }), $(i.currentTarget).popover("show")) : ($(i.currentTarget).popover("destroy"), n.NameErrorMessage(undefined))) : ($(i.currentTarget).popover("destroy"), n.NameErrorMessage(undefined))
    };
    n.ValueErrorMessage = ko.observable();
    n.EventValueErrorMessage = function (t, i) {
        var r, f, u, e, o;
        $(i.currentTarget).trigger("change");
        n.Menu() != undefined ? (r = 0, f = n.Menu().type, f === "view" ? r = 1024 : (u = parseInt(n.Type()), u === 1 ? r = 128 : u === 2 && (r = 256)), e = n.Menu().value, o = getbyteLenth(e), o > r ? (n.ValueErrorMessage("已超出最大长度"), $(i.currentTarget).popover({
            trigger: "manual",
            animation: !1
        }), $(i.currentTarget).popover("show")) : ($(i.currentTarget).popover("destroy"), n.ValueErrorMessage(undefined))) : ($(i.currentTarget).popover("destroy"), n.ValueErrorMessage(undefined))
    };
    n.NewMenu = ko.dependentObservable(function () {
        if (this.Menus() == undefined)return undefined;
        var n = JSON.parse(JSON.stringify(this.Menus()));
        return $(n.button).each(function (n, t) {
            t.type === "view" ? (t.url = t.value, delete t.value, delete t.appid, delete t.pagepath) : (t.url = t.value, delete t.value);
            t.sub_button instanceof Array && ($(t.sub_button).each(function (n, t) {
                t.type === "view" ? (t.url = t.value, delete t.value, delete t.appid, delete t.pagepath) : (t.url = t.value, delete t.value)
            }), t.sub_button.length > 0 ? (delete t.key, delete t.url, delete t.type, delete t.appid, delete t.pagepath) : delete t.sub_button)
        }), n
    }, n);
    ko.applyBindings(n, document.getElementById("divMain"));
    // ko.applyBindings(n, document.getElementById("jsonShow"));
    MenuFormValidate();
    n.EditMenus();
    $("#divMenu").show();
    // $.LoadingHide();
    $("#selType").change(function () {
        $(this).val() === "2" ? $("#selTokenType").val("1").trigger("change") : $("#selTokenType").removeAttr("disabled")
    });
    $('[data-toggle="tooltip"]').tooltip()
})