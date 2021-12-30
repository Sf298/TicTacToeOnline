
class Cookies {

    static put(key, value) {
        document.cookie = key+"="+value;
    }

    static get(key) {
        var cookiePair = document.cookie.split('; ').find(row => row.startsWith(key+'='));

        if (cookiePair == null)
            return null;
        return cookiePair.split('=')[1];
    }

}
