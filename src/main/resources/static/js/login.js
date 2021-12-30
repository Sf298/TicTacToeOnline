
function renewUserId() {
    console.log("renewing user id");
    const action = async () => {
        const response = await fetch('/user/create', {method:'POST'});
        const userId = await response.json();
        Cookies.put("userId", userId);
    }
    action.apply();
}

const userId = Cookies.get("userId");
if (userId == null) {
    renewUserId();
} else {
    const action = async () => {
        const response = await fetch('/user/get-name/'+userId);
        if (response.status == 204) {
            renewUserId();
        } else {
            const userName = await response.text();
        }
    }
    action.apply();
}