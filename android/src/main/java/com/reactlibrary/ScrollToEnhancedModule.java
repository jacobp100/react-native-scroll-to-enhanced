package com.reactlibrary;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.graphics.Rect;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.bridge.ReadableMap;

public class ScrollToEnhancedModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public ScrollToEnhancedModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ScrollToEnhanced";
    }

    private void scrollVertical(ScrollView scrollview, Rect rect, boolean animated) {
        int scrollTop = scrollview.getScrollY();
        int scrollHeight =  scrollview.getHeight();
        int scrollY;
        if (rect.top < scrollTop) {
            scrollY = rect.top;
        } else if (rect.bottom > scrollTop + scrollHeight) {
            scrollY = rect.bottom - scrollHeight;
        } else {
            return;
        }

        if (animated) {
            scrollview.smoothScrollTo(0, scrollY);
        } else {
            scrollview.scrollTo(0, scrollY);
        }
    }

    private void scrollHorizontal(HorizontalScrollView scrollview, Rect rect, boolean animated) {
        int scrollLeft = scrollview.getScrollX();
        int scrollWidth = scrollview.getWidth();
        int scrollX;
        if (rect.left < scrollLeft) {
            scrollX = rect.left;
        } else if (rect.right > scrollLeft + scrollWidth) {
            scrollX = rect.right - scrollWidth;
        } else {
            return;
        }

        if (animated) {
            scrollview.smoothScrollTo(scrollX, 0);
        } else {
            scrollview.scrollTo(scrollX, 0);
        }
    }

    @ReactMethod
    public void scrollToView(final int reactTag, final int childTag, final boolean animated) {
        UIManagerModule uiManager = reactContext.getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(
                new UIBlock() {
                    @Override
                    public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                        View view = nativeViewHierarchyManager.resolveView(reactTag);
                        View child = nativeViewHierarchyManager.resolveView(childTag);

                        Rect rect = new Rect();
                        child.getDrawingRect(rect);

                        if (view instanceof ScrollView) {
                            ScrollView scrollview = (ScrollView) view;
                            /* Offset from child's local coordinates to ScrollView coordinates */
                            scrollview.offsetDescendantRectToMyCoords(child, rect);
                            scrollVertical(scrollview, rect, animated);
                        } else if (view instanceof HorizontalScrollView) {
                            HorizontalScrollView scrollview = (HorizontalScrollView) view;
                            scrollview.offsetDescendantRectToMyCoords(child, rect);
                            scrollHorizontal(scrollview, rect, animated);
                        }
                    }
                }
        );
    }

    @ReactMethod
    public void scrollToRect(final int reactTag, final ReadableMap rectJson, final boolean animated) {
        UIManagerModule uiManager = reactContext.getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(
            new UIBlock() {
                @Override
                public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
                    View view = nativeViewHierarchyManager.resolveView(reactTag);
                    double scale = (double) Resources.getSystem().getDisplayMetrics().density;
                    int x = (int) (rectJson.getDouble("x") * scale);
                    int y = (int) (rectJson.getDouble("y") * scale);
                    int width = (int) (rectJson.getDouble("width") * scale);
                    int height = (int) (rectJson.getDouble("height") * scale);
                    Rect rect = new Rect(x, y, x + width, y + height);

                    if (view instanceof ScrollView) {
                        ScrollView scrollview = (ScrollView) view;
                        scrollVertical(scrollview, rect, animated);
                    } else if (view instanceof HorizontalScrollView) {
                        HorizontalScrollView scrollview = (HorizontalScrollView) view;
                        scrollHorizontal(scrollview, rect, animated);
                    }
                }
            }
        );
    }
}
