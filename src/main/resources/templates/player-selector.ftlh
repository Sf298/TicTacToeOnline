<!DOCTYPE html>
<html>
<head>
    <title>Saud's TicTacToe</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="/semantic-ui/semantic.min.css"/>
    <link rel="stylesheet" href="/css/base.css"/>
</head>
<body class="centerChildren">

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="/semantic-ui/semantic.min.js"></script>
<script src="/js/Cookies.js"></script>
<#-- <script src="/js/login.js"></script> -->

<div class="centeredBox" style="min-height: 100%">
    <span class="myText usernameTitle">${userName!''}</span>

    <br/>

    <#if friends??>
        <span class="myText">Friends</span>
        <#list friends?keys as friendKey>
            <a href="/game/create/${friendKey}" style="display: inline-block">
                <button class="ui button" style="width: 200px; margin-bottom: 7px">${friends[friendKey]}</button>
            </a>
            <a href="/user/remove-friend/${friendKey}" style="display: inline-block">
                <button class="ui icon button"><i class="user times icon"></i></button>
            </a>
            <br/>
        </#list>
    </#if>

    <br/>

    <span class="myText">Nearby</span>
    <#list nearby?keys as nearbyKey>
        <a href="/game/create/${nearbyKey}" style="display: inline-block">
            <button class="ui button" style="width: 200px; margin-bottom: 7px">${nearby[nearbyKey]}</button>
        </a>
        <a href="/user/add-friend/${nearbyKey}" style="display: inline-block">
            <button class="ui icon button"><i class="user plus icon"></i></button>
        </a>
        <br/>
    </#list>

</div>

</body>
</html>