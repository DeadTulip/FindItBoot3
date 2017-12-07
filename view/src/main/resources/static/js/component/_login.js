(function (exports) {
    'use strict';

    exports.app = new Vue({
        el: '#loginForm',
        data: {
            username: '',
            password: ''
        },
        computed: {
            hasInput: function () {
                return this.username.length > 0 && this.password.length > 0
            }
        }
    });
})(window);
