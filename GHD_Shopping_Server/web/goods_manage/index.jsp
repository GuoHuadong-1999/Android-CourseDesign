<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GHD_Shopping_商品发布</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="css/xcConfirm.css"/>
    <script src="js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/xcConfirm.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8">
        function check() {
            // var data = "恭喜您，商品发布成功！";
            // window.wxc.xcConfirm(data, window.wxc.xcConfirm.typeEnum.success);
            return true;
        }
    </script>
    <style type="text/css">
        .center-horizontal {
            margin: 0 auto;
        }

        .center-in-center {
            position: absolute;
            top: 50%;
            left: 50%;
            -webkit-transform: translate(-50%, -50%);
            -moz-transform: translate(-50%, -50%);
            -ms-transform: translate(-50%, -50%);
            -o-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
        }
    </style>
</head>
<body>
<div style="width: 80%" class="center-in-center center-horizontal">
    <form action="../PublishGoods" method="post" class="form-horizontal" onsubmit="return check()">
        <div class="form-group">
            <label for="goods_name" class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-10">
                <input name="goods_name" type="text" class="form-control" id="goods_name" placeholder="请输入商品名称">
            </div>
        </div>
        <div class="form-group">
            <label for="goods_price" class="col-sm-2 control-label">商品价格</label>
            <div class="col-sm-10">
                <input name="goods_price" type="text" class="form-control" id="goods_price" placeholder="请输入商品价格">
            </div>
        </div>
        <div class="form-group">
            <label for="goods_image" class="col-sm-2 control-label">商品图片链接</label>
            <div class="col-sm-10">
                <input name="goods_image" type="text" class="form-control" id="goods_image" placeholder="请输入商品图片链接">
            </div>
        </div>
        <div class="form-group">
            <label for="goods_description" class="col-sm-2 control-label">商品详细信息</label>
            <div class="col-sm-10">
                <textarea name="goods_description" class="form-control" id="goods_description" rows="3"
                          placeholder="请输入商品详细信息"></textarea>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-12">
                <div style="text-align: center">
                    <button type="submit" class="btn btn-primary" id="goods_publish">发布商品</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>

