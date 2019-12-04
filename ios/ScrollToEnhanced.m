#import <React/RCTUIManager.h>
#import <React/RCTScrollView.h>
#import "ScrollToEnhanced.h"


@implementation ScrollToEnhanced

RCT_EXPORT_MODULE()

@synthesize bridge = _bridge;

- (dispatch_queue_t)methodQueue {
    return _bridge.uiManager.methodQueue;
}

RCT_EXPORT_METHOD(scrollToRect:(nonnull NSNumber *)reactTag rect:(CGRect)rect animated:(BOOL)animated)
{
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        RCTScrollView *scrollview = (RCTScrollView *)viewRegistry[reactTag];

        if (![scrollview isKindOfClass:[RCTScrollView class]]) {
            RCTLogError(@"Invalid view returned from registry, expecting ScrollView, got: %@", scrollview);
        } else {
            [scrollview.scrollView scrollRectToVisible:rect animated:animated];
        }
    }];
}

RCT_EXPORT_METHOD(scrollToView:(nonnull NSNumber *)reactTag viewTag:(nonnull NSNumber *)viewTag animated:(BOOL)animated)
{
    [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, UIView *> *viewRegistry) {
        RCTScrollView *scrollview = (RCTScrollView *)viewRegistry[reactTag];
        UIView *view = viewRegistry[viewTag];
        
        if (![scrollview isKindOfClass:[RCTScrollView class]]) {
            RCTLogError(@"Invalid view returned from registry, expecting ScrollView, got: %@", scrollview);
        } else {
            CGRect rect = [view convertRect:view.bounds toView:scrollview.scrollView];
            [scrollview.scrollView scrollRectToVisible:rect animated:animated];
        }
    }];
}

@end
