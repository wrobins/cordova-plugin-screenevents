module.exports = {
    listenerInit: function(successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, 'ScreenEvents', 'listenerInit', []);
    }
};