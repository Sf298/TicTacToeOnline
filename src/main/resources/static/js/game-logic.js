let positions = document.querySelectorAll('.clickable-area');

for (var i=0; i < positions.length; i++) {
    positions[i].addEventListener("click", function(){
        if (this.childNodes[0].classList.contains("cross")) {
            this.childNodes[0].classList.remove("cross");
            this.childNodes[0].classList.add("circle");
            this.childNodes[0].innerHTML = "⭘";
        } else {
            this.childNodes[0].classList.remove("circle");
            this.childNodes[0].classList.add("cross");
            this.childNodes[0].innerHTML = "⨯";
        }
    });
}