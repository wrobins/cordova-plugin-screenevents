#import "ScreenEvents.h"
#import <Cordova/CDVPlugin.h>
#import <notify.h>

@implementation ScreenEvents

-(void)pluginInitialize
{
    _notifyToken = 0;
}

-(void)listenerInit:(CDVInvokedUrlCommand *)command
{
    NSString *isLocked = @"Y29tLmFwcGxlLnNwcmluZ2JvYXJkLmhhc0JsYW5rZWRTY3JlZW4=";
    NSData *isLockedDecoded = [[NSData alloc] initWithBase64EncodedString:isLocked options:0];
    NSString *decodedString =[[NSString alloc] initWithData:isLockedDecoded encoding:NSUTF8StringEncoding];
    int status = notify_register_dispatch(
                                          (char*)[decodedString UTF8String],
                                          &(self->_notifyToken),
                                          dispatch_get_main_queue(),
                                          ^(int t) {
        uint64_t state;
        CDVPluginResult *pluginResult = nil;
        int result = notify_get_state(self->_notifyToken, &state);
        if (result == NOTIFY_STATUS_OK) {
            NSString *screenStatus = nil;
            if (state == 0) {
                screenStatus = @"SCREEN_TURNED_ON";
            } else {
                screenStatus = @"SCREEN_TURNED_OFF";
            }
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:screenStatus];
        } else {
            pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[NSString stringWithFormat:@"Result returned result: %d", result]];
        }
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    });
    if (status != NOTIFY_STATUS_OK) {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[NSString stringWithFormat:@"Result returned status: %d", status]];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

@end
