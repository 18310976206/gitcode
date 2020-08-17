layui.config({
    base : "js/modules/"
}).extend({
    "common" : "common"
})
layui.use(['form','layer','jquery','common'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        common = layui.common;

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    });

    $("#imgCode img").click(function() {
        this.src = "/we/login/getVcode?rnd="+Math.random();
    });

    //登录按钮
    form.on("submit(login)",function(data){
        $("#login").text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        $.ajax({
            url: "/we/login/personalLogin",
            type: "post",
            async: true,//异步操作
            data: data.field,
            contentType:"application/x-www-form-urlencoded; charset=utf-8",
            success: function(response){
                var resp = eval('(' + response + ')');
                 // if(resp.flag==1){
                 //     $("#errorDiv").text(resp.msg).show();
                 // }else 
                 if(resp.flag== 1){
                     $("#errorDiv").text("登陆时发生错误，原因：【"+resp.msg+"】").show();
                 }else{
                     var sendToUrl = resp.url;
                     setCookie('us_na',data.field.username,720,"/");//保存用户名
                     document.location=sendToUrl;
                 }
                  $("#login").text("登录").removeAttr("disabled").removeClass("layui-disabled");
                //location.href = "/index.html";
            },
            error: function (xmlHttpRequest) {
                $("#login").text("登录").removeAttr("disabled").removeClass("layui-disabled");
                common.outErrorMsg(xmlHttpRequest);
            }
        });
        return false;
    });

})

function setCookie(name,value,hours,path){
        var name = escape(name);
        var value = escape(value);
        var expires = new Date();
        expires.setTime(expires.getTime() + hours*3600000);
        path = path == "" ? "" : ";path=" + path;
        _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
        document.cookie = name + "=" + value + _expires + path;
   }

   function getCookie(name){
        var name = escape(name);
        var allcookies = document.cookie;
        name +="=";
        var pos = allcookies.indexOf(name);
        if(pos!=-1){
             var start = pos+name.length;
             var end = allcookies.indexOf(";",start);
             if(end ==-1) end = allcookies.length;
             var value = allcookies.substring(start,end); //提取cookie的值
             return unescape(value); //对它解码
             }
             else return ""; //搜索失败，返回空字符串
   }    