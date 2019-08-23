<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录页面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" charset="UTF-8">
<link rel="stylesheet" href="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.css">
<script src="https://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="https://apps.bdimg.com/libs/jquerymobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
    <form action="login.do" method="post" data-ajax="false">
    
        <label for="userid">用户编号:</label>
        <input type="text" id="userid" name="userid" value="admin">
        <label for="userid">用户密码:</label>
        <input type="text" id="password" name="password" value="admin">
        <button class="ui-btn ui-corner-all ui-shadow ui-icon-check ui-btn-icon-top">登录</button>
        <label style="color:red">${error!''}</label>
    </form>
</body>
</html>