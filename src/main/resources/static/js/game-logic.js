
function updateCell(element, value) {
    if (value == "PLAYER_1") {
        element.childNodes[0].classList.remove("circle");
        element.childNodes[0].classList.add("cross");
        element.childNodes[0].innerHTML = "⨯";
    } else if (value == "PLAYER_2") {
        element.childNodes[0].classList.remove("cross");
        element.childNodes[0].classList.add("circle");
        element.childNodes[0].innerHTML = "⭘";
    }
}

var makeMoveListener = function(event) {
    var position = event.target.id.substring(4);
    const action = async () => {
        const response = await fetch('/game/move/'+position, {method:"POST"});
        if (response.status == 200) {
            refreshState();
        }
    };
    action.apply();
};
function refreshState() {
    console.log("refreshing");
    const action = async () => {
        const response = await fetch('/game/state');
        const game = await response.json();

        if (game.turn == "PLAYER_1") {
            document.getElementById("player1Name").classList.add("underline");
            document.getElementById("player2Name").classList.remove("underline");
        } else if (game.turn == "PLAYER_2") {
            document.getElementById("player2Name").classList.add("underline");
            document.getElementById("player1Name").classList.remove("underline");
        }

        for (var i=0; i<game.board.length; i++) {
            var element = document.getElementById("cell"+i);
            updateCell(element, game.board[i]);

            if (game.isComplete) {
                window.location.replace("/game/end");
            }

            if (game.turn == game.requesterPlayer) {
                element.addEventListener("click", makeMoveListener);
            } else {
                element.removeEventListener("click", makeMoveListener);
            }
        }

    };
    action.apply();
    setTimeout(refreshState, 1000);
}

refreshState();