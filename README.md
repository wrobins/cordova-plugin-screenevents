# Cordova Screen Events Plugin
Trigger events based on your device's screen state!
## What is it?
This plugin lets you set up a simple event listener that lets you respond to your user turning their phone's screen on or off. This lets you differentiate between your app simple going back and forth between background and foreground (pause and resume). These events only fire if the actual screen is being turned on or off.
## How do I install it?
<pre>
cordova plugin add cordova-plugin-screenevents
</pre>
## How do I use it?
Cordova plugin calls expect a single callback event, so I suggest you set up a listener similar to the code below:
```js
function success(msg) {
    // Handle your success event here with the msg
    // Restart the listener
    window.cordova.plugins.ScreenEvents.listenerInit(function(msg) {
        success(msg);
    }, function(err) {
        // Handle fail event again here
    });
}
window.cordova.plugins.ScreenEvents.listenerInit(function(msg) {
    success(msg);
    }, function(err) {
    // Handle fail event here
});
```
You probably want "Success event" and "fail event" logic to be function calls to avoid duplicate code since you'll have to reference each twice.

The msg variable will be a string, and one of two things: in a success scenario, it will be either "SCREEN_TURNED_ON" or "SCREEN_TURNED_OFF" which should be self-explanatory. Otherwise, in a failure scenario the err will be a string of whatever exception message your device's native platform decides to conjure.
