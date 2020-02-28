#import <Cordova/CDVPlugin.h>

@interface ScreenEvents : CDVPlugin

@property int notifyToken;

-(void)pluginInitialize;

-(void)listenerInit:(CDVInvokedUrlCommand*)command;

@end
