
function updateUserSearchResults() {
    var userId = null;
    var cookiePair = document.cookie.split('; ').find(row => row.startsWith('userId='));
    if (cookiePair != null)
        userId = cookiePair.split('=')[1];

    const userSearchBox = document.getElementById("userSearchBox");
    const userSearchDiv = document.getElementById("userSearchDiv");

    const searchString = userSearchBox.value;

    userSearchBox.classList.add("loading");

    const action = async () => {
        // fetch the list of users
        const response = await fetch(`/user/search/${userId}?searchString=${searchString}`);
        const listings = await response.json();

        // clear any previous results
        userSearchDiv.innerHTML = "";

        // build the results html
        var htmlArr = [];
        for (var i = 0; i < listings.length; i++) {
            const listing = listings[i];
            if (listing.available) {
                htmlArr.push(`<a href="/game/create/${listing.id}" class="inlineBlk">`);
                htmlArr.push(`    <button class="ui username button">${listing.name}</button>`);
                htmlArr.push(`</a>`);
            } else {
                htmlArr.push(`<button class="ui username button disabled">${listing.name}</button>`);
            }

            if (listing.friend) {
                htmlArr.push(`<a href="/user/remove-friend/${listing.id}" class="inlineBlk">`);
                htmlArr.push(`    <button class="ui icon button"><i class="user times icon"></i></button>`);
            } else {
                htmlArr.push(`<a href="/user/add-friend/${listing.id}" class="inlineBlk">`);
                htmlArr.push(`    <button class="ui icon button"><i class="user plus icon"></i></button>`);
            }
            htmlArr.push(`</a>`);
            htmlArr.push(`<br/>`);
            /*htmlArr.push(`<a href="/game/create/${listing.id}" class="inlineBlk">
                           <button class="ui button username-button">${listing.name}</button>
                       </a>
                       <a href="/user/add-friend/${listing.id}" class="inlineBlk">
                           <button class="ui icon button"><i class="user plus icon"></i></button>
                       </a>
                       <br/>
            `);*/
        }
        const combinedHtml = htmlArr.join("");

        // set the generated results
        userSearchDiv.innerHTML = combinedHtml;

        // end the loading spinner
        userSearchBox.classList.remove("loading");
    };
    action.apply();
}