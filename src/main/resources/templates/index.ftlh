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

<div class="centeredBox">
    <span class="myText usernameTitle">${userName!''}</span>
    <#if hasGame>
        <a href="/game" style="display: inline-block">
            <button class="ui button pink" style="width: 200px; margin-bottom: 7px">Resume Game</button> <!-- loads game page -->
        </a>
        <a href="/game/end-saved" style="display: inline-block">
            <button class="ui icon button"><i class="trash icon"></i></button>
        </a>
        <br/>
    </#if>
    <a href="/menu/create" style="display: inline-block">
        <button class="ui button <#if !hasGame>pink</#if>" style="width: 200px">New Game</button> <!-- loads player selector -->
    </a>
</div>

</body>
</html>