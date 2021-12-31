<!DOCTYPE html>
<html>
<head>
    <title>Saud's TicTacToe</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="/css/base.css"/>
    <link rel="stylesheet" href="/css/game-style-base.css"/>
</head>
<body>

<div style="text-align:center; font-size:20px; font-family:'Courier New'; color:white">
    <span id="player1Name" style="color:#DD1111">${game.player1Name!''}</span> vs <span id="player2Name" style="color:#00BBFF">${game.player2Name!''}</span>
</div>
<br/>

<table>
    <tr>
        <td id="cell0"class="clickable-area"><div class="my-cell"></div></td>
        <td id="cell1"class="clickable-area"><div class="my-cell"></div></td>
        <td id="cell2"class="clickable-area"><div class="my-cell"></div></td>
    </tr>
    <tr>
        <td id="cell3"class="clickable-area"><div class="my-cell"></div></td>
        <td id="cell4"class="clickable-area"><div class="my-cell"></div></td>
        <td id="cell5"class="clickable-area"><div class="my-cell"></div></td>
    </tr>
    <tr>
        <td id="cell6"class="clickable-area"><div class="my-cell"></div></td>
        <td id="cell7"class="clickable-area"><div class="my-cell"></div></td>
        <td id="cell8"class="clickable-area"><div class="my-cell"></div></td>
    </tr>
</table>

<script src="/js/game-logic.js"></script>

</body>
</html>