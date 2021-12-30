<!DOCTYPE html>
<html>
<head>
    <title>Saud's TicTacToe</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body>

<script src="/js/Cookies.js"></script>
<script src="/js/login.js"></script>

<div>
    <#if hasGame><a href="/game">Resume</a><br/></#if> <!-- loads game page -->
    <a href="/game/create">New Game</a> <!-- loads player selector -->
</div>

</body>
</html>