<!DOCTYPE html>
<html>
<head>
    <title>Saud's TicTacToe</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="/semantic-ui/semantic.min.css"/>
    <link rel="stylesheet" href="/css/base.css"/>
    <link rel="stylesheet" href="/css/player-selector-base.css"/>
</head>
<body class="centerChildren">

<script src="/js/jquery-3.6.0.min.js"></script>
<script src="/semantic-ui/semantic.min.js"></script>
<script src="/js/user-search.js"></script>

<#macro userRow listing>
    <#if listing.available>
        <a href="/game/create/${listing.id?c}" class="inlineBlk">
            <button class="ui username button">${listing.name}</button>
        </a>
    <#else>
        <button class="ui username button disabled">${listing.name}</button>
    </#if>

    <#if listing.friend>
        <a href="/user/remove-friend/${listing.id?c}" class="inlineBlk">
            <button class="ui icon button"><i class="user times icon"></i></button>
        </a>
    <#else>
        <a href="/user/add-friend/${listing.id?c}" class="inlineBlk">
            <button class="ui icon button"><i class="user plus icon"></i></button>
        </a>
    </#if>
    <br/>
</#macro>

<div class="centeredBox" style="min-height: 100%">
    <span class="myText usernameTitle">${userName!''}</span>

    <br/>

    <span class="myText">Friends</span>
    <#list friends as listing>
        <@userRow listing=listing/>
    <#else>
        <span class="myText" style="font-size:15px">Empty</span>
    </#list>

    <br/>

    <span class="myText">Nearby</span>
    <#list nearby as listing>
        <@userRow listing=listing/>
    <#else>
        <span class="myText" style="font-size:15px">Empty</span>
    </#list>

    <br/>

    <span class="myText">Search</span>
    <div class="ui left icon input" style="margin-bottom:7px; width:245px;">
        <input id="userSearchBox" onchange="updateUserSearchResults()" type="text" placeholder="Search...">
        <i class="search icon"></i>
    </div>
    <div id="userSearchDiv">

    </div>

</div>

</body>
</html>